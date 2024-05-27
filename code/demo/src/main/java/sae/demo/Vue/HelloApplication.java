package sae.demo.Vue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sae.demo.Controller.EventColumn;
import sae.demo.Controller.GestionEvt;
import sae.demo.Model.DataTable;
import sae.demo.Model.Employe;

import java.sql.SQLException;

public class HelloApplication extends Application {

    private final TableView<Employe> table = new TableView<>();
    final HBox hb = new HBox();
    private DataTable dt = new DataTable();

    final TextField addNom = new TextField();
    final TextField addprenom = new TextField();
    final TextField addadresse = new TextField();

    public HelloApplication() throws SQLException {
    }

    public void addPersonne(String nom, String prenom, String adresse) throws SQLException {
        dt.addEmploye(new Employe(nom, prenom, adresse)); }

    public String getNewNom(){
        return addNom.getText();
    }

    public String getNewprenom(){
        return addprenom.getText();
    }

    public String getNewadresse(){
        return addadresse.getText();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(), 650, 480);
        stage.setScene(scene);
        stage.setTitle("Employee Management System");


        final Label label = new Label("Employees");
        label.setFont(new Font("Arial", 20));


        table.setItems(dt.getData());
        table.setEditable(true);

        TableColumn<Employe, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nomCol.setOnEditCommit(new EventColumn());

        TableColumn<Employe, String> prenomCol = new TableColumn<>("prenom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        prenomCol.setCellFactory(TextFieldTableCell.forTableColumn());
        prenomCol.setOnEditCommit(new EventColumn());

        TableColumn<Employe, String> adresseCol = new TableColumn<>("adresse");
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        adresseCol.setCellFactory(TextFieldTableCell.forTableColumn());
        adresseCol.setOnEditCommit(new EventColumn());

        table.getColumns().addAll(nomCol, prenomCol, adresseCol);


        addNom.setPromptText("Nom");
        addprenom.setPromptText("prenom");
        addadresse.setPromptText("adresse");

        final Button addButton = new Button("Add");
        hb.setSpacing(3);
        addButton.setOnMouseClicked(new GestionEvt(this));
        addButton.setOnKeyPressed(new GestionEvt(this));

        final Button refreshButton = new Button("refresh");
        hb.setSpacing(3);
        refreshButton.setOnMouseClicked(new GestionEvt(this));
        refreshButton.setOnKeyPressed(new GestionEvt(this));


        hb.getChildren().addAll(addNom, addprenom, addadresse, addButton, refreshButton);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding( new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}