<?php

	$lat = $_GET["lat"];
	$lon = $_GET["lon"];
	$user_id = $_GET["user_id"];
	$action = $_GET["action"];

	 	$conn = mysqli_connect("localhost", "thesis", "123456789", "location");

	if($action == "post" ){
			$sql_insert = "insert into location (lat, lon, user_id) values( '".$lat."', '".$lon."', '".$user_id."')";

			if (mysqli_query($conn, $sql_insert);){
				echo json_encode("{success}");
			}else{
				echo json_encode("{failed}");
			}

		}else{
	    	$sql2 = "select * FROM location WHERE user_id = {$user_id}";

				$result_data = fetch_multiple_result($sql2);
		    echo json_encode($result_data);
		}


?>
