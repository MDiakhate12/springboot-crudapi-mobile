package com.crudapi.mobile.client;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "client")
public class Client {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "prenom")
    private String prenom;

    @NonNull
    @ColumnInfo(name = "nom")
    private String nom;

    @NonNull
    @ColumnInfo(name = "telephone")
    private String telephone;

    public Client(String prenom, String nom, String telephone) {
        this.prenom = prenom;
        this.nom = nom;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(@NonNull String prenom) {
        this.prenom = prenom;
    }

    @NonNull
    public String getNom() {
        return nom;
    }

    public void setNom(@NonNull String nom) {
        this.nom = nom;
    }

    @NonNull
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(@NonNull String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}

