module com.tomas.unb_oo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tomas.unb_oo to javafx.fxml;
    exports com.tomas.unb_oo;
}