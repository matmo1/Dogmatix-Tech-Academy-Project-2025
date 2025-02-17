<?php
session_start();
require('db.php');

$login_message = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = "SELECT user_id, username, password FROM users WHERE username = ?";
    
    if ($stmt = $con->prepare($sql)) {
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $stmt->store_result();
        
        if ($stmt->num_rows == 1) {
            $stmt->bind_result($id, $stored_username, $stored_password);
            $stmt->fetch();

            if (password_verify($password, $stored_password)) {
                $_SESSION['user_id'] = $id;
                $_SESSION['username'] = $stored_username;
                
                header("Location: index.php");
                exit();
            } else {
                $login_message = "Invalid password.";
            }
        } else {
            $login_message = "No account found with that username.";
        }
        $stmt->close();
    } else {
        $login_message = "Database error. Please try again later.";
    }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            flex-direction: column;
        }

        form {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            margin: 10px;
        }

        button:hover {
            background-color: #45a049;
        }

        .error {
            color: red;
        }
    </style>
</head>

<body>
    <h2>Login</h2>
    <div>
        <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
            <label for="username"><b>Username:</b></label>
            <input type="text" id="username" name="username" required><br>

            <label for="password"><b>Password:</b></label>
            <input type="password" id="password" name="password" required><br>

            <button type="submit" name="submit">Login</button>

            <?php
            if (!empty($login_message)) {
                echo "<p class='error'>$login_message</p>";
            }
            ?>
            <p>Don't have an account? <a href="register.php">Sign up here</a></p>
        </form>
    </div>
</body>
</html>
