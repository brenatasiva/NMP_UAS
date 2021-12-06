<?php
header("Access-Control-Allow-Origin:*");

require_once 'koneksi.php';

$conn->set_charset("utf8");

extract($_POST);

$sql = "SELECT id FROM users where username = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $username);

if ($stmt->execute()) {
    $res = $stmt->get_result();
    $row = $res->fetch_assoc();
    $id = $row['id'];
}

if ($history) {
    $sql = "SELECT p.id, name, checkin, doses FROM histories h INNER JOIN places p ON h.places_id = p.id WHERE users_id = ? AND checkout is NULL";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $id);

    if ($stmt->execute()) {
        $res = $stmt->get_result();
        $data = $res->fetch_assoc();

        $data['color'] = ($data['doses'] > 1) ? "#9EDA58" : "#FEEA3B";

        $arr = array("result" => "OK", "status" => "success", "data" => $data);
    } else {
        $arr = array("result" => "NG", "messages" => "Unable to get check out!");
    }
} else if ($btnCheckOut) {
    $sql = "UPDATE histories SET checkout = ? WHERE users_id = ? AND places_code = ? AND checkin = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssss", $checkOutDate, $id, $code, $checkInDate);

    if ($stmt->execute()) {

        $arr = array("result" => "OK", "status" => "success", "messages" => "Successfully Checked out!");
    } else {
        $arr = array("result" => "NG", "messages" => "Unable to get check out!");
    }
} else {
    $arr = array("result" => "NG", "messages" => "No checked command");
}

echo json_encode($arr);
