<?php
require "../DAO/ClientDAO.php";

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

if (!isset($_GET["id"])) {
    echo json_encode(["erro" => "ID não informado"]);
    exit;
}

$id = intval($_GET["id"]);

$cliente = findClientById($id);

if ($cliente) {
    echo json_encode($cliente);
} else {
    echo json_encode(["erro" => "Cliente não encontrado"]);
}
?>
