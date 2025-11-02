<?php
require  "../DAO/ClientDAO.php";

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");


$dados = json_decode($json, true); 


$address = $_POST['address'];
$cpf     = $_POST['cpf'];
$email   = $_POST['email'];
$name    = $_POST['name'];
$rg      = $_POST['rg'];
$phone   = $_POST['phone'];
insert($address, $cpf, $email, $name, $rg, $phone);