<?php
function insert($autorizado, $cost_value, $defect_defect_for_repair,
    $departure_date, $departure_equipment_warranty,
    $entry_date,
    $entry_equipment_warranty,
    $model,
    $price,
    $pronto,
    $serial,
    $client_id,
    $brand,
    $devolucao,
    $obs,
    $entregue,
    $garantia,
    $description
    ) {
        
   
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("INSERT INTO equipment ( autorizado, cost_value, defect_defect_for_repair, 
        departure_date, 
        departure_equipment_warranty,
        entry_date, 
        entry_equipment_warranty,
        model, 
        price, 
        pronto, 
        serial, 
        client_id, 
        brand, 
        devolucao, 
        obs, 
        entregue, 
        garantia, 
        description)
        VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("idssssssdisisssiis", 
    $autorizado, 
    $cost_value, 
    $defect_defect_for_repair, 
    $departure_date, 
    $departure_equipment_warranty,
    $entry_date,
    $entry_equipment_warranty, 
    $model,
    $price,
    $pronto,
    $serial,
    $client_id,
    $brand,
    $devolucao,
    $obs,
    $entregue,
    $garantia,
    $description
    );
    
  if ($stmt->execute()) {
    $newId = $stmt->insert_id; // ou $conn1->insert_id
    echo json_encode([
        "success" => true,
        "id" => $newId,
        "message" => "Equipamento inserido com sucesso!"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "error" => $stmt->error
    ]);
}
    $conn1->close();
}


function update($id,$autorizado, $cost_value, $defect_defect_for_repair,
    $departure_date, $departure_equipment_warranty,
    $entry_date,
    $entry_equipment_warranty,
    $model,
    $price,
    $pronto,
    $serial,
    $client_id,
    $brand,
    $devolucao,
    $obs,
    $entregue,
    $garantia,
    $description
    ) {
        
   
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("UPDATE equipment  SET
    autorizado = ?, 
    cost_value = ?, 
    defect_defect_for_repair = ?, 
    departure_date = ?,  
    departure_equipment_warranty = ?, 
    entry_equipment_warranty = ?,
        model = ?, 
        price = ?, 
        pronto = ?, 
        serial = ?, 
  
        brand = ?, 
        devolucao = ? , 
        obs = ?, 
        entregue = ?, 
        garantia = ?, 
        description = ?
        WHERE id = ?");
    $stmt->bind_param("idsssssdissssiisi", 
    $autorizado, 
    $cost_value, 
    $defect_defect_for_repair, 
     $departure_date,
    $departure_equipment_warranty,

    $entry_equipment_warranty, 
    $model,
    $price,
    $pronto,
    $serial,

    $brand,
    $devolucao,
    $obs,
    $entregue,
    $garantia,
    $description,
    $id
    );
    header('Content-Type: application/json');


// if (empty($departure_date)||$departure_date===""||$departure_date===null){
    
//     if(entregue==1)
//     {
//         $entry_date = new DateTime(); // Data atual
// $data = new DateTime();
// $dataFormatada = $data->format('Y-m-d H:i:s');
// $departure_date = $dataFormatada;
//     }
if ($stmt->execute()) {
    if ($stmt->affected_rows > 0) {
        echo json_encode([
            "success" => true,
            "message" => "Equipamento atualizado com sucesso!",
            "data" => [
                "id" => $id,
                "model" => $model,
                "brand" => $brand,
                "serial" => $serial,
                "defect_defect_for_repair" => $defect_defect_for_repair,
                "description" => $description,
                "price" => $price,
                "entregue" => $entregue,
                "garantia" => $garantia
            ]
        ]);
    } else {
        echo json_encode([
            "success" => false,
            "message" => "Nenhuma linha foi alterada."
        ]);
    }
} else {
    echo json_encode([
        "success" => false,
        "message" => "Erro ao atualizar: " . $stmt->error
    ]);
}
$conn1->close();//}
}

function findClientById($id) {
    
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26_loja";
    $conn1 = new mysqli($servername, $username, $passwordd, $database);
    $stmt = $conn1->prepare("SELECT * FROM clients WHERE id = ?");
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $result = $stmt->get_result();
    return $result->fetch_assoc(); // Retorna o cliente como array associativo
    $conn1->close();
}




