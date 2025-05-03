package com.hotel;

import entity.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDate;

@WebServlet("/RegisterGuestServlet")
public class RegisterGuestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            LocalDate checkIn = LocalDate.parse(request.getParameter("checkin"));
            LocalDate checkOut = LocalDate.parse(request.getParameter("checkout"));
            int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
            String roomType = request.getParameter("roomType");

            Guest guest = roomType.equalsIgnoreCase("vip") ?
                    new VipGuest(name, phone, checkIn, checkOut, roomNumber) :
                    new NormalGuest(name, phone, checkIn, checkOut, roomNumber);

            File file = new File("C:\\Users\\USER\\Desktop\\final project\\HotelRoomManagementApp\\src\\main\\webapp\\Guests.txt");
            file.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(guest.getSummary());
                writer.newLine();
            }

            response.sendRedirect("guestDashboard.jsp");
        } catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().println("<html><body><h3>Error: " + e.getMessage() + "</h3></body></html>");
        }
    }
}