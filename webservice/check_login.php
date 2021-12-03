<?php
	error_reporting(E_ERROR | E_PARSE);
	$conn = new mysqli("localhost", "native_160419002", "ubaya", "native_160419002");

	if ($conn->connect_errno) {
		echo json_encode(array('result'=>'ERROR', 'message'=>'Failed to connect DB'));
		die();
	}

	$conn->set_charset('UTF8');
	extract($_POST);
	/*
		EXTRACTED VARIABLE NAME :
		- $username
		- $password
	*/
	
	if (isset($_POST['username']) && isset($_POST['password'])) {
		$stmt = $conn->prepare("SELECT name, total_vaccine FROM user WHERE username = ? AND password = ?");
		$stmt->bind_param("ss", $username, $password);
		$stmt->execute();
		$result = $stmt->get_result();

		$output = [];
		if ($result->num_rows > 0) {
			while ($obj = $result->fetch_object()) {
				$output[] = $obj;
			}
			echo json_encode(array('result'=>'OK', 'data'=>$output));
		}
		else {
			die(json_encode(array('result'=>'ERROR', 'message'=>'Username or password are incorrect')));
		}
	}
	else {
		die(json_encode(array('result'=>'ERROR', 'message'=>'No input found')));
	}

	$stmt->close();
	$conn->close();
?>