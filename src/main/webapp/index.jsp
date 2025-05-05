<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hotel Employee Portal</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap CSS and Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #e1f5fe;
            margin: 0;
            padding: 0;
        }

        .header {
            text-align: right;
            padding: 15px 30px;
            font-size: 20px;
            background-color: #ffffff;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .header a {
            text-decoration: none;
            color: #0288d1;
            font-weight: 600;
            font-size: 22px;
        }

        .welcome-section {
            text-align: center;
            padding: 60px 20px 40px 20px;
        }

        .welcome-section i {
            font-size: 100px;
            color: #0288d1;
            margin-bottom: 20px;
        }

        .welcome-section h1 {
            font-size: 36px;
            color: #01579b;
            font-weight: 700;
        }

        .welcome-section p {
            font-size: 18px;
            color: #555;
            max-width: 700px;
            margin: 20px auto;
        }

        .action-box {
            background-color: #ffffff;
            padding: 30px 50px;
            border-radius: 12px;
            box-shadow: 0 6px 12px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 400px;
            margin: 0 auto 60px auto;
        }

        .action-box a {
            display: block;
            margin: 20px 0;
            padding: 15px;
            font-size: 16px;
            text-decoration: none;
            color: #ffffff;
            background-color: #0288d1;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .action-box a:hover {
            background-color: #01579b;
        }
    </style>
</head>
<body>

<div class="header">
    <a href="index.jsp">BlueWave Hotel</a>
</div>

<!-- Welcome Section -->
<div class="welcome-section">
    <i class="bi bi-building"></i>
    <h1>Welcome to the Hotel Employee Portal</h1>
    <p>
        Project Group 124 Hotel Room Management System. Easily manage guest registrations, room assignments, and stay records. Use the tools below to handle your daily tasks efficiently.
    </p>
</div>

<!-- Action Buttons -->
<div class="action-box">
    <a href="registerGuest.jsp"><i class="bi bi-person-plus"></i> Register Guest</a>
    <a href="guestDashboard.jsp"><i class="bi bi-person-dash"></i> Remove Guest</a>
</div>

</body>
</html>
