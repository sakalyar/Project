package com.example.forms;

import android.widget.TextView;

import java.util.List;

public class Form implements FormInterface {
    TextView email = null,
            password = null;

    List<String> birdsWatched = null;
    @Override
    public void setForm(TextView email, TextView password, List<String> birds) {

    }
}
