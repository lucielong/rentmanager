package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Vehicle {
    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;

    public Vehicle(int id, String constructeur, int nbPlaces) {
        this.id = id;
        this.constructeur = constructeur;
        this.nb_places = nbPlaces;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }



    public int getNb_places() {
        return nb_places;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }


    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

}
