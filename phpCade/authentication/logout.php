<?php
session_start();

if (session_destroy()) {
    echo json_encode(["status" => "success", "message" => "Logged out successfully"]);
} else {
    echo json_encode(["status" => "error", "message" => "Failed to log out"]);
}

?>
