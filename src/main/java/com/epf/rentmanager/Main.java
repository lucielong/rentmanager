
package com.epf.rentmanager;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new Client(12, "Julie", "DOLIGEZ", "julie.doligez@gmail.com", LocalDate.now()));
            System.out.println(VehicleService.getInstance().findAll());
            System.out.println(ClientService.getInstance().findById(2));
            System.out.println(ClientService.getInstance().findAll());
            System.out.println(VehicleService.getInstance().findById(2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }
    }

