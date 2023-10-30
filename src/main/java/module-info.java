module com.group1.reproductorjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.group1.reproductorjava to javafx.fxml;
    exports com.group1.reproductorjava;
}