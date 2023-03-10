package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ReservationService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rents")
public class ReservationServlet extends HttpServlet {

        /**
        *
        */
        private static final long serialVersionUID = 1L;

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            try {
                request.setAttribute("rents", ReservationService.getInstance().findAll());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        }
}
