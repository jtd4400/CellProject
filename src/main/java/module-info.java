module com.example.cellproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.cellproject to javafx.fxml;
    exports com.example.cellproject;
}