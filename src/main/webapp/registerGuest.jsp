registerGuest.jsp
<%@ page import="java.io.*, java.util.*, entity.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register Guest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #e1f5fe;
            color: #333;
        }

        .container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
            margin-bottom: 50px;
        }

        h3 {
            color: #0288d1;
            font-weight: 600;
            text-align: center;
            margin-bottom: 25px;
        }

        .form-label {
            font-weight: 500;
        }

        .btn-primary {
            background-color: #0288d1;
            border-color: #0288d1;
            border-radius: 0.75rem;
        }

        .btn-primary:hover {
            background-color: #01579b;
            border-color: #01579b;
        }

        .hotel-name {
            position: absolute;
            top: 20px;
            right: 30px;
            font-weight: 600;
            font-size: 1rem;
        }

        #totalPrice {
            font-weight: 600;
            color: #0288d1;
        }

        .error-message {
            color: red;
            font-weight: 600;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>

    <script>
        function calculateTotalPrice() {
            var roomType = document.getElementById('roomType').value;
            var checkinDate = new Date(document.getElementById('checkin').value);
            var checkoutDate = new Date(document.getElementById('checkout').value);
            var roomPrice = 0;
            var totalPrice = 0;

            if (checkinDate && checkoutDate && checkinDate < checkoutDate) {
                var timeDiff = checkoutDate - checkinDate;
                var dayDiff = timeDiff / (1000 * 3600 * 24);

                roomPrice = (roomType === 'vip') ? 1000 : 500;
                totalPrice = roomPrice * dayDiff;
                document.getElementById('totalPrice').innerHTML = "Total Price: " + totalPrice + " USD";
            } else {
                document.getElementById('totalPrice').innerHTML = "Total Price: -";
            }
        }
    </script>
</head>
<body>
<a href="index.jsp" class="hotel-name text-decoration-none text-dark">Blue Wave Hotel</a>

<div class="container col-md-6 offset-md-3">
    <h3>Register Guest</h3>

    <% String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>

    <form action="RegisterGuestServlet" method="POST">
        <div class="mb-3">
            <label for="name" class="form-label">Name:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label">Phone Number:</label>
            <input type="text" class="form-control" id="phone" name="phone" required>
        </div>
        <div class="mb-3">
            <label for="checkin" class="form-label">Check-in Date:</label>
            <input type="date" class="form-control" id="checkin" name="checkin" required onchange="calculateTotalPrice()">
        </div>
        <div class="mb-3">
            <label for="checkout" class="form-label">Check-out Date:</label>
            <input type="date" class="form-control" id="checkout" name="checkout" required onchange="calculateTotalPrice()">
        </div>
        <div class="mb-3">
            <label for="roomNumber" class="form-label">Room Number:</label>
            <select class="form-select" id="roomNumber" name="roomNumber" required>
                <option value="" disabled selected>Select Room</option>
                <% for (int i = 1; i <= 20; i++) { %>
                <option value="<%= i %>"><%= i %></option>
                <% } %>
            </select>
        </div>
        <div class="mb-3">
            <label for="roomType" class="form-label">Room Type:</label>
            <select class="form-select" id="roomType" name="roomType" required onchange="calculateTotalPrice()">
                <option value="normal">Normal</option>
                <option value="vip">VIP</option>
            </select>
        </div>
        <div class="mb-3">
            <span id="totalPrice">Total Price: -</span>
        </div>
        <button type="submit" class="btn btn-primary w-100">Register Guest</button>
    </form>
</div>
</body>
</html>