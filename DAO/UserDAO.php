<?php



function insert($login, $password) {
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("INSERT INTO user_profile (login, password) VALUES (?, ?)");
    $stmt->bind_param("ss", $login, $password);
    
    if ($stmt->execute()) {
        echo "Usuário inserido com sucesso!";
    } else {
        echo "Erro ao inserir: " . $stmt->error;
    }
    $conn1->close();
}


function update($login, $password, $id) {
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("UPDATE  user_profile SET login = ?, password = ? WHERE id = ?");
    $stmt->bind_param("ssi", $login, $password,$id);
    
    if ($stmt->execute()) {
        echo "Usuário Atualizado com sucesso!";
    } else {
        echo "Erro ao inserir: " . $stmt->error;
    }
    $conn1->close();
}