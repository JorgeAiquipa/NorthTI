<?php	
	require("Slim-2.6.2/Slim/Slim.php");
	require("modelos/conex.php");

	\Slim\Slim::registerAutoloader();
	$app = new \Slim\Slim();

	$app->get("/actividades",function() use($app){
		try {
			$conn = Conexion::abrir();
			$sql = "SELECT * FROM actividad";
			$stmt = $conn->prepare($sql);
			$stmt->execute();
			$actividad = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$dataArray = array("actividad" => $actividad);
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