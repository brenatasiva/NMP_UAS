<?php
header("Access-Control-Allow-Origin:*");
error_reporting(E_ERROR | E_PARSE);
$conn = new mysqli("localhost", "native_160419003", "ubaya", "native_160419003");
$conn->set_charset("utf8");//Untuk membaca charset

//check for any connection
if ($conn->connect_error) {
    die(json_encode(array("result" => "ERROR", "message" => "Failed to connect to DB " . $conn->connect_error)));
}

extract($_POST);

if (isset($btncheck_in)){
    $query = "SELECT * FROM location WHERE code = ? AND name = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("ss", $location_code, $locName);
    $stmt->execute();
    $res = $stmt->get_result();

    if ($res->num_rows > 0){
        $query = "SELECT number_vaccine FROM user WHERE username = ?";
        $stmt = $conn->prepare($query);
        $stmt->bind_param("s", $username);
    
        if ($stmt->execute()){
            $res = $stmt->get_result();
            $vaccine = $res->fetch_assoc()['number_vaccine'];

            if ($vaccine > 0){  
                $query = "INSERT INTO history(username, location_code, check_in, number_vaccine) VALUES(?,?,?,?)";
                $stmt = $conn->prepare($query);
                $stmt->bind_param("sssi", $username, $location_code, $checkIn, $vaccine);
                $stmt->execute();

                $arr = array("result" => "OK", "status" => "success","messages" => "Successfully Checked in!");
            }else{
                $arr = array("result" => "OK", "status" => "failed", "messages" => "You must vaccinated at least once.");
            }
        }else {
            $arr = array("result" => "NG", "messages" => "Unable to insert playlist");
        }
    }else{
        $arr = array("result" => "OK", "status" => "failed", "messages" => "Invalid code!");
    }
} else if ($get_check_in){       
    $query = "SELECT * FROM location";
    $res = $conn->query($query);

    $data = array();
    while($row = $res->fetch_assoc()){
        array_push($data,$row);
    }

    $arr = array("result" => "OK", "status" => "success","data" => $data);
}else{
    $arr = array("result" => "NG", "messages" => "No checked command");
}

echo json_encode($arr);