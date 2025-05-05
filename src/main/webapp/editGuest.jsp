<%@ page import="java.io.*, java.util.*, entity.*, java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    int roomId = Integer.parseInt(request.getParameter("id"));
    Guest editingGuest = null;

    File file = new File("C:/Users/USER/Desktop/final project/HotelRoomManagementApp/src/main/webapp/Guests.txt");
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[5].trim()) == roomId) {
                String name = parts[0].trim();
                String phone = parts[1].trim();
                LocalDate checkIn = LocalDate.parse(parts[2].trim());
                LocalDate checkOut = LocalDate.parse(parts[3].trim());
                String roomType = parts[4].trim();
                editingGuest = roomType.equalsIgnoreCase("vip") ?
                        new VipGuest(name, phone, checkIn, checkOut, roomId) :
                        new NormalGuest(name, phone, checkIn, checkOut, roomId);
                break;
            }
        }
    }

    if (editingGuest == null) {
        System.out.println("<h2>Guest not found for Room ID: " + roomId + "</h2>");
        return;
    }

    LocalDate checkIn = editingGuest.getCheckIn();
    LocalDate oldCheckOut = editingGuest.getCheckOut();
    LocalDate minNewCheckout = checkIn.plusDays(1);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Guest Info</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap CSS and Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #e1f5fe;
            font-family: 'Segoe UI', sans-serif;
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

        .container-box {
            max-width: 600px;
            background-color: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 6px 12px rgba(0,0,0,0.1);
            margin: 60px auto;
        }

        .container-box h2 {
            text-align: center;
            color: #01579b;
            margin-bottom: 30px;
        }

        .container-box i {
            font-size: 60px;
            color: #0288d1;
            display: block;
            text-align: center;
            margin-bottom: 10px;
        }

        .form-label {
            font-weight: 600;
        }

        .btn-primary {
            background-color: #0288d1;
            border: none;
        }

        .btn-primary:hover {
            background-color: #01579b;
        }
    </style>
</head>
<body>

<div class="header">
    <a href="index.jsp">BlueWave Hotel</a>
</div>

<div class="container-box">
    <i class="bi bi-pencil-square"></i>
    <h2>Edit Guest Info</h2>

    <form action="EditGuestServlet" method="POST">
        <input type="hidden" name="roomId" value="<%= editingGuest.getRoomNumber() %>">
        <input type="hidden" name="oldCheckout" value="<%= oldCheckOut %>">

        <div class="mb-3">
            <label class="form-label">Guest Name:</label>
            <input type="text" class="form-control" name="name" value="<%= editingGuest.getName() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Phone Number:</label>
            <input type="text" class="form-control" name="phone" value="<%= editingGuest.getPhone() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Previous Checkout Date:</label>
            <input type="text" class="form-control" value="<%= oldCheckOut %>" readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">New Checkout Date (After <%= checkIn %>):</label>
            <input type="date" class="form-control" name="checkout"
                   value="<%= oldCheckOut %>"
                   min="<%= minNewCheckout %>"
                   required>
        </div>

        <button type="submit" class="btn btn-primary w-100">Save Changes</button>
    </form>
</div>

</body>
</html>
