package sae.demo.Controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import sae.demo.Model.Employe;

import java.sql.SQLException;

public class EventColumn<T extends Event> implements EventHandler<T> {
    @Override
    public void handle(T event) {
        if (event instanceof CellEditEvent) {
            CellEditEvent c = (CellEditEvent) event;
            int colonne = ((CellEditEvent)event).getTablePosition().getColumn();
            System.out.println("Colonne : " + colonne);
            int index = ((CellEditEvent)event).getTablePosition().getRow();
            System.out.println("Index : " + index);

            switch (colonne) {
                case 0:
                    try {
                        ((Employe)(c.getRowValue())).setNom((String)c.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 1:
                    try {
                        ((Employe)(c.getRowValue())).setPrenom((String)c.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    try {
                        ((Employe)(c.getRowValue())).setadresse((String)c.getNewValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }
}