<%@ page import="java.io.*, java.util.*, entity.*, java.time.LocalDate" %>
<%@ page import="util.GuestSort" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Guest Dashboard</title>

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
        }

        h2 {
            color: #0288d1;
            font-weight: 600;
        }

        table {
            background-color: #ffffff;
            border-radius: 8px;
            width: 100%;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 15px;
            text-align: left;
        }

        th {
            background-color: #81d4fa;
            color: #01579b;
            font-size: 16px;
        }

        td {
            background-color: #f1f8e9;
            font-size: 14px;
        }

        tr:nth-child(even) td {
            background-color: #e3f2fd;
        }

        tr:hover td {
            background-color: #b3e5fc;
        }

        .btn-primary {
            background-color: #0288d1;
            border-color: #0288d1;
        }

        .btn-primary:hover {
            background-color: #01579b;
            border-color: #01579b;
        }

        .bi-pencil-square, .bi-trash {
            font-size: 18px;
            color: #0288d1;
            margin-right: 10px;
        }

        .bi-pencil-square:hover, .bi-trash:hover {
            color: #01579b;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mt-5">Registered Guests</h2>
    <table class="table table-striped mt-4">
        <thead>
        <tr>
            <th>Name</th>
            <th>Phone</th>
            <th>Check-in Date</th>
            <th>Check-out Date</th>
            <th>Room Type</th>
            <th>Room Number</th>
            <th>Total Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            File file = new File("C:/Users/USER/Desktop/final project/HotelRoomManagementApp/src/main/webapp/Guests.txt");
            List<Guest> guests = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String name = parts[0].trim();
                    String phone = parts[1].trim();
                    LocalDate checkIn = LocalDate.parse(parts[2].trim());
                    LocalDate checkOut = LocalDate.parse(parts[3].trim());
                    String roomType = parts[4].trim();
                    int roomNumber = Integer.parseInt(parts[5].trim());

                    Guest guest = roomType.equalsIgnoreCase("vip") ?
                            new VipGuest(name, phone, checkIn, checkOut, roomNumber) :
                            new NormalGuest(name, phone, checkIn, checkOut, roomNumber);
                    guests.add(guest);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            GuestSort.sortGuestsByCheckInDate(guests);

            for (Guest guest : guests) {
        %>
        <tr>
            <td><%= guest.getName() %></td>
            <td><%= guest.getPhone() %></td>
            <td><%= guest.getCheckIn() %></td>
            <td><%= guest.getCheckOut() %></td>
            <td><%= guest instanceof VipGuest ? "VIP" : "Normal" %></td>
            <td><%= guest.getRoomNumber() %></td>
            <td><%= guest.calculatePrice() %></td>
            <td>
                <a href="editGuest.jsp?id=<%= guest.getRoomNumber() %>" class="bi bi-pencil-square"></a>
                <a href="deleteGuest?id=<%= guest.getRoomNumber() %>" class="bi bi-trash"
                   onclick="return confirm('Are you sure you want to delete this guest?');"></a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <!-- Back to Home Button -->
    <a href="index.jsp" class="btn btn-primary w-100 mt-4">Back to Home</a>
</div>
</body>
</html>