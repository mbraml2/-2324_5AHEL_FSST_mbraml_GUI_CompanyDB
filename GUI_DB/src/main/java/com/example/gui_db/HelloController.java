package com.example.gui_db;

import com.example.gui_db.models.HumanResources;
import com.example.gui_db.models.Login;
import com.example.gui_db.models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HelloController {

    private HumanResources hResources = new HumanResources();
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<String> contentLV = new ListView<>();
    private Hashtable<Integer, String> contentList = new Hashtable<>();

    @FXML
    public void initialize() {
        contentLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
                Person newPerson = new Person();
                newPerson.setFirstName(rs.getString("first_name"));
                newPerson.setLastName(rs.getString("last_name"));
                newPerson.setGender(rs.getString("gender"));
                newPerson.setDateOfBirth(rs.getDate("date_of_birth").toString());
                newPerson.setEmail(rs.getString("email"));
                newPerson.setPostalCode(rs.getInt("postal_code"));
                newPerson.setCity(rs.getString("city"));
                peopleArray.add(newPerson);
                contentList.put(rs.getInt("person_id"), newPerson.toString());
            }
            hResources.setPeople(peopleArray); // sets hResources to people from DB

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

        contentLV.getItems().clear();   // clears listview
        for (String person : contentList.values()) { // loads content list strings into ListView
            contentLV.getItems().add(person);
        }
    }


    @FXML
    public void onDeleteBTNClick() {
        ObservableList selectedIndices = contentLV.getSelectionModel().getSelectedItems();

        ArrayList<Integer> toDeleteIDs = new ArrayList<>(); // Collects the IDs of the people we want to delete

        for (Object o : selectedIndices) {
            Enumeration<Integer> idKeys = contentList.keys();
            while (idKeys.hasMoreElements()) {
                int key = idKeys.nextElement();
                if (contentList.get(key).equals(o.toString())) {
                    toDeleteIDs.add(key);
                }
            }
        }

        System.out.println(toDeleteIDs);

        Connection dbConn = null;
        Statement stm = null;
        Login login = Login.getInstance();

        for (Integer id : toDeleteIDs) { // Deletes people from DB by person_id
            try {
                dbConn = DriverManager.getConnection(login.getHost(), login.getUser(), login.getPassword());

                stm = dbConn.createStatement();
                String sql = "SET search_path TO company";
                stm.executeUpdate(sql);

                sql = "DELETE FROM t_human_resources WHERE person_id = "+ id;
                stm.executeUpdate(sql);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
        }
        Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
        deleteAlert.setTitle("Succesful Deleted");
        deleteAlert.setContentText("Successfully deleted "+ toDeleteIDs.size()+ " items");
        deleteAlert.showAndWait();


    }
}