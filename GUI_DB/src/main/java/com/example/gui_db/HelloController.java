package com.example.gui_db;

import com.example.gui_db.models.HumanResources;
import com.example.gui_db.models.Login;
import com.example.gui_db.models.Person;
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

    private HumanResources hResources = new HumanResources();
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<String> contentLV = new ListView<>();
    private ArrayList<String> contentList = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("INIT");

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void onloadBTNClick() {
        Login login = Login.getInstance();
        Connection dbConn = null;
        Statement stm = null;
        ArrayList<Person> peopleArray = new ArrayList<>();

        try {
            dbConn = DriverManager.getConnection(login.getHost(), login.getUser(), login.getPassword());
            stm = dbConn.createStatement();
            String sql = "SET search_path TO company";
            stm.executeUpdate(sql);

            sql = "SELECT * FROM t_human_resources "
                    + "INNER JOIN t_city ON t_city.postal_code = t_human_resources.postal_code";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Person newPerson = new Person();
                newPerson.setFirstName(rs.getString("first_name"));
                newPerson.setLastName(rs.getString("last_name"));
                newPerson.setGender(rs.getString("gender"));
                newPerson.setDateOfBirth(rs.getDate("date_of_birth").toString());
                newPerson.setEmail(rs.getString("email"));
                newPerson.setPostalCode(rs.getInt("postal_code"));
                newPerson.setCity(rs.getString("city"));
                peopleArray.add(newPerson);
            }
            hResources.setPeople(peopleArray);

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
                System.out.println(e.getMessage());
            }
            try {
                dbConn.close();
                System.out.println("Connection Closed");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        contentList.clear();
        contentLV.getItems().clear();

        for (Person person : hResources.getPeople()) {
            contentList.add(person.toString());
        }

        for(String person:contentList){
            contentLV.getItems().add(person);
        }
    }


    @FXML
    public void onDeleteBTNClick() {
        ObservableList selectedIndices = contentLV.getSelectionModel().getSelectedIndices();

        for (Object o : selectedIndices) {
            System.out.println(contentList.get((int) o));
            String firstName = contentList.get((int) o);
        }
    }
}