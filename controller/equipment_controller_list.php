<?php

require  "../connector.php";



  
$listEquipment = $conn->query("select * from equipment");
// Check connection
if ($conn->connect_error) {
    $test = "erro";
}else{
    $test = "okay mesmo";
}

$rows = array();
foreach ($listEquipment as $row){
   $rows[] = $row;
}




$conn->close();
  
// make it json format
echo json_encode($rows);
