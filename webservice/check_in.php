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
		- $location_name
		- $code
		- $username
	*/

	if (isset($_POST['username'])) {
		if (isset($_POST['location_name']) && isset($_POST['code'])) {
			$stmt = $conn->prepare("SELECT * FROM location WHERE code = ? AND name = ?");
			$stmt->bind_param("ss", $code, $location_name);
			$stmt->execute();
			$result = $stmt->get_result();
			
			if ($result->num_rows > 0) {
				$stmt = $conn->prepare("INSERT INTO history(check_in, user_username, location_code) VALUES(CURRENT_TIMESTAMP, ?, ?)");
				$stmt->bind_param("ss", $username, $code);
				$stmt->execute();

				if ($stmt->affected_rows > 0) {
					echo json_encode(array('result'=>'OK', 'data'=>$stmt->insert_id));
				}
				else {
					die(json_encode(array('result'=>'ERROR', 'message'=>'There is something error when check in')));
				}
			}
			else {
				die(json_encode(array('result'=>'ERROR', 'message'=>'Please select location and type the unique code correctly!')));
			}
		}
		else {
			die(json_encode(array('result'=>'ERROR', 'message'=>'No data input')));
		}
	}
	else {
		die(json_encode(array('result'=>'ERROR', 'message'=>'User or account not found')));
	}

	$stmt->close();
	$conn->close();
?>