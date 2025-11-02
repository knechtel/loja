<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$servername = "localhost:3306";
$username = "maique26_local_user";
$passwordd = "local_321";
$database = "maique26_loja";

$conn = new mysqli($servername, $username, $passwordd, $database);
if ($conn->connect_error) {
    die(json_encode(["erro" => "Erro na conexão com o banco."]));
}

$entry_date = isset($_GET['entry_date']) ? $_GET['entry_date'] : "";

if ($entry_date != "") {
    $sql = "SELECT id, client_id, description, model, entry_date 
            FROM equipment 
            WHERE DATE(entry_date) = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $entry_date);
} else {
    $sql = "SELECT id, client_id, description, model, entry_date 
            FROM equipment 
            ORDER BY id DESC";
    $stmt = $conn->prepare($sql);
}

$stmt->execute();
$result = $stmt->get_result();

$dados = [];
while ($row = $result->fetch_assoc()) {
    $dados[] = $row;
}

echo json_encode($dados);
$conn->close();
?>
