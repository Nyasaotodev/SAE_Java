module sae.mining_simulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    exports vue;
    exports code;
    opens code to javafx.fxml;
}