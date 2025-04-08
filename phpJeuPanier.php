<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: text/plain");

$servername = "localhost";
$username = "root"; 
$password = "";
$dbname = "basket_game_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$action = $_GET['action'] ?? '';

if ($action == 'getScores') {
    $result = $conn->query("SELECT meilleur_score FROM score_table WHERE id_joueur IN (1, 2) ORDER BY id_joueur");
    $scores = [];
    while ($row = $result->fetch_assoc()) {
        $scores[] = $row['meilleur_score'];
    }
    echo implode(",", $scores);
} 
elseif ($action == 'getScore') {
    $player = intval($_GET['player']);
    $result = $conn->query("SELECT meilleur_score FROM score_table WHERE id_joueur = $player");
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        echo $row['meilleur_score'];
    } else {
        echo "0";
    }
} 
elseif ($action == 'submitScore') {
    $player = intval($_GET['player']);
    $score = intval($_GET['score']);
    
  
    $result = $conn->query("SELECT meilleur_score FROM score_table WHERE id_joueur = $player");
    
    if ($result->num_rows > 0) {
        
        $row = $result->fetch_assoc();
        $currentHighScore = $row['meilleur_score'];
        
        if ($score > $currentHighScore) {
            $conn->query("UPDATE score_table SET meilleur_score = $score WHERE id_joueur = $player");
            echo "Updated";
        } else {
            echo "Not updated";
        }
    } else {
       
        $conn->query("INSERT INTO score_table (id_joueur, meilleur_score) VALUES ($player, $score)");
        echo "Created new player record";
    }
}

$conn->close();
?>