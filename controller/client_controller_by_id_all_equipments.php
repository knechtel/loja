<?php


// Recebe os dados JSON da requisição POST
$json = file_get_contents("php://input");
$dados = json_decode($json, true);

// Verifica se o ID foi enviado
if (!isset($dados['id'])) {
    http_response_code(400); // Bad Request
    echo json_encode(["erro" => "ID não enviado"]);
    exit;
}

$id = $dados['id'];
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
// Prepara a consulta segura
$stmt = $conn1->prepare("SELECT * FROM equipment WHERE client_id = ?");
$stmt->bind_param("i", $id);
$stmt->execute();

$result = $stmt->get_result();

// Monta o array de resultados
$rows = [];
while ($row = $result->fetch_assoc()) {
    $rows[] = $row;
}

$conn1->close();

// Define o tipo de resposta e retorna o JSON
header('Content-Type: application/json');
echo json_encode($rows);
?>
