<?php
require  "../DAO/EquipmentDAO.php";


$json = file_get_contents("php://input");

$dados = json_decode($json, true); 




$autorizado = $dados['autorizado'];
$cost_value = $dados['cost_value'];
$defect_defect_for_repair = $dados['defect_defect_for_repair'];
$departure_date = $dados['departure_date'];
$departure_equipment_warranty = $dados['departure_equipment_warranty'];
$model =  $dados['model'];
$price =  $dados['price'];
$pronto =  $dados['pronto'];
$serial =  $dados['serial'];
$client_id = $dados['client_id'];
$brand = $dados['brand'];
$devolucao = $dados['devolucao'];
$obs = $dados['obs'];
$entregue = $dados['entregue'];
$garantia = $dados['garantia'];
$description = $dados['description'];
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