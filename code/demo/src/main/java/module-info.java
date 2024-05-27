module sae.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.naming;

    opens sae.demo to javafx.fxml;
    exports sae.demo.Controller;
    opens sae.demo.Controller to javafx.fxml;
    exports sae.demo.Vue;
    opens sae.demo.Vue to javafx.fxml;
    exports sae.demo.Model;
    opens sae.demo.Model to javafx.fxml;
}