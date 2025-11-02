<?php

require  "../connector.php";



  
$listClient = $conn->query("select * from client ORDER BY id DESC;");
// Check connection
if ($conn->connect_error) {
    $test = "erro";
}else{
    $test = "okay mesmo";
}

$rows = array();
foreach ($listClient as $row){
   $rows[] = $row;
}




$conn->close();
  
// make it json format
echo json_encode($rows);
