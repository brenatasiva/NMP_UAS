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

if (isset($get_user)){
    if (isset($password)){
        $query = "SELECT username, name, number_vaccine FROM user WHERE username = ? AND password = ?";
        $stmt = $conn->prepare($query);
        $stmt->bind_param("ss", $username, $password);
    }else{
        $query = "SELECT name, number_vaccine FROM user WHERE username = ?";
        $stmt = $conn->prepare($query);
        $stmt->bind_param("s", $username);
    }

    if ($stmt->execute()){
        $res = $stmt->get_result();
        $data = array();

        while ($obj = $res->fetch_object()) {
            $data[] = $obj;
        }

        $arr = array("result" => "OK", "data" => $data);
    }else {
        $arr = array("result" => "NG", "messages" => "Unable to login!");
    }
} else{
    $arr = array("result" => "NG", "messages" => "No checked command");
}

echo json_encode($arr);