<?php	
	require("Slim-2.6.2/Slim/Slim.php");
	require("modelos/conex.php");

	\Slim\Slim::registerAutoloader();
	$app = new \Slim\Slim();

	$app->get("/horarios/:actividad/:sede/:edadHijo",function($actividad, $sede, $edadHijo) use($app){
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
				    pah.descripcion as deshor,pah.edaddesde,pah.edadhasta,pah.fechainscripcion,pah.cupos - pah.matriculados as disponible ,s.descripcion as dessed,
					pah.Costo
					from programaactividadhorariodia pahd inner join programaactividadhorario pah
				    on pahd.idprograma = pah.idprograma and pahd.idactividad=pah.idactividad and
				    pahd.idhorario = pah.idhorario inner join programa p on pahd.idprograma = p.idprograma inner join actividad a on pahd.idactividad = a.idactividad 
				    inner join sede s on pah.idsede = s.idsede 
				    where pahd.idprograma=1 and pah.idsede=? and pahd.idactividad=? and ? between EdadDesde and EdadHasta and (pah.cupos - pah.matriculados) > 0";
			$stmt = $conn->prepare($sql);
			$stmt->bindParam(1, $sede);
			$stmt->bindParam(2, $actividad);
			$stmt->bindParam(3, $edadHijo);
			$stmt->execute();
			$horario = $stmt->fetchAll(PDO::FETCH_ASSOC);
			$dataArray = array("horario" => $horario);
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