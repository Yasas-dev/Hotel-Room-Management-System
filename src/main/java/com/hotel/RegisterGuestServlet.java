package com.hotel;

import entity.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDate;

@WebServlet("/RegisterGuestServlet")
public class RegisterGuestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            LocalDate checkIn = LocalDate.parse(request.getParameter("checkin"));
            LocalDate checkOut = LocalDate.parse(request.getParameter("checkout"));
            int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
            String roomType = request.getParameter("roomType");

            if (phone.length() != 10) {
                request.setAttribute("errorMessage", "Phone number must have exactly 10 digits.");
                request.getRequestDispatcher("registerGuest.jsp").forward(request, response);
                return;
            }

            if (!GuestServices.isRoomAvailable(roomNumber)) {
                request.setAttribute("errorMessage", "Room " + roomNumber + " is already booked!");
                request.getRequestDispatcher("registerGuest.jsp").forward(request, response);
                return;
            }

            Guest guest = roomType.equalsIgnoreCase("vip") ?
                    new VipGuest(name, phone, checkIn, checkOut, roomNumber) :
                    new NormalGuest(name, phone, checkIn, checkOut, roomNumber);

            if (GuestServices.registerGuest(guest)) {
                response.sendRedirect("guestDashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("registerGuest.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("registerGuest.jsp").forward(request, response);
        }
    }
}