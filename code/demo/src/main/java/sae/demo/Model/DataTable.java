package sae.demo.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataTable {
    private ObservableList<Employe> data;
    private int id = 1;

    public DataTable() throws SQLException {

        this.data = FXCollections.observableArrayList();

        Connection conn = new BDD().getConn();

        try (PreparedStatement stmt =
                     conn.prepareStatement(
                             "SELECT idemploye, nom, prenom, adresse FROM employe;")
        ) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    this.id += 1;
                    this.data.add(new Employe(rs.getInt("idemploye"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse")));
                }
            }
        }
        conn.close();
    }

    public ObservableList<Employe> getData() {
        return data;
    }

    public void addEmploye(Employe employe) throws SQLException {
        this.data.add(employe);
        Connection conn = new BDD().getConn();

        try (PreparedStatement stmt =
                     conn.prepareStatement(
                             "INSERT INTO employe VALUES (?, ?, ?, ?, 1);")
        ) {
            stmt.setInt(1, this.id);
            stmt.setString(2, employe.getNom());
            stmt.setString(3, employe.getPrenom());
            stmt.setString(4, employe.getAdresse());
            stmt.executeUpdate();
        }
        conn.close();
    }
}
