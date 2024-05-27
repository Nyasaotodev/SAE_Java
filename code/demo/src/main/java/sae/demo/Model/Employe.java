package sae.demo.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employe {
    private int globalid;
    private int id;
    private String nom;
    private String prenom;
    private String adresse;

    public Employe( int id, String nom, String prenom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.globalid += 1;
    }
    public Employe(String nom, String prenom, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.globalid += 1;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws SQLException {
        this.nom = nom;
        Connection conn = new BDD().getConn();
        try (PreparedStatement stmt =
                     conn.prepareStatement(
                             "UPDATE employe SET nom = ? WHERE idemploye = ?;")
        ) {
            stmt.setString(1, nom);
            stmt.setInt(2, this.id);
            stmt.executeUpdate();
        }
        conn.close();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) throws SQLException {
        this.prenom = prenom;
        Connection conn = new BDD().getConn();
        try (PreparedStatement stmt =
                     conn.prepareStatement(
                             "UPDATE employe SET prenom = ? WHERE idemploye = ?;")
        ) {
            stmt.setString(1, prenom);
            stmt.setInt(2, this.id);
            stmt.executeUpdate();
        }
        conn.close();
    }

    public String getAdresse() {
        return adresse;
    }

    public void setadresse(String adresse) throws SQLException {
        this.adresse = adresse;
        Connection conn = new BDD().getConn();
        try (PreparedStatement stmt =
                     conn.prepareStatement(
                             "UPDATE employe SET adresse = ? WHERE idemploye = ?;")
        ) {
            stmt.setString(1, adresse);
            stmt.setInt(2, this.id);
            stmt.executeUpdate();
        }
        conn.close();
    }

    public String toString() {
        return "Employe{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
