<?php




require  "../DAO/EquipmentDAO.php";



header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");
$id       = $_POST["id"] ;
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



if($departure_date==="0000-00-00 00:00:00.000000"){
//    $dataAtual = new DateTime();
//    $departure_date= dataAtual;}
//echo "meu deus uuuuuuuusss";
    if($entregue==0){
    update($id,$autorizado, $cost_value, $defect_defect_for_repair,
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
         header('Content-Type: application/json');
    http_response_code(200); // Set HTTP status code
    exit;
    }
    else
    {
  $dataAtual = date('Y-m-d H:i:s');
    $departure_date= $dataAtual;
            update($id,$autorizado, $cost_value, $defect_defect_for_repair,
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
     header('Content-Type: application/json');
    http_response_code(200); // Set HTTP status code
    
    exit;
    }

}else{

 header('Content-Type: application/json');
    http_response_code(400); // Set HTTP status code
    echo json_encode(['error' => 'Invalid input data', 'code' => 1001]);
    exit;
    
}
