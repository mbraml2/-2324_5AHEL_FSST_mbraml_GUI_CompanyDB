package com.example.gui_db.models;

import java.io.BufferedReader;
import java.io.FileReader;

public class Login {

    private static Login instance = new Login();
    private final String loginPath = "c:/Geheim/login.txt";
    private final String host = "jdbc:postgresql://localhost:54321/mbraml";
    //private final String host = "jdbc:postgresql://xserv:5432/mbraml";
    private String user;
    private String password;

    private Login() {
        setLoginData();
    }

    public static Login getInstance() {
        return instance;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    private void setLoginData(){
        String line;
        try {
            var reader = new BufferedReader(new FileReader(loginPath));
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("user:")) {
                    user = line.substring(5).trim();
                }
                if(line.startsWith("password:")){
                    password = line.substring(9).trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


