package com.example.forms;

import java.util.List;

public class WatcherModel {

    private int id;
    private String login;
    private String password;
    private List<String> birdsList;

    public WatcherModel(int id, String login, String password, List<String> birdsList) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.birdsList = birdsList;
    }

    public WatcherModel() {}

    @Override
    public String toString() {
        return "WatcherModel{" +
                "id=" + id +
                ", login='" + login + "\'" +
                ", age=" + password +
                ", isActive=" + ListToString(birdsList) +
                '}';
    }

    private String ListToString(List<String> lst) {
        String s = "";
        for (int i = 0; i < lst.size()-1; ++i)
            s += lst.get(i) + ", ";
        s += lst.size()-1;
        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getBirdsList() {
        return birdsList;
    }

    public void setBirdsList(List<String> birdsList) {
        this.birdsList = birdsList;
    }

    public String getBirds() {
        return ListToString(birdsList);
    }


    public String getName() {
        return null;
    }


    public String getPassword() {
        return null;
    }
}
