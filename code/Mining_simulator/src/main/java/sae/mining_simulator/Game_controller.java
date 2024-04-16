package sae.mining_simulator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Game_controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}