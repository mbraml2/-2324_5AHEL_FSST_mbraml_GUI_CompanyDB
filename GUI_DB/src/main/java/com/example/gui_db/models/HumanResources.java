package com.example.gui_db.models;

import java.util.ArrayList;

public class HumanResources {
    private ArrayList<Person> people;

    public HumanResources(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }
}
