<?php
header("Access-Control-Allow-Origin:*");

require_once 'koneksi.php';

$conn->set_charset("utf8");

extract($_POST);

if (isset($checkStatus)) {
    $sql = "SELECT histories.* , p.name FROM histories h INNER JOIN places p ON h.places_id = p.id WHERE username = ? ORDER BY checkin DESC";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $username);

    if ($stmt->execute()) {
        $res = $stmt->get_result();
        $data = array();

        while ($obj = $res->fetch_object()) {
            $data[] = $obj;
        }

        $arr = array("result" => "OK", "data" => $data);
    } else {
        $arr = array("result" => "NG", "messages" => "Unable to insert playlist");
    }
} else {
    $arr = array("result" => "NG", "messages" => "No checked command");
}

echo json_encode($arr);
