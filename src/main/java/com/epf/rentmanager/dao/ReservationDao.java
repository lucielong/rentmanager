package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class ReservationDao {


    private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
    private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
    private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
    private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";

    private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
    private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
    private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";


    private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";
    private static final String COUNT_RESERVATIONS_BY_CLIENT_QUERY = "SELECT COUNT(id) AS count FROM Reservation WHERE client_id=?;";
    private static final String COUNT_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT COUNT(id) AS count FROM Reservation WHERE vehicle_id=?;";

    public long create(Reservation reservation) throws DaoException {
        long id = 0;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, reservation.getClient_id());
            preparedStatement.setLong(2, reservation.getVehicle_id());
            preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
            preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la création de la réservation");
        }
    }

    public void update(Reservation reservation) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESERVATION_QUERY);
            preparedStatement.setLong(1, reservation.getClient_id());
            preparedStatement.setLong(2, reservation.getVehicle_id());
            preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
            preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
            preparedStatement.setLong(5, reservation.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la mise à jour de la réservation");
        }
    }

    public void delete(long id) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la suppression de la réservation");
        }
    }

    public Reservation findById(long id) throws DaoException {
        Reservation reservation = new Reservation();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATION_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservation.setId((int) id);
                reservation.setClient_id(resultSet.getInt("client_id"));
                reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
                reservation.setDebut(resultSet.getDate("debut").toLocalDate());
                reservation.setFin(resultSet.getDate("fin").toLocalDate());
            }
            return reservation;
        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la récupération de la réservation");
        }
    }

    public List<Reservation> findResaByClientId(int client_id) throws DaoException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
            preparedStatement.setInt(1, client_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setClient_id((int) client_id);
                reservation.setId(resultSet.getInt("id"));
                reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
                reservation.setDebut(resultSet.getDate("debut").toLocalDate());
                reservation.setFin(resultSet.getDate("fin").toLocalDate());
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    public List<Reservation> findResaByVehicleId(long vehicle_id) throws DaoException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
            preparedStatement.setLong(1, vehicle_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setVehicle_id((int) vehicle_id);
                reservation.setId(resultSet.getInt("id"));
                reservation.setClient_id(resultSet.getInt("client_id"));
                reservation.setDebut(resultSet.getDate("debut").toLocalDate());
                reservation.setFin(resultSet.getDate("fin").toLocalDate());
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public List<Reservation> findAll() throws DaoException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        try {
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_RESERVATIONS_QUERY);
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
                reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
                reservation.setClient_id(resultSet.getInt("client_id"));
                reservation.setDebut(resultSet.getDate("debut").toLocalDate());
                reservation.setFin(resultSet.getDate("fin").toLocalDate());
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public int count() throws DaoException {
        int nbResa = 0;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nbResa = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors du comptage des réservations");
        }
        return nbResa;
    }

    public int countResaByClientId(int client_id) throws DaoException {
        int nbResa = 0;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_BY_CLIENT_QUERY);
            preparedStatement.setInt(1, client_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nbResa = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors du comptage des réservations");
        }
        return nbResa;
    }

    public int countResaByVehicleId(int client_id) throws DaoException {
        int nbResa = 0;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_BY_VEHICLE_QUERY);
            preparedStatement.setInt(1, client_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nbResa = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors du comptage des réservations");
        }
        return nbResa;
    }


}
