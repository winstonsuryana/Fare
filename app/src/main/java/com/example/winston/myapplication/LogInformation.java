package com.example.winston.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
Currently this screen is used only for test purpose. The largest risk exposure of this project was establishing database
connectivity. The LogInformation allows the user to push information to the database and can be successfully seen in the databse.
The user does not currently have a method to view the entries in the database. Will be implemented during iteration 2.
 */
public class LogInformation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = LogInformation.class.getSimpleName();

    //Global Variables
    Button button;
    EditText amount;
    EditText location;
    EditText paidby;
    FirebaseDatabase database;
    String UUID;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginfo_screen);
        button = (Button)findViewById(R.id.pushtodb);
        amount = (EditText) findViewById(R.id.moneySpent);
        location = (EditText) findViewById(R.id.location);
        paidby = (EditText) findViewById(R.id.payee);
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate: " + email);
        Log.d(TAG, "onCreate: ");
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<>();
        categories.add("Food");
        categories.add("Home Goods");
        categories.add("Groceries");
        categories.add("Loan");
        categories.add("Other");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Snackbar.make(view, "HistoryActivity...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent historyIntent = new Intent(LogInformation.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });
        /*
        The User is distinguished by a UUID, this ID is, as implied, uniquie to the user.
        The next few lines retrieves the associated user and pushes the UUID to the server to
        bind the values inputted.
         */
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onCreate: " + user.getUid().toString());
        //only requires a single UUID per session
        UUID = user.getUid();
        database = FirebaseDatabase.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToDatabase(getBaseContext());
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    /*
     *Test method to send to database
      * currently it will send any information placed
       * next iteration will implement an error check to ensure the data entered
       * is reasonably within range
       * This method is called each time the push to database button is pushed
     */
    private void sendToDatabase(Context context){
        Date date = new Date();
        //The UUID is the table name and uses the timestamp to differentiate entries
        //UUID -> Time Stamps -> Values
        long time = date.getTime();
        //if(){



        DatabaseReference locationRef = database.getReference(UUID + "/" + time + "/" + "Location");
        locationRef.setValue( location.getText().toString() );
        DatabaseReference myRef = database.getReference(UUID + "/" + time + "/" + "Amount");
        myRef.setValue( amount.getText().toString());
        DatabaseReference PayerRef = database.getReference(UUID + "/" + time + "/" + "PaidBy");
        PayerRef.setValue( paidby.getText().toString() );
        DatabaseReference TypeRef = database.getReference(UUID + "/" + time + "/" + "Type");
        TypeRef.setValue( spinner.getSelectedItem().toString());
        Toast.makeText(  context ,"Entry Successful", Toast.LENGTH_LONG).show();
        finish();
    }



}
