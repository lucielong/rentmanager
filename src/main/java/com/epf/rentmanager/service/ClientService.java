package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	/*public static ClientService instance;*/
	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	/*private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}*/
	
	/*public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}*/



	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		try {
			if (client.getNom().length() < 1) {
				throw new ServiceException("Le nom doit contenir au moins 1 caractère");
			}
			if (client.getPrenom().length() < 1) {
				throw new ServiceException("Le prénom doit contenir au moins 1 caractère");
			}
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du client");
		}
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		if(id<0){
			throw new ServiceException("L'id doit être positif");
		}
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur lors de la récupération du client");
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération des clients");
		}
	}

	public int count() throws ServiceException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du comptage des clients");
		}
	}
	
}
