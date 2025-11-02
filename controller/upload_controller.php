<?php
$target_dir = "../photo/";
if (!file_exists($target_dir)) {
    mkdir($target_dir, 0777, true);
}

$target_file = $target_dir . basename($_FILES["file"]["name"]);

if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
    echo "Upload concluído: " . $target_file;
} else {
    echo "Erro no upload.";
}
?>
