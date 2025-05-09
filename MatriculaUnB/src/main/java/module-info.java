module com.tomas.matriculaunb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tomas.matriculaunb to javafx.fxml;
    exports com.tomas.matriculaunb;
    exports com.tomas.matriculaunb.controllersJavaFX;
    opens com.tomas.matriculaunb.controllersJavaFX to javafx.fxml;
}