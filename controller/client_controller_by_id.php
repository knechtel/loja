<?php
require  "../DAO/ClientDAO.php";


$json = file_get_contents("php://input");

$dados = json_decode($json, true); 


$id = $dados['id'];


echo json_encode(find_by_id($id));

