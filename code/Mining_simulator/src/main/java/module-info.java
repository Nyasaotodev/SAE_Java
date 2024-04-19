module sae.mining_simulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens sae.mining_simulator to javafx.fxml;
    exports sae.mining_simulator;
    exports code;
    opens code to javafx.fxml;
}