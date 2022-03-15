module com.example.airlineproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;


    opens com.example.airlineproject to javafx.fxml;
    exports com.example.airlineproject;
}