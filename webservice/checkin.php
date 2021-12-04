<?php
header("Access-Control-Allow-Origin:*");

require_once 'koneksi.php';

$conn->set_charset("utf8");

extract($_POST);

if (isset($btnCheckIn)) {
    $sql = "SELECT * FROM location WHERE code = ? AND name = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $code, $placeName);
    $stmt->execute();
    $res = $stmt->get_result();

    if ($res->num_rows > 0) {
        $sql = "SELECT vaccination FROM users WHERE username = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $username);

        if ($stmt->execute()) {
            $res = $stmt->get_result();
            $dose = $res->fetch_assoc()['vaccination'];

            if ($vaccine > 0) {
                $sql = "INSERT INTO histories(username, places_id, checkin, doses) VALUES(?,?,?,?)";
                $stmt = $conn->prepare($sql);
                $stmt->bind_param("sssi", $username, $code, $checkInDate, $dose);
                $stmt->execute();

                $arr = array("result" => "OK", "status" => "success", "messages" => "Successfully Checked in!");
            } else {
                $arr = array("result" => "OK", "status" => "failed", "messages" => "You must vaccinated at least once.");
            }
        } else {
            $arr = array("result" => "NG", "messages" => "Unable to insert playlist");
        }
    } else {
        $arr = array("result" => "OK", "status" => "failed", "messages" => "Invalid code!");
    }
} else if ($getPlaces) {
    $sql = "SELECT * FROM places";
    $res = $conn->query($sql);

    $data = array();
    while ($row = $res->fetch_assoc()) {
        array_push($data, $row);
    }

    $arr = array("result" => "OK", "status" => "success", "data" => $data);
} else {
    $arr = array("result" => "NG", "messages" => "No checked command");
}

echo json_encode($arr);
