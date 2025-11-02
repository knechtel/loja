<?php
require "../connector.php";

header("Access-Control-Allow-Origin: https://sftcode.com");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");
header('Content-Type: application/json');

if (!isset($_GET["id"])) {
    echo json_encode(["erro" => "ID não informado"]);
    exit;
}

$id = intval($_GET["id"]);

$stmt = $conn->prepare("SELECT * FROM equipment  WHERE client_id = ?");
$stmt->bind_param("i", $id);
$stmt->execute();
$result = $stmt->get_result();

$equipamentos = [];

while ($row = $result->fetch_assoc()) {
    $equipamentos[] = $row;
}

$conn->close();

echo json_encode($equipamentos);
?>
