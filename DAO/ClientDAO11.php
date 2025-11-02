<?php

require  "../connection/connection.php";


$listClient = $conn->query("select * from clients");
// Check connection
if ($conn->connect_error) {
    $test = "erro";
}else{
    $test = "okay mesmo";
}