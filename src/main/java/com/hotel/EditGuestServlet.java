package com.hotel;

import entity.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/EditGuestServlet")
public class EditGuestServlet extends HttpServlet {

    private final String FILE_PATH = "C:/Users/USER/Desktop/final project/HotelRoomManagementApp/src/main/webapp/Guests.txt";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String newName = request.getParameter("name").trim();
        String newPhone = request.getParameter("phone").trim();
        LocalDate newCheckout = LocalDate.parse(request.getParameter("checkout"));
        LocalDate oldCheckout = LocalDate.parse(request.getParameter("oldCheckout"));

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentRoom = Integer.parseInt(parts[5].trim());

                if (currentRoom == roomId) {
                    // Update this guest
                    String roomType = parts[4].trim();
                    LocalDate checkIn = LocalDate.parse(parts[2].trim());
                    double oldPrice = Double.parseDouble(parts[6].trim());

                    long extraDays = oldCheckout.until(newCheckout).getDays();
                    double newPrice = oldPrice + (extraDays > 0 ? extraDays * 1000 : 0);

                    String updatedLine = String.join(",",
                            newName,
                            newPhone,
                            checkIn.toString(),
                            newCheckout.toString(),
                            roomType,
                            String.valueOf(roomId),
                            String.valueOf(newPrice)
                    );

                    updatedLines.add(updatedLine);
                } else {
                    updatedLines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }

        response.sendRedirect("guestDashboard.jsp");
    }
}
