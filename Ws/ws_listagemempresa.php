 <?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$conn = new mysqli($servername, $username, $password);

	$id = $_GET["id"];

	$query = "SELECT * FROM dbturistandoibitinga.viewSelectEmpresaListagem where id between ($id+1) and ($id+25)";
   
	$result = mysqli_query($conn, $query);

	while($row = mysqli_fetch_assoc($result)){

		$array[] = $row;
	}

	header('Content-Type:Application/json');
	echo json_encode($array);


?>