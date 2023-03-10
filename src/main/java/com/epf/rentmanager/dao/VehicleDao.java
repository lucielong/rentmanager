package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";

	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setInt(2, vehicle.getNb_places());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				return resultSet.getLong(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule");
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicle.getId());
			preparedStatement.executeUpdate();
			return vehicle.getId();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule");
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Vehicle vehicule = new Vehicle();
				vehicule.setId((int) id);
				vehicule.setConstructeur(resultSet.getString("constructeur"));
				vehicule.setNb_places(resultSet.getInt("nb_places"));
				return vehicule;
			}
			return null;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule");
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(FIND_VEHICLES_QUERY);
			while (resultSet.next()){
				int id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				int nb_places = resultSet.getInt("nb_places");
				vehicles.add(new Vehicle(id, constructeur, nb_places));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicles;
	}

	public int count() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT_VEHICLES_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des véhicules");
		}
		return 0;
	}
}


