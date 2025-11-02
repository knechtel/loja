<?php
require('fpdf186/fpdf.php');
require('DAO/ClientDAO.php');


class PDF extends FPDF {
    function Header() {
         $this->Image('2004_widget.jpeg', 10, 8, 30); // (x=10mm, y=8mm, largura=30mm)
        $this->SetFont('Arial','B',14);
        $this->Cell(0,7,utf8_decode('Eletrônica Delta'),0,1,'C');
           // Cabeçalho da loja
        $this->SetFont('Arial','B',12);
        $this->Cell(0,6,utf8_decode('Fernandes Bastos 1978'),0,1,'C');
        $this->Cell(0,6,'Telefone: (51) 98204-0011',0,1,'C');
        $this->Cell(0,6,'Email: maiquelknechtel@gmail.com',0,1,'C');
        $this->Ln(5);

        // Título da nota
        $this->SetFont('Arial','B',16);
        $this->Cell(0,10,'NOTA DE SERVICO',0,1,'C');
        $this->Ln(5);
    }

    function Footer() {
        $this->SetY(-15);
        $this->SetFont('Arial','I',8);
        $this->Cell(0,10,'Pagina '.$this->PageNo().'/{nb}',0,0,'C');
    }
}

$pdf = new PDF();
$pdf->AliasNbPages();
$pdf->AddPage();
$pdf->SetFont('Arial','',12);

// ==========================
// SEÇÃO CLIENTE
// ==========================
$pdf->SetFont('Arial','B',14);
$pdf->Cell(0,10,'Dados do Cliente',0,1);
$pdf->SetFont('Arial','',12);

$id = $_GET['id']; 
$row  = find_by_id($id);
  $endereco = $row["address"];
    $cpf     = $row['cpf'];
    $email   = $row['email'];
    $nome    = $row['name'];
    $cpf      = $row['rg'];
    $telefone   = $row['phone'];
    $id      = $row['id'];

$idCliente = $id; // Novo campo

// ID do Cliente
$pdf->Cell(40,8,"ID Cliente:",0,0);
$pdf->Cell(0,8,$idCliente,0,1);

// Demais dados
$pdf->Cell(40,8,"Nome:",0,0);
$pdf->Cell(0,8,$nome,0,1);

$pdf->Cell(40,8,"Endereco:",0,0);
$pdf->Cell(0,8,$endereco,0,1);

$pdf->Cell(40,8,"Email:",0,0);
$pdf->Cell(0,8,$email,0,1);

$pdf->Cell(40,8,"CPF:",0,0);
$pdf->Cell(0,8,$cpf,0,1);

$pdf->Cell(40,8,"Telefone:",0,0);
$pdf->Cell(0,8,$telefone,0,1);

$pdf->Ln(10);

// ==========================
// SEÇÃO APARELHO
// ==========================
$pdf->SetFont('Arial','B',14);
$pdf->Cell(0,10,'Dados do Aparelho',0,1);
$pdf->SetFont('Arial','',12);
///find by id 
    $servername = "localhost:3306"; // ou o IP se for remoto
    $username = "maique26_local_user";
    $passwordd = "local_321";
    $database = "maique26";
    $conn12 = new mysqli($servername, $username, $passwordd, $database);
    
    




// Prepara a consulta segura
$stmt = $conn12->prepare("SELECT * FROM equipment WHERE client_id = ?");
$stmt->bind_param("i", $id);
$stmt->execute();

$result = $stmt->get_result();

// Monta o array de resultados
$rows1 = [];
while ($row = $result->fetch_assoc()) {
    $rows1[] = $row;
}


 $conn12->close();

///
$descricao =  $rows1[0]['description'];
$modelo = $rows1[0]['model'];
$serial = $rows1[0]['serial'];
$marca = $rows1[0]['brand'];
$defeito = $rows1[0]['defect_defect_for_repair'];
$valor =  $rows1[0]['price'];
$preco = "R$ " . number_format($valor, 2, ',', '.');
$data_format =  $rows1[0]["entry_date"];
$data_entry = new DateTime($data_format);
$dataFormatada = $data_entry->format('d/m/Y H:i');
$dataEntrada = $dataFormatada;


$data_format_departure = $rows1[0]["departure_date"];
$dataSaida = "Nao informado"; // valor padrão

if (!empty($data_format_departure) && $data_format_departure != "0000-00-00 00:00:00") {
    try {
        $data_departure = new DateTime($data_format_departure);
        $dataSaida = $data_departure->format('d/m/Y H:i');
    } catch (Exception $e) {
        $dataSaida = "Data invalida";
    }
}


$pdf->Cell(40,8,"Descricao:",0,0);
$pdf->Cell(0,8,$descricao,0,1);

$pdf->Cell(40,8,"Modelo:",0,0);
$pdf->Cell(0,8,$modelo,0,1);

$pdf->Cell(40,8,"Serial:",0,0);
$pdf->Cell(0,8,$serial,0,1);

$pdf->Cell(40,8,"Marca:",0,0);
$pdf->Cell(0,8,$marca,0,1);

$pdf->Cell(40,8,"Defeito:",0,0);
$pdf->MultiCell(0,8,$defeito,0,1);

$pdf->Cell(40,8,"Preco:",0,0);
$pdf->Cell(0,8,$preco,0,1);

$pdf->Cell(40,8,"Data de Entrada:",0,0);
$pdf->Cell(0,8,$dataEntrada,0,1);


$pdf->Cell(40,8,"Data de Saida:",0,0);
$pdf->Cell(0,8,$dataSaida,0,1); // <<< ADICIONADO AQUI
$pdf->Ln(10);

// Assinatura
$pdf->Cell(0,20,"___________________________________",0,1,'C');
$pdf->Cell(0,10,"Assinatura do Cliente",0,1,'C');

// Gera o PDF
$pdf->Output('I', $id.'nota.pdf');
?>