package com.example.birdsforms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private TextView textView;
    private Button submit, viewAll, debugCLEAR, clear, send;
    private EditText login, birds, numberOfBirds, password;
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
    private boolean rareSpeciesObserved = false;


    private boolean[] selectedBirds;
    private ArrayList<Integer> birdsList = new ArrayList<>();
    private static final String[] birdsArray = {"Linote mélodieuse", "Pigeon", "Ibis sacré",
            "Poule sauvage", "Kiwi"};

//    private GoogleApiClient mGoogleApiClient;
//    mGoogleApiClient = new GoogleApiClient.Builder(this)
//            .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        birdsList();

        submit = findViewById(R.id.saveButton);
        viewAll = findViewById(R.id.ViewAllButton);
        login = findViewById(R.id.EmailText);
        numberOfBirds = findViewById(R.id.numberOfBirds);
        clear = findViewById(R.id.clearButton);
        password = findViewById(R.id.passwordText);
        send = findViewById(R.id.pushDBButton);

        debugCLEAR = findViewById(R.id.DebugClear);

        debugCLEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPS.requestSingleUpdate(v.getContext(),
                        new GPS.LocationCallback() {
                            @Override
                            public void onNewLocationAvailable(GPS.GPSCoordinates loc) {
//                        location = loc;
                                String s = "getLocation() LAT: " + loc.latitude + ", LON: " + loc.longitude;
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
//                        MyApplication.log(LOG_TAG, "getLocation() LAT: " + loc.latitude + ", LON: " + loc.longitude);
                            }
                        });
                dataBaseHelper.DeleteAll();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("");
                numberOfBirds.setText("");
                password.setText("");
                for (int i = selectedBirds.length;i > 0;) {
                    selectedBirds[--i] = false;
                }
                for (int j = 0; j < selectedBirds.length; j++) {
                    // remove all selection
                    selectedBirds[j] = false;
                    // clear birds list
                    birdsList.clear();
                    // clear text view value
                    textView.setText("");
                }
                if (rareSpeciesObserved)
                    Toast.makeText(MainActivity.this, "true" , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "false" , Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WatcherModel watcherModel;

                try {
                    String x = addBirdsListToDatabase() + " ";
                    Toast.makeText(MainActivity.this, login.getText().toString(), Toast.LENGTH_SHORT).show();
                    watcherModel = new WatcherModel(-1, login.getText().toString(), password.getText().toString(),
                            Integer.parseInt(numberOfBirds.getText().toString()), rareSpeciesObserved, x);
                    Toast.makeText(MainActivity.this, dataBaseHelper.getEveryone().toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    watcherModel = new WatcherModel(-1, "error", "error", 0, false,"error");
                    Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, login.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(watcherModel);
//                Toast.makeText(MainActivity.this, login.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<WatcherModel> all = dataBaseHelper.getEveryone();
                Toast.makeText(MainActivity.this, all.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.PushOnline();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.redBookIncludedButton:
                if (checked) {
                    rareSpeciesObserved = true;
                    RadioButton b = findViewById(R.id.redBookExcludedButton);
                    b.setChecked(false);
                    break;
                }
            case R.id.redBookExcludedButton:
                if (checked)
                    rareSpeciesObserved = false;
                RadioButton b = findViewById(R.id.redBookIncludedButton);
                b.setChecked(false);
                break;
        }
    }


    private String addBirdsListToDatabase() {
        String birdsListString = "";
        for (int i = 0, s = selectedBirds.length; i < s; ++i) {
            if ((selectedBirds[i] == true))
                birdsListString += birdsArray[i] + ";";
        }
        return birdsListString;
    }


    // Structure de données

    // LIST DES OISEQUX
    private void birdsList() {
        /*La liste deroulante: */
        textView = findViewById(R.id.textView);

        selectedBirds = new boolean[birdsArray.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // set title
                builder.setTitle("Choisissez un ou plusieurs oiseaux");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(birdsArray, selectedBirds, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            birdsList.add(i);
                            Collections.sort(birdsList);
                        } else {
                            birdsList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < birdsList.size(); j++) {
                            stringBuilder.append(birdsArray[birdsList.get(j)]);
                            if (j != birdsList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Effacer tout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedBirds.length; j++) {
                            // remove all selection
                            selectedBirds[j] = false;
                            // clear birds list
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
    }
}