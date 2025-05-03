<%@ page import="java.io.*, java.util.*, entity.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Guest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        // Function to calculate total price
        function calculateTotalPrice() {
            var roomType = document.getElementById('roomType').value;
            var checkinDate = new Date(document.getElementById('checkin').value);
            var checkoutDate = new Date(document.getElementById('checkout').value);
            var roomPrice = 0;
            var totalPrice = 0;

            // Check if both dates are selected
            if (checkinDate && checkoutDate && checkinDate < checkoutDate) {
                // Calculate the difference in days
                var timeDiff = checkoutDate - checkinDate;
                var dayDiff = timeDiff / (1000 * 3600 * 24); // Days difference

                // Set room price based on room type
                if (roomType === 'vip') {
                    roomPrice = 1000; // VIP room price per day
                } else {
                    roomPrice = 500; // Normal room price per day
                }

                // Calculate total price
                totalPrice = roomPrice * dayDiff;
                document.getElementById('totalPrice').innerHTML = "Total Price: " + totalPrice + " USD";
            } else {
                document.getElementById('totalPrice').innerHTML = "Total Price: -";
            }
        }
    </script>
</head>
<body>
<div class="container mt-5">
    <h2>Register Guest</h2>
    <form action="RegisterGuestServlet" method="POST">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="phone">Phone Number:</label>
            <input type="text" class="form-control" id="phone" name="phone" required>
        </div>
        <div class="form-group">
            <label for="checkin">Check-in Date:</label>
            <input type="date" class="form-control" id="checkin" name="checkin" required onchange="calculateTotalPrice()">
        </div>
        <div class="form-group">
            <label for="checkout">Check-out Date:</label>
            <input type="date" class="form-control" id="checkout" name="checkout" required onchange="calculateTotalPrice()">
        </div>
        <div class="form-group">
            <label for="roomNumber">Room Number:</label>
            <select class="form-control" id="roomNumber" name="roomNumber" required>
                <option value="" disabled selected>Select Room</option>
                <% for (int i = 1; i <= 20; i++) { %>
                <option value="<%= i %>"><%= i %></option>
                <% } %>
            </select>
        </div>
        <div class="form-group">
            <label for="roomType">Room Type:</label>
            <select class="form-control" id="roomType" name="roomType" required onchange="calculateTotalPrice()">
                <option value="normal">Normal</option>
                <option value="vip">VIP</option>
            </select>
        </div>
        <div class="form-group mt-3">
            <span id="totalPrice">Total Price: -</span>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Register Guest</button>
    </form>
</div>
</body>
</html>
