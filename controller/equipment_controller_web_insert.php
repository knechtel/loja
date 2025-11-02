<?php
require  "../DAO/EquipmentDAO.php";

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");







$autorizado = $_POST['autorizado'];
$cost_value = $_POST['cost_value'];
$defect_defect_for_repair = $_POST['defect_defect_for_repair'];
$departure_date = $_POST['departure_date'];
$departure_equipment_warranty = $_POST['departure_equipment_warranty'];
$model =  $_POST['model'];
$price =  $_POST['price'];
$pronto =  $_POST['pronto'];
$serial =  $_POST['serial'];
$client_id = $_POST['client_id'];
$brand = $_POST['brand'];
$devolucao = $_POST['devolucao'];
$obs = $_POST['obs'];
$entregue = $_POST['entregue'];
$garantia = $_POST['garantia'];
$description = $_POST['description'];
$entry_date = new DateTime(); // Data atual
$data = new DateTime();
$dataFormatada = $data->format('Y-m-d H:i:s');
$entry_date = $dataFormatada;
insert($autorizado, $cost_value, $defect_defect_for_repair,
    $departure_date, $departure_equipment_warranty,
    $entry_date,
    $entry_equipment_warranty,
    $model,
    $price,
    $pronto,
    $serial,
    $client_id,
    $brand,
    $devolucao,
    $obs,
    $entregue,
    $garantia,
    $description
    ) ;