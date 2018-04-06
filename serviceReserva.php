<?php	
	require("Slim-2.6.2/Slim/Slim.php");
	require("modelos/conex.php");

	\Slim\Slim::registerAutoloader();
	$app = new \Slim\Slim();

	$app->get("/reserva/:ciudadano",function($ciudadano) use($app){
		try {
			$conn = Conexion::abrir();
			$sql = "select pahd.idprograma,p.descripcion, pahd.idactividad, a.descripcion as disciplina, pahd.idhorario,
				   case when pahd.dia = 1 then 'Lun'
				        when pahd.dia = 2 then 'Mar'
				        when pahd.dia = 3 then 'Mie'
				        when pahd.dia = 4 then 'Jue'
				        when pahd.dia = 5 then 'Vie'
				        when pahd.dia = 6 then 'Sab'
				        when pahd.dia = 7 then 'Dom'
				        else '***' end as sem,pahd.horainicio,pahd.horafin,
				     pah.descripcion as deshor,pah.edaddesde,pah.edadhasta,pah.fechainscripcion,pah.cupos - pah.matriculados as disponible ,s.descripcion as dessed, mat.idmatricula, pah.Costo
				  from  matricula mat inner join  
				  programaactividadhorariodia pahd
				  on mat.idprograma = pahd.idprograma and mat.idactividad=pahd.idactividad and
				          mat.idhorario = pahd.idhorario
				  inner join programaactividadhorario pah
				       on pahd.idprograma = pah.idprograma and pahd.idactividad=pah.idactividad and
				          pahd.idhorario = pah.idhorario inner join programa p on pahd.idprograma = p.idprograma inner join actividad a on pahd.idactividad = a.idactividad 
				          inner join sede s on pah.idsede = s.idsede            
				     where  mat.DniCiudadano=? and mat.Estado='R' and mat.DniDependiente <>'' group by mat.IdMatricula";
			$stmt = $conn->prepare($sql);
			$stmt->bindParam(1, $ciudadano);
			$stmt->execute();
			$reserva = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$dataArray = array("reserva" => $reserva);
			$conn = null;

			$app->response->headers->set("Content-type", "application/json");
			$app->response->status(200);
			$app->response->body(json_encode($dataArray));
		} catch (Exception $e) {
			echo "Error: " . $e->getMessage();
		}
	});

	$app->post("/reserva/grabar",function() use($app){
		$actividad = $_POST['actividad'];
		$programa = $_POST['programa'];
		$horario = $_POST['horario'];
		$ciudadano = $_POST['ciudadano'];
		$dependiente = $_POST['dependiente'];
		$precio = $_POST['precio'];

		try {
			$conn = Conexion::abrir();
			$sql = "INSERT INTO matricula VALUES(NULL,DATE(NOW()),?,?,?,?,?,DATE(NOW()),?,'R','0')";
			$stmt = $conn->prepare($sql);
			$stmt->bindParam(1, $programa);
			$stmt->bindParam(2, $actividad);
			$stmt->bindParam(3, $horario);
			$stmt->bindParam(4, $ciudadano);
			$stmt->bindParam(5, $dependiente);
			$stmt->bindParam(6, $precio);
			$stmt->execute();
			$conn = null;

			$app->response->headers->set("Content-type", "application/json");
			$app->response->status(200);
			$app->response->body(json_encode(1));
		} catch (Exception $e) {
			echo "Error: " . $e->getMessage();
		}
	});

	$app->post("/reserva/pagar",function() use($app){
		$matricula = $_POST['matricula'];
		$monto = $_POST['monto'];

		try {
			$conn = Conexion::abrir();
			$sql = "INSERT INTO comprobante VALUES(NULL,?,DATE(NOW()),?,'A')";
			$stmt = $conn->prepare($sql);
			$stmt->bindParam(1, $matricula);
			$stmt->bindParam(2, $monto);
			$stmt->execute();

			$sql = "UPDATE matricula SET Estado='P', FlgComprobante='1' WHERE IdMatricula=?";
			$stmt = $conn->prepare($sql);
			$stmt->bindParam(1, $matricula);
			$stmt->execute();

			$conn = null;

			$app->response->headers->set("Content-type", "application/json");
			$app->response->status(200);
			$app->response->body(json_encode(1));
		} catch (Exception $e) {
			echo "Error: " . $e->getMessage();
		}
	});

	$app->run();
?>