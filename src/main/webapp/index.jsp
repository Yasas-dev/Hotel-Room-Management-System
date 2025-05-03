<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hotel Employee Portal</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .header {
            text-align: right;
            padding: 10px 30px;
            font-size: 20px;
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .header a {
            text-decoration: none;
            color: #333;
        }

        .container {
            text-align: center;
            margin-top: 100px;
        }

        .box {
            display: inline-block;
            background-color: #fff;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .box a {
            display: block;
            margin: 20px 0;
            padding: 15px 30px;
            font-size: 16px;
            text-decoration: none;
            color: white;
            background-color: #007bff;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .box a:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>

<div class="header">
    <a href="guestDashboard.jsp">BlueWave Hotel</a>
</div>

<div class="container">
    <div class="box">
        <a href="registerGuest.jsp">Register Guest</a>
        <a href="guestDashboard.jsp">Remove Guest</a>
        <!-- Add more staff tools here if needed -->
    </div>
</div>

</body>
</html>
