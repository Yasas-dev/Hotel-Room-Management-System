package com.hotel;

import entity.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDate;

@WebServlet("/EditGuestServlet")
public class EditGuestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String newName = request.getParameter("newName");
            String newPhone = request.getParameter("newPhone");
            LocalDate newCheckout = LocalDate.parse(request.getParameter("newCheckout"));
            LocalDate oldCheckout = LocalDate.parse(request.getParameter("oldCheckout"));

            if (newPhone.length() != 10) {
                throw new Exception("Phone number must be 10 digits");
            }

            if (newCheckout.isBefore(oldCheckout)) {
                throw new Exception("New checkout date cannot be before current date");
            }

            if (GuestServices.updateGuest(roomId, newName, newPhone, newCheckout, oldCheckout)) {
                response.sendRedirect("guestDashboard.jsp");
            } else {
                throw new Exception("Failed to update guest information");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("editGuest.jsp?id=" + request.getParameter("roomId"))
                    .forward(request, response);
        }
    }
}