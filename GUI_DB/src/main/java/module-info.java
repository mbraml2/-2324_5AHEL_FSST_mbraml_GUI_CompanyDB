module com.example.gui_db {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens com.example.gui_db to javafx.fxml, com.google.gson;
    exports com.example.gui_db;
    exports com.example.gui_db.models;
    opens com.example.gui_db.models to javafx.fxml, com.google.gson;
}