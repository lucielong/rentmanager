package com.epf.rentmanager.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException {
        try {
            if (reservation.getDebut().isAfter(reservation.getFin())) {
                throw new ServiceException("La date de début doit être avant la date de fin");
            }
            if (Period.between(reservation.getDebut(), reservation.getFin()).getDays() > 7) {
                throw new ServiceException("La réservation ne peut pas durer plus de 7 jours consécutifs");
            }
            List<Reservation> existingReservations = reservationDao.findResaByVehicleId(reservation.getVehicle_id());
            for (Reservation existingReservation : existingReservations) {
                if (reservation.getDebut().isEqual(existingReservation.getDebut()) || reservation.getFin().isEqual(existingReservation.getFin()) || reservation.getDebut().isEqual(existingReservation.getFin()) || reservation.getFin().isEqual(existingReservation.getDebut()) || (reservation.getDebut().isAfter(existingReservation.getDebut()) && reservation.getDebut().isBefore(existingReservation.getFin())) || (reservation.getFin().isAfter(existingReservation.getDebut()) && reservation.getFin().isBefore(existingReservation.getFin())) || (reservation.getDebut().isBefore(existingReservation.getDebut()) && reservation.getFin().isAfter(existingReservation.getFin()))) {
                    throw new ServiceException("La voiture est déjà réservée pour cette période");
                }
                if (reservation.getClient_id() == existingReservation.getClient_id()) {
                    if (reservation.getDebut().isEqual(existingReservation.getFin().plusDays(1)) && (Period.between(reservation.getDebut(), reservation.getFin()).getDays() + Period.between(existingReservation.getDebut(), existingReservation.getFin()).getDays()) > 7) {
                        throw new ServiceException("La réservation ne peut pas durer plus de 7 jours consécutifs");
                    }
                }
                List<Reservation> reservations = reservationDao.findResaByVehicleId(reservation.getVehicle_id());
                for (Reservation res : reservations) {
                    if (res.getClient_id() == reservation.getClient_id()) {
                        if (res.getFin().plusDays(1).until(reservation.getDebut(), ChronoUnit.DAYS) <= 30) {
                            throw new ServiceException("La voiture ne peut pas être réservée plus de 30 jours consécutifs sans pause");
                        }
                    }
                }
            }
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création de la réservation");
        }
    }

    public void update(Reservation reservation) throws ServiceException {
        try {
            if (reservation.getDebut().isAfter(reservation.getFin())) {
                throw new ServiceException("La date de début doit être avant la date de fin");
            }
            if (Period.between(reservation.getDebut(), reservation.getFin()).getDays() > 7) {
                throw new ServiceException("La réservation ne peut pas durer plus de 7 jours consécutifs");
            }
            List<Reservation> existingReservations = reservationDao.findResaByVehicleId(reservation.getVehicle_id());
            for (Reservation existingReservation : existingReservations) {
                if (reservation.getDebut().isEqual(existingReservation.getDebut()) || reservation.getFin().isEqual(existingReservation.getFin()) || reservation.getDebut().isEqual(existingReservation.getFin()) || reservation.getFin().isEqual(existingReservation.getDebut()) || (reservation.getDebut().isAfter(existingReservation.getDebut()) && reservation.getDebut().isBefore(existingReservation.getFin())) || (reservation.getFin().isAfter(existingReservation.getDebut()) && reservation.getFin().isBefore(existingReservation.getFin())) || (reservation.getDebut().isBefore(existingReservation.getDebut()) && reservation.getFin().isAfter(existingReservation.getFin()))) {
                    throw new ServiceException("La voiture est déjà réservée pour cette période");
                }
                if (reservation.getClient_id() == existingReservation.getClient_id()) {
                    if (reservation.getDebut().isEqual(existingReservation.getFin().plusDays(1)) && (Period.between(reservation.getDebut(), reservation.getFin()).getDays() + Period.between(existingReservation.getDebut(), existingReservation.getFin()).getDays()) > 7) {
                        throw new ServiceException("La réservation ne peut pas durer plus de 7 jours consécutifs");
                    }
                }
                List<Reservation> reservations = reservationDao.findResaByVehicleId(reservation.getVehicle_id());
                for (Reservation res : reservations) {
                    if (res.getClient_id() == reservation.getClient_id()) {
                        if (res.getFin().plusDays(1).until(reservation.getDebut(), ChronoUnit.DAYS) <= 30) {
                            throw new ServiceException("La voiture ne peut pas être réservée plus de 30 jours consécutifs sans pause");
                        }
                    }
                }
            }
            reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la mise à jour de la réservation");
        }
    }

    public void delete(long id) throws ServiceException {
        try {
            reservationDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression de la réservation");
        }
    }

    public Reservation findById(long id) throws ServiceException {
        if (id < 0) {
            throw new ServiceException("L'id doit être positif");
        }
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération de la réservation");
        }
    }

    public List<Reservation> findResaByClientId(int client_id) throws ServiceException {
        if (client_id < 0) {
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
        if (vehicle_id < 0) {
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

    public int countResaByVehicleId(int client_id) throws ServiceException {
        try {
            return reservationDao.countResaByVehicleId(client_id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des réservations");
        }
    }

}

