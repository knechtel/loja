<?php
require  "../connector.php";



  
$listUser = $conn->query("select * from user_profile");
// Check connection
if ($conn->connect_error) {
    $test = "erro";
}else{
    $test = "okay mesmo";
}

$rows = array();
foreach ($listUser as $row){
   $rows[] = $row;
}




$conn->close();
  
// make it json format
echo json_encode($rows);