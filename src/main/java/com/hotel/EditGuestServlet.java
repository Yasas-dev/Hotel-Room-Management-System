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

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String newName = request.getParameter("newName");
        String newPhone = request.getParameter("newPhone");
        String newCheckoutStr = request.getParameter("newCheckout");
        String oldCheckoutStr = request.getParameter("oldCheckout");

        try {
            LocalDate newCheckout = LocalDate.parse(newCheckoutStr);
            LocalDate oldCheckout = LocalDate.parse(oldCheckoutStr);

            // Validation: Phone number length
            if (newPhone.length() != 10) {
                request.setAttribute("errorMessage", "Phone number must be 10 digits");
                request.getRequestDispatcher("editGuest.jsp?id=" + roomId).forward(request, response);
                return;
            }

            // Validation: Checkout date
            if (newCheckout.isBefore(oldCheckout)) {
                request.setAttribute("errorMessage", "New checkout date cannot be before the current date");
                request.getRequestDispatcher("editGuest.jsp?id=" + roomId).forward(request, response);
                return;
            }

            // Try to update guest
            if (GuestServices.updateGuest(roomId, newName, newPhone, newCheckout, oldCheckout)) {
                response.sendRedirect("guestDashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Failed to update guest information");
                request.getRequestDispatcher("editGuest.jsp?id=" + roomId).forward(request, response);
            }


        } catch (Exception e) {
            request.setAttribute("errorMessage", "Something went wrong: " + e.getMessage());
            request.getRequestDispatcher("editGuest.jsp?id=" + roomId).forward(request, response);
        }
    }
}
