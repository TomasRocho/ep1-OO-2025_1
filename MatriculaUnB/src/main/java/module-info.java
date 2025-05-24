module com.tomas.matriculaunb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.fasterxml.jackson.databind;


    opens com.tomas.matriculaunb to javafx.fxml;
    exports com.tomas.matriculaunb;
    exports com.tomas.matriculaunb.modelo;
    exports com.tomas.matriculaunb.modelo.enumerations;
    exports com.tomas.matriculaunb.controllersJavaFX;
    opens com.tomas.matriculaunb.controllersJavaFX to javafx.fxml;



}