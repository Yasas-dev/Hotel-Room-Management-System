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
    <title>Edit Guest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
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

        <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>
</div>
</body>
</html>
