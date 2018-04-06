<?php	
	require("Slim-2.6.2/Slim/Slim.php");
	require("modelos/conex.php");

	\Slim\Slim::registerAutoloader();
	$app = new \Slim\Slim();

	$app->get("/sedes",function() use($app){
		try {
			$conn = Conexion::abrir();
			$sql = "SELECT * FROM sede WHERE Estado='A'";
			$stmt = $conn->prepare($sql);
			$stmt->execute();
			$sede = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$dataArray = array("sede" => $sede);
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