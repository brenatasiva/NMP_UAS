<?php
header("Access-Control-Allow-Origin:*");

require_once 'koneksi.php';

$conn->set_charset("utf8");

extract($_POST);

if (isset($getUser)) {
    if (isset($password)) {
        $sql = "SELECT username, name, vaccination FROM users WHERE username = ? AND password = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ss", $username, $password);
    } else {
        $sql = "SELECT name, vaccination FROM users WHERE username = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $username);
    }

    if ($stmt->execute()) {
        $res = $stmt->get_result();

        while ($obj = $res->fetch_object()) {
            $data[] = $obj;
        }

        $arr = array("result" => "OK", "data" => $data);
    } else {
        $arr = array("result" => "NG", "messages" => "Unable to login!");
    }
} else {
    $arr = array("result" => "NG", "messages" => "No checked command");
}

echo json_encode($arr);
