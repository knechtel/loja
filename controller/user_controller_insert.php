<?php
require  "../DAO/UserDAO.php";


$json = file_get_contents("php://input");

$dados = json_decode($json, true); 


$login = $dados['login'];
$password = $dados['password'];
insert($login,$password);




