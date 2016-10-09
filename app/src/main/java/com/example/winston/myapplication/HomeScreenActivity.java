package com.example.winston.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.value;

/*
Currently this screen is used only for test purpose. The largest risk exposure of this project was establishing database
connectivity. The HomeScreenActivity allows the user to push information to the database and can be successfully seen in the databse.
The user does not currently have a method to view the entries in the database. Will be implemented during iteration 2.
 */
public class HomeScreenActivity extends AppCompatActivity {
    private static final String TAG = HomeScreenActivity.class.getSimpleName();

    //Global Variables
    Button button;
    EditText amount;
    FirebaseDatabase database;
    String UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        button = (Button)findViewById(R.id.pushtodb);
        amount = (EditText) findViewById(R.id.moneySpent);
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate: " + email);
        Log.d(TAG, "onCreate: ");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Snackbar.make(view, "HistoryActivity...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent historyIntent = new Intent(HomeScreenActivity.this, HistoryActivity.class);
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
                sendToDatabase();
            }
        });
    }

    /*
     *Test method to send to database
      * currently it will send any information placed
       * next iteration will implement an error check to ensure the data entered
       * is reasonably within range
       * This method is called each time the push to database button is pushed
     */
    private void sendToDatabase(){
        Date date = new Date();
        //The UUID is the table name and uses the timestamp to differentiate entries
        //UUID -> Time Stamps -> Values
        long time = date.getTime();
        //if(){
        DatabaseReference myRef = database.getReference(UUID + "/" + time);
        myRef.setValue( amount.getText().toString());
    }


    /*
     *Prevents back button from returning to log on screen.
      * If held long enough it will allow the app to minimize.
     */
    long backPressedAt = System.currentTimeMillis();
    int backPressCount = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final long currentTime = System.currentTimeMillis();
            if (currentTime- backPressedAt < 888)
            {
                backPressCount++;
            }
            else
            {
                backPressCount = 0;
            }
            backPressedAt = currentTime;
            if(backPressCount == 2)
            {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
