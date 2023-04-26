package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

        private static ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }

        /*public static ReservationService instance;*/

        /*private ReservationService() { reservationDao = ReservationDao.getInstance(); }*/

        /*public static ReservationService getInstance() {
            if (instance == null) {
                instance = new ReservationService();
            }

            return instance;
        }*/

        public long create(Reservation reservation) throws ServiceException {
            try {
                return reservationDao.create(reservation);
            } catch (DaoException e) {
                throw new ServiceException("Erreur lors de la création de la réservation");
            }
        }

    public List<Reservation> findResaByClientId(int client_id) throws ServiceException {
        // TODO: récupérer un client par son id
        if(client_id<0){
            throw new ServiceException("L'id doit être positif");
        }
        try {
            return reservationDao.findResaByClientId(client_id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur lors de la récupération des réservations");
        }
    }

    public List<Reservation> findResaByVehicleId(int vehicle_id) throws ServiceException {
        if(vehicle_id<0){
            throw new ServiceException("L'id doit être positif");
        }
        try {
            return reservationDao.findResaByVehicleId(vehicle_id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur lors de la récupération des réservations");
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        // TODO: récupérer tous les clients
        try {

            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération des réservations");
        }
    }

    public int count() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des réservations");
        }
    }

    public int countResaByClientId(int client_id) throws ServiceException {
        try {
            return reservationDao.countResaByClientId(client_id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des réservations");
        }
    }

}

