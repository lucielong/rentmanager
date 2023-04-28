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
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";

	private static final String FIND_CLIENT_BY_EMAIL_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE email=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	private static final String COUNT_CLIENTS_BY_VEHICLE_QUERY = "SELECT COUNT(id) AS count FROM Client WHERE id IN (SELECT client_id FROM Reservation WHERE vehicle_id=?);";
	
	public long create(Client client) throws DaoException {
		long clientId = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, client.getNom().toUpperCase());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				clientId = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client");
		}
		return clientId;
	}

	public void update(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_QUERY);
			preparedStatement.setString(1, client.getNom().toUpperCase());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			preparedStatement.setLong(5, client.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la mise à jour du client");
		}
	}
	
	public void delete(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client");
		}
	}

	public Client findById(long id) throws DaoException {
		Client client = new Client();
		try {
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);
				preparedStatement.setLong(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					client.setId((int) id);
					client.setNom(resultSet.getString("nom"));
					client.setPrenom(resultSet.getString("prenom"));
					client.setEmail(resultSet.getString("email"));
					client.setNaissance(resultSet.getDate("naissance").toLocalDate());
				}
				return client;
			} catch (SQLException e) {
				throw new DaoException("Erreur lors de la recherche du client");
			}
	}

	public Client findByEmail(String email) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_BY_EMAIL_QUERY);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Client client = new Client();
				client.setId((int) resultSet.getLong("id"));
				client.setNom(resultSet.getString("nom").toUpperCase());
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance").toLocalDate());
				return client;
			}
			return null;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client");
		}
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Client client = new Client();
				client.setId((int) resultSet.getLong("id"));
				client.setNom(resultSet.getString("nom").toUpperCase());
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance").toLocalDate());
				clients.add(client);
			}
			return clients;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des clients");
		}
	}

	public int count() throws DaoException {
		int nbClients = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				nbClients = resultSet.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des clients");
		}
		return nbClients;
	}

	public int countClientByVehicleId(long vehicleId) throws DaoException {
		int nbClients = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CLIENTS_BY_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicleId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				nbClients = resultSet.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des clients");
		}
		return nbClients;
	}
}
