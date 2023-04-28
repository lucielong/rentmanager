package com.epf.rentmanager.servlet;
import java.util.List;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/delete")
public class UserDeleteServlet extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Integer.parseInt(request.getParameter("id"));
            List<Reservation> resaClient = reservationService.findResaByClientId(Integer.parseInt(request.getParameter("id")));
            for (Reservation resa : resaClient ){
                reservationService.delete(resa.getId());
            }
            clientService.delete(id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }

}
