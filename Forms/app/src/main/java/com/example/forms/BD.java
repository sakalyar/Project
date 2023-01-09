package com.example.forms;

import java.util.List;

public class BD {
    List<Form> formsDb;
    public BD(){}

    public void addToDb(Form form) {
        formsDb.add(form);
    }

}
