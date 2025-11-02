<?php
require  "../DAO/ClientDAO.php";


$json = file_get_contents("php://input");

$dados = json_decode($json, true); 


$address = $dados['address'];
$cpf = $dados['cpf'];
$email = $dados['email'];
$name = $dados['name'];
$rg = $dados['rg'];
$phone = $dados['phone'];
insert($address, $cpf, $email, $name, $rg, $phone);




