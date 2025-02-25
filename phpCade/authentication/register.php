<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $password = $_POST['password'];
    $email = $_POST['email'];
    $role = $_POST['role'];
    $subjects = isset($_POST['subjects']) ? implode(", ", $_POST['subjects']) : NULL;
    $grade = isset($_POST['grade']) && $_POST['role'] == 'student' ? $_POST['grade'] : NULL;

    // Send data to Spring Boot API
    $url = 'http://localhost:8080/api/register';
    $data = json_encode([
        'username' => $username,
        'password' => $password,
        'email' => $email,
        'role' => $role,
        'subjects' => $subjects,
        'grade' => $grade,
    ]);

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
        echo json_encode(["status" => "success", "message" => "Registration successful"]);
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
    <title>Sign Up</title>
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
    </style>
    <script>
        function toggleFields() {
            let role = document.querySelector('input[name="role"]:checked').value;
            document.getElementById("subjectField").style.display = role === "teacher" ? "block" : "none";
            document.getElementById("gradeField").style.display = role === "student" ? "block" : "none";
        }
    </script>
</head>

<body>
    <h2>Sign Up</h2>
    <div>
        <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
            <label for="username"><b>Username:</b></label>
            <input type="text" id="username" name="username" required><br>

            <label for="email"><b>Email:</b></label>
            <input type="email" id="email" name="email" required><br>

            <label for="password"><b>Password:</b></label>
            <input type="password" id="password" name="password" required><br>

            <label for="role"><b>Select Role:</b></label><br>
            <label><input type="radio" name="role" value="student" required onchange="toggleFields()"> Student</label>
            <label><input type="radio" name="role" value="teacher" required onchange="toggleFields()"> Teacher</label>
            <label><input type="radio" name="role" value="admin" required onchange="toggleFields()"> Admin</label><br>

            <div id="subjectField" style="display:none;">
                <label for="subjects"><b>Subjects:</b></label><br>
                <label><input type="checkbox" name="subjects[]" value="Mathematics"> Mathematics</label><br>
                <label><input type="checkbox" name="subjects[]" value="Bulgarian Language and Literature"> Bulgarian</label><br>
                <label><input type="checkbox" name="subjects[]" value="English"> English</label><br>
                <label><input type="checkbox" name="subjects[]" value="German"> German</label><br>
                <label><input type="checkbox" name="subjects[]" value="Spanish"> Spanish</label><br>
                <label><input type="checkbox" name="subjects[]" value="Russian"> Russian</label><br>
                <label><input type="checkbox" name="subjects[]" value="History"> History</label><br>
                <label><input type="checkbox" name="subjects[]" value="Philosophy"> Philosophy</label><br>
                <label><input type="checkbox" name="subjects[]" value="Geography"> Geography</label><br>
                <label><input type="checkbox" name="subjects[]" value="Biology"> Biology</label><br>
                <label><input type="checkbox" name="subjects[]" value="Programming"> Programming</label><br>
                <label><input type="checkbox" name="subjects[]" value="UOOP"> UOOP</label><br>
                <label><input type="checkbox" name="subjects[]" value="UASD"> UASD</label><br>
                <label><input type="checkbox" name="subjects[]" value="Modern Web Technologies"> Modern Web Technologies</label><br>
                <label><input type="checkbox" name="subjects[]" value="IT"> IT</label><br>
            </div>
            
            <div id="gradeField" style="display:none;">
                <label for="grade"><b>Grade/Class:</b></label>
                <select id="grade" name="grade">
                    <?php for ($i = 1; $i <= 12; $i++) { echo "<option value='$i'>$i</option>"; } ?>
                </select><br>
            </div>

            <button type="submit" name="submit">Sign Up</button>
    <?php
    if (!empty($registration_message)) {
        echo "<p>$registration_message</p>";
    }
    ?>
    <p>Already have an account? <a href="login.php">Login here</a></p>
    </form>
    </div>
</body>
</html>
