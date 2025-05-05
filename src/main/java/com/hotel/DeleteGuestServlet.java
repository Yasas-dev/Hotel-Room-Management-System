package com.hotel;

import entity.RoomBST;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet("/deleteGuest")
public class DeleteGuestServlet extends HttpServlet {
    private static final String FILE_PATH = "C:\\Users\\USER\\Desktop\\final project\\HotelRoomManagementApp\\src\\main\\webapp\\Guests.txt";
    private static final RoomBST roomTree = new RoomBST(); // Add this

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                int roomNumberToDelete = Integer.parseInt(idParam);
                List<String> updatedGuests = new ArrayList<>();

                // Read and filter guest lines
                try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 6) {
                            int roomNumber = Integer.parseInt(parts[5].trim());
                            if (roomNumber != roomNumberToDelete) {
                                updatedGuests.add(line);
                            }
                        }
                    }
                }

                // Write back updated list
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
                    for (String guestLine : updatedGuests) {
                        writer.write(guestLine);
                        writer.newLine();
                    }
                }

                // Mark room as available again
                roomTree.releaseRoom(roomNumberToDelete);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("guestDashboard.jsp");
    }
}
