module com.group1.reproductorjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.group1.reproductorjava to javafx.fxml;
    opens com.group1.reproductorjava.controller to javafx.fxml;
    opens com.group1.reproductorjava.model.Entity to javafx.base;
    exports com.group1.reproductorjava;
}