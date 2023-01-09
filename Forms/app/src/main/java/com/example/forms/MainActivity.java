package com.example.forms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private boolean[] selectedBirds;
    private ArrayList<Integer> birdsList = new ArrayList<>();
    private static final String[] birdsArray = {"Eagle", "Pigeon", "Sparrow", "Chicken", "falcon"};

//    public Form() {}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // initialize selected language array
        selectedBirds = new boolean[birdsArray.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(birdsArray, selectedBirds, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            birdsList.add(i);
                            // Sort array list
                            Collections.sort(birdsList);
                        } else {
                            // when checkbox unselected
                            // Remove position from birdsList
                            birdsList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < birdsList.size(); j++) {
                            // concat array value
                            stringBuilder.append(birdsArray[birdsList.get(j)]);
                            // check condition
                            if (j != birdsList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedBirds.length; j++) {
                            // remove all selection
                            selectedBirds[j] = false;
                            // clear language list
                            birdsList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });


        Button save = (Button) findViewById(R.id.saveButton);
        Button submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

}