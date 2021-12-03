<?php
header("Access-Control-Allow-Origin:*");

require_once 'koneksi.php';

$conn->set_charset("utf8");

extract($_POST);

if (isset($get_check_out)){
    $query = "SELECT location.code, name, check_in, number_vaccine FROM history INNER JOIN location ON history.location_code = location.code WHERE username = ? AND check_out is NULL";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("s", $username);

    if ($stmt->execute()){
        $res = $stmt->get_result();
        $data = $res->fetch_assoc();

        $color = "#FEEA3B"; //kuning
        if ($data['number_vaccine'] > 1) $color = "#9EDA58"; //hijau

        $data['bgColor'] = $color;

        $arr = array("result" => "OK", "status" => "success","data" => $data);
    }else {
        $arr = array("result" => "NG", "messages" => "Unable to get check out!");
    }
} else if (isset($btncheck_out)){
    $query = "UPDATE history SET check_out = ? WHERE username = ? AND location_code = ? AND check_in = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("ssss", $checkout, $username, $loccode, $checkin);

    if ($stmt->execute()){

        $arr = array("result" => "OK", "status" => "success","messages" => "Successfully Checked out!");
    }else {
        $arr = array("result" => "NG", "messages" => "Unable to get check out!");
    }
}else{
    $arr = array("result" => "NG", "messages" => "No checked command");
}

echo json_encode($arr);