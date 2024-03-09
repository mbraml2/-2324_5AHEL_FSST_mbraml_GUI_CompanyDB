package com.example.gui_db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
        System.out.println("Hello");
        contentList.add("Eins");
        contentList.add("Zwei");
        contentLV.getItems().addAll(contentList);




    }
}