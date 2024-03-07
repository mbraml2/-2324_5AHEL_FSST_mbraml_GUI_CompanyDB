module com.example.gui_db {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gui_db to javafx.fxml;
    exports com.example.gui_db;
}