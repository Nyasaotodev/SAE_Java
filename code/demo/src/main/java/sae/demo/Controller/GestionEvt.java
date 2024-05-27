package sae.demo.Controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import sae.demo.Model.BDD;
import sae.demo.Model.Employe;
import sae.demo.Vue.HelloApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionEvt <T extends Event> implements EventHandler<T>{
    public HelloApplication tv;
    public GestionEvt(HelloApplication tvs){
        tv = tvs;
    }
    public GestionEvt() throws SQLException {
        tv = new HelloApplication();
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() instanceof Button && ((Button) event.getSource()).getText().equals("Add")) {
            System.out.println("employe added");
            try {
                tv.addPersonne(tv.getNewNom(), tv.getNewprenom(), tv.getNewadresse());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        if (event.getSource() instanceof Button && ((Button) event.getSource()).getText().equals("refresh")) {
            System.out.println("list refreshed");
            Button b = (Button) event.getSource();

        }
    }

}
