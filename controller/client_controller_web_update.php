<?php
require "../DAO/ClientDAO.php";

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

$id       = $_POST["id"] ?? null;
$name     = $_POST["name"] ?? null;
$address  = $_POST["address"] ?? null;
$cpf      = $_POST["cpf"] ?? null;
$rg       = $_POST["rg"] ?? null;
$email    = $_POST["email"] ?? null;
$phone    = $_POST["phone"] ?? null;


///find by id 
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn12 = new mysqli($servername, $username, $passwordd, $database);
    
    




// Prepara a consulta segura
$stmt = $conn12->prepare("SELECT entregue FROM equipment WHERE client_id = ?");
$stmt->bind_param("i", $id);
$stmt->execute();

$result = $stmt->get_result();

// Monta o array de resultados
$rows1 = [];
while ($row = $result->fetch_assoc()) {
    $rows1[] = $row;
}
$conn12->close();
if($rows1[0]["entregue"]==0){
   update($address, $cpf, $email, $name, $rg, $phone,$id);
   header('Content-Type: application/json');
    http_response_code(200); // Set HTTP status code
         echo json_encode([
            "success" => true,
            "message" => $rows1[0]["entregue"],
            "data" => [
             "ts"=>"ts",
            ]
        ]);
    exit;
}else{
       header('Content-Type: application/json');
    http_response_code(400); // Set HTTP status code
        echo json_encode([
        "success" => false,
        "message" => $rows1[0]["entregue"],
         "data" => [
             "ts"=>"ts",
            ]
    ]);

    exit;
}

?>
