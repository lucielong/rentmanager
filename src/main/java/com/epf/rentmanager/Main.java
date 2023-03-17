
package com.epf.rentmanager;
import com.epf.rentmanager.Configuration.AppConfiguration;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
            ClientService clientService = context.getBean(ClientService.class);
            VehicleService vehicleService = context.getBean(VehicleService.class);
            System.out.println(new Client(12, "Julie", "DOLIGEZ", "julie.doligez@gmail.com", LocalDate.now()));
            System.out.println(vehicleService.findAll());
            System.out.println(clientService.findById(2));
            System.out.println(clientService.findAll());
            System.out.println(vehicleService.findById(2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }
    }

