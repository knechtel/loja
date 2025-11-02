<?php
function insert($address, $cpf, $email, $name, $rg, $phone) {
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("INSERT INTO client (address, cpf,email, name, rg, phone  ) VALUES (?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssss", $address, $cpf, $email, $name, $rg, $phone);
    
  if ($stmt->execute()) {
    $newId = $stmt->insert_id; // ou $conn1->insert_id
    echo json_encode([
        "success" => true,
        "id" => $newId,
        "message" => "Cliente inserido com sucesso!"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "error" => $stmt->error
    ]);
}
    $conn1->close();
}

function update($address, $cpf, $email, $name, $rg, $phone,$id) {
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("UPDATE  client SET address = ?, cpf = ?,
    email = ?, name = ?, rg = ?,
    phone = ? WHERE id = ?");
    $stmt->bind_param("ssssssi", $address, $cpf,$email,$name,$rg,$phone,$id);
    
    if ($stmt->execute()) {
     //   echo "Client Atualizado com sucesso!";
    } else {
        echo "Erro ao inserir: " . $stmt->error;
    }
    $conn1->close();
}

function findClientById($id){
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("select address, cpf, email, name ,rg, phone, id from client where id = ?");
    
    $stmt->bind_param("i", $id);
   
if ($stmt->execute()) {
    $result = $stmt->get_result(); // Recupera o resultado da consulta

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc(); // Pega os dados como array associativo
        
        return $row;
    } else {
        echo json_encode([
            "status" => "not_found",
            "message" => "Nenhum cliente encontrado com esse ID."
        ]);
    }
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Erro ao executar consulta: " . $stmt->error
    ]);
}
}


function find_by_id($id){  $servername = "localhost:3306"; 
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("select address, cpf, email, name ,rg, phone, id from client where id = ?");
    
    $stmt->bind_param("i", $id);
   
if ($stmt->execute()) {
    $result = $stmt->get_result(); // Recupera o resultado da consulta

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc(); // Pega os dados como array associativo
        
        return $row;
    } else {
        echo json_encode([
            "status" => "not_found",
            "message" => "Nenhum cliente encontrado com esse ID."
        ]);
    }
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Erro ao executar consulta: " . $stmt->error
    ]);
}
}