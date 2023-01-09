package com.example.forms;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public interface FormInterface {
    TextView email = null,
             password = null;

    List<String> birdsWatched = null;

    void setForm(TextView email, TextView password, List<String> birds);

}
