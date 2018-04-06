<?php	
	require("Slim-2.6.2/Slim/Slim.php");
	require("modelos/conex.php");

	\Slim\Slim::registerAutoloader();
	$app = new \Slim\Slim();

	$app->get("/dependientes/:ciudadano",function($ciudadano) use($app){
		try {
			$conn = Conexion::abrir();
			$sql = "SELECT * FROM ciudadanodependiente WHERE Estado='A' AND DniCiudadano=?";
			$stmt = $conn->prepare($sql);
			$stmt->bindParam(1, $ciudadano);
			$stmt->execute();
			$dependiente = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$dataArray = array("dependiente" => $dependiente);
			$conn = null;

			$app->response->headers->set("Content-type", "application/json");
			$app->response->status(200);
			$app->response->body(json_encode($dataArray));
		} catch (Exception $e) {
			echo "Error: " . $e->getMessage();
		}
	});

	$app->run();
?>