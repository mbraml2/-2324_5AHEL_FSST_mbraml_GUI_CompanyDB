package com.example.gui_db;

import com.example.gui_db.models.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.*;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<String> contentLV = new ListView<>();
    private ArrayList<String> contentList = new ArrayList<>();
    @FXML
    private Button loadBTN;

    @FXML
    public void initialize(){
        System.out.println("INIT");

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void onloadBTNClick(){
        Login login = Login.getInstance();
        Connection dbConn = null;
        Statement stm = null;

        contentList.clear();

        try {
            dbConn = DriverManager.getConnection(login.getHost(), login.getUser(), login.getPassword());
            stm = dbConn.createStatement();
            String sql = "SET search_path TO company";
            stm.executeUpdate(sql);

            sql = "SELECT * FROM t_human_resources "
                    + "INNER JOIN t_city ON t_city.postal_code = t_human_resources.postal_code";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                contentList.add(rs.getString("first_name") + ", " +
                        rs.getString("last_name") + ", " +
                        rs.getDate("date_of_birth") + ", "+
                        rs.getInt("postal_code") + ", " +
                        rs.getString("city") + ", "
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert sqlAlert = new Alert(Alert.AlertType.ERROR);
            sqlAlert.setTitle("Fehler");
            sqlAlert.setHeaderText("Fehler beim Datenbank Zugriff");
            sqlAlert.showAndWait();
        } finally {
            try {
                stm.close();
            } catch (Exception e) {
            }
            try {
                dbConn.close();
            } catch (Exception e) {
            }
        }

        contentLV.getItems().clear();


        for(String person:contentList){
            contentLV.getItems().add(person);
        }




    }
}