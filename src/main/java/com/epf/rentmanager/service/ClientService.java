package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientDao clientDao;

    private ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    public long create(Client client) throws ServiceException {
        if (client.getNom().length() < 3) {
            throw new ServiceException("Le nom doit contenir au moins 3 caractère");
        }
        if (client.getPrenom().length() < 3) {
            throw new ServiceException("Le prénom doit contenir au moins 3 caractère");
        }
        if (Period.between(client.getNaissance(), LocalDate.now()).getYears() < 18) {
            throw new ServiceException("Le client doit être majeur");
        }
        try {
            Client existingClient = clientDao.findByEmail(client.getEmail());
            if (existingClient != null) {
                throw new ServiceException("L'adresse email est déjà prise");
            }
            return clientDao.create(client);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création du client");
        }
    }

    public void update(Client client) throws ServiceException {
        if (client.getNom().length() < 3) {
            throw new ServiceException("Le nom doit contenir au moins 3 caractère");
        }
        if (client.getPrenom().length() < 3) {
            throw new ServiceException("Le prénom doit contenir au moins 3 caractère");
        }
        if (Period.between(client.getNaissance(), LocalDate.now()).getYears() < 18) {
            throw new ServiceException("Le client doit être majeur");
        }
        try {
            Client existingClient = clientDao.findByEmail(client.getEmail());
            if (existingClient != null) {
                throw new ServiceException("L'adresse email est déjà prise");
            }
            clientDao.update(client);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la mise à jour du client");
        }
    }

    public void delete(long id) throws ServiceException {
        try {
            clientDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression du client");
        }
    }

    public Client findById(long id) throws ServiceException {
        if (id < 0) {
            throw new ServiceException("L'id doit être positif");
        }
        try {
            return clientDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur lors de la récupération du client");
        }
    }

    public Client findByEmail(String email) throws ServiceException {
        try {
            return clientDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération du client");
        }
    }
    public List<Client> findAll() throws ServiceException {
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

    public int countClientByVehicleId(long vehicleId) throws ServiceException {
        try {
            return clientDao.countClientByVehicleId(vehicleId);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors du comptage des clients");
        }
    }

}
