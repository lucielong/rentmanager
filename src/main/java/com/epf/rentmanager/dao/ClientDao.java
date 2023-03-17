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
	
	/*private static ClientDao instance = null;*/
	private ClientDao() {}

	/*public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}*/
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	
	public long create(Client client) throws DaoException {
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
				return resultSet.getLong(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client");
		}
	}
	
	public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setLong(1, client.getId());
			preparedStatement.executeUpdate();
			return client.getId();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client");
		}
	}

	public Client findById(long id) throws DaoException {
			try {
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);
				preparedStatement.setLong(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					Client client = new Client();
					client.setId((int) id);
					client.setNom(resultSet.getString("nom"));
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
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des clients");
		}
		return 0;
	}
}
