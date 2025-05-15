package com.hotel;

import entity.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/deleteGuest")
public class DeleteGuestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameter - changed from roomNumber to id
            int roomNumber = Integer.parseInt(request.getParameter("id"));

            if (GuestServices.deleteGuest(roomNumber)) {
                response.sendRedirect("guestDashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Failed to delete guest.");
                request.getRequestDispatcher("guestDashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("guestDashboard.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Handle POST the same as GET
    }
}