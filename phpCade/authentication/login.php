<?php
session_start();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Send data to Spring Boot API
    $url = 'http://localhost:8080/api/login';
    $data = json_encode(['username' => $username, 'password' => $password]);

    $options = [
        'http' => [
            'header'  => "Content-type: application/json\r\n",
            'method'  => 'POST',
            'content' => $data,
        ],
    ];

    $context  = stream_context_create($options);
    $result = file_get_contents($url, false, $context);

    if ($result === FALSE) {
        die('Error calling Spring Boot API');
    }

    $response = json_decode($result, true);

    if ($response['status'] === 'success') {
        // Store user session in PHP
        $_SESSION['username'] = $username;
        echo json_encode(["status" => "success", "message" => "Login successful"]);
    } else {
        echo json_encode(["status" => "error", "message" => $response['message']]);
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
