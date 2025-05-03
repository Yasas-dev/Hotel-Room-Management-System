<%@ page import="java.io.*, java.util.*, entity.*, java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    int roomId = Integer.parseInt(request.getParameter("roomId"));
    String newName = request.getParameter("name");
    String newPhone = request.getParameter("phone");
    LocalDate newCheckout = LocalDate.parse(request.getParameter("checkout"));

    File file = new File("C:\\Users\\USER\\Desktop\\final project\\HotelRoomManagementApp\\src\\main\\webapp\\Guests.txt");
    List<String> updatedLines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int roomNumber = Integer.parseInt(parts[5].trim());

            if (roomNumber == roomId) {
                // Parse original values
                String roomType = parts[4].trim();
                LocalDate oldCheckIn = LocalDate.parse(parts[2].trim());
                LocalDate oldCheckOut = LocalDate.parse(parts[3].trim());
                double oldPrice = Double.parseDouble(parts[6].trim());

                long extraDays = oldCheckOut.until(newCheckout).getDays();
                double newPrice = oldPrice + (extraDays * 1000);

                // Rebuild line
                String newLine = String.join(",", newName, newPhone, oldCheckIn.toString(), newCheckout.toString(), roomType, String.valueOf(roomNumber), String.valueOf(newPrice));
                updatedLines.add(newLine);
            } else {
                updatedLines.add(line); // Keep other guests unchanged
            }
        }
    }

    // Write updated lines back to the file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String updatedLine : updatedLines) {
            writer.write(updatedLine);
            writer.newLine();
        }
    }

    // Redirect back to dashboard
    response.sendRedirect("guestDashboard.jsp");
%>

