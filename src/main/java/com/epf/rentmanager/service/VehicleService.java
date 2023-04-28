package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service

public class VehicleService {

    private final VehicleDao vehicleDao;

    private VehicleService(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }


    public long create(Vehicle vehicle) throws ServiceException {

        if (vehicle.getConstructeur().length() < 1) {
            throw new ServiceException("Le constructeur doit contenir au moins 1 caractère");
        }
        if (2 > vehicle.getNb_places() || vehicle.getNb_places() > 9) {
            throw new ServiceException("La voiture doit contenir entre 2 et 9 places");
        }
        try {
            return vehicleDao.create(vehicle);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création du véhicule");
        }

    }

    public void update(Vehicle vehicle) throws ServiceException {

        if (vehicle.getConstructeur().length() < 1) {
            throw new ServiceException("Le constructeur doit contenir au moins 1 caractère");
        }
        if (vehicle.getNb_places() < 1) {
            throw new ServiceException("La voiture doit contenir au moins 1 place");
        }
        try {
            vehicleDao.update(vehicle);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la mise à jour du vehicule");
        }
    }

    public void delete(long id) throws ServiceException {
        try {
            vehicleDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression du vehicule");
        }
    }

    public Vehicle findById(long id) throws ServiceException {
        try {
            return vehicleDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération du véhicule");
        }

    }

    public List<Vehicle> findAll() throws ServiceException {
        try {
            return vehicleDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération des véhicules");
        }
    }

    public int count() throws ServiceException {
        try {
            return vehicleDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des véhicules");
        }
    }

    public int countVehicleByClientId(long clientId) throws ServiceException {
        try {
            return vehicleDao.countVehicleByClientId(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des véhicules");
        }
    }


}
