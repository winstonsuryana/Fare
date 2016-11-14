package com.example.winston.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import static com.example.winston.myapplication.R.id.Suggestion;
import static com.example.winston.myapplication.R.id.login;
import static com.example.winston.myapplication.R.id.time;


/**
 * HomeScreen is the class which is provided after the user has logged in with correct credentials
 * This class will display recent activities and a pie chart for the purchases made.
 * The user can traverse the application, allowing them to add transactions or view historical
 * transactions
 */
@SuppressLint("SetJavaScriptEnabled")
public class HomeScreen extends AppCompatActivity {

    WebView webView;
    int num1, num2, num3, num4, num5;
    String name1, name2;
    private ArrayList<Transaction> mTransactions;
    private ListAdapter mTransListAdapter;
    ListView mTransListView;
    String TAG = "HomeScreen";
    String email;
    String UUID;
    String Amount;
    String timestamp;
    String Location;
    String Paidby;
    String Type;
    String Transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("user");

//        Transaction transaction1 = new Transaction("McDonalds", "10/10/2016", "Payee1", "$47.72");
//        Transaction transaction2 = new Transaction("Wendy's", "12/30/2016", "Payee2", "$34.50");


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UUID = user.getUid();
//        Log.d(TAG, "onCreate: uuid"  + UUID);
        DatabaseReference ref = database.getReference(UUID);

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTransactions = new ArrayList<>();
//                Log.d(TAG, "onDataChange: "+ dataSnapshot.getValue().toString());

                dataSnapshot.getChildren();
                for( DataSnapshot message: dataSnapshot.getChildren()){

                    timestamp = message.getKey();
                    try {

                        Amount = message.child("Amount").getValue().toString();
                        Paidby = message.child("PaidBy").getValue().toString();
                        Location = message.child("Location").getValue().toString();
                        Type = message.child("Type").getValue().toString();
                    }
                    catch(NullPointerException e){
                        e.printStackTrace();
                    }
                    Date date  = new Date();
                    date.setTime(Long.parseLong(timestamp));
                    Log.d(TAG, "onDataChange: " + timestamp);
                    mTransactions.add( new Transaction(timestamp.toString(), Location,date.toString(), Paidby, Amount, Type));



                }
//                if(dataSnapshot.getValue() != null) {
//                    parseJSON(dataSnapshot.getValue().toString());
//                }

                Collections.reverse(mTransactions);
                mTransListAdapter = new TransactionAdapter(HomeScreen.this, mTransactions);
                mTransListView = (ListView) findViewById(R.id.RecentListView);
                mTransListView.setAdapter(mTransListAdapter);

                mTransListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                        Transaction item = (Transaction) parent.getItemAtPosition(position);
                        Intent i = new Intent(HomeScreen.this, EditInformation.class);
                        Log.d(TAG, "onItemClick: " + item.getTransaction());

                        i.putExtra("transaction", item.getTransaction());
                        i.putExtra("company", item.getCompany());
                        i.putExtra("date", item.getDate());
                        i.putExtra("paidby", item.getName());
                        i.putExtra("type", item.getType());
                        i.putExtra("cost", item.getCost());
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        //This is hard coded information that will be replaced during iteration 3 with information from the database

//        mTransactions.add(transaction1);
//        mTransactions.add(transaction2);
//        mTransactions.add(transaction1);
//        mTransactions.add(transaction2);
//        mTransactions.add(transaction1);
//        mTransactions.add(transaction2);
//        mTransactions.add(transaction1);
//        mTransactions.add(transaction2);
//        mTransactions.add(transaction1);
//        mTransactions.add(transaction2);



        //When the selecting the item it will allow the user to change the information on the card
        //Currently pressing on a card does has no action


        //these names will be fetched when rendering the graph
        num1 = 1;
        num2 = 1;
        name1 = "name1";
        name2 = "name2";

        webView = (WebView)findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/chart.html");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ahh", "onClick: " + "fab");
                LogActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_history:
                Intent i = new Intent(this, HistoryActivity.class);
                startActivity(i);
                return true;
            case R.id.action_settings:
                Log.d(TAG, "onOptionsItemSelected: ");
                return true;
            case R.id.Suggestion:
                Log.d(TAG, "onOptionsItemSelected: Suggestion");
                getSuggestion();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }



    public void getSuggestion(){
        Random randomizer = new Random();
        ArrayList<Transaction> suggestion = new ArrayList<>();
        for(int i = 0; i < mTransactions.size(); i++){
            Log.d(TAG, "getSuggestion: " + mTransactions.get(i).getType() );
            if(mTransactions.get(i).getType().equals("Food")){
                suggestion.add(mTransactions.get(i));
                Log.d(TAG, "getSuggestion: " + suggestion.size());
            }
        }

        if(suggestion.size() == 0){
            new AlertDialog.Builder(this).setTitle("Error").setMessage("There are no restaurants to choose from.").setNeutralButton("Close", null).show();
            return;
        }
        int randint = randomizer.nextInt() % suggestion.size();
        String random = suggestion.get(randint).getCompany();

        new AlertDialog.Builder(this).setTitle("Random Recommendation").setMessage(random).setNeutralButton("Close", null).show();

    }
    /**
     * Allows for the javascript display of the piechart
     */
    public class WebAppInterface {

        @JavascriptInterface
        public int getNum1() {
            return num1;
        }

        @JavascriptInterface
        public int getNum2() {
            return num2;
        }

        @JavascriptInterface
        public String getName1(){
            return name1;
        }

        @JavascriptInterface
        public String getName2(){
            return name2;
        }
    }

    public void LogActivity(){
        Intent i = new Intent(this, LogInformation.class);
        i.putExtra("user",email);
        startActivity(i);

    }

    public void parseJSON(String data){

        StringTokenizer st = new StringTokenizer(data, "}");
        while(st.hasMoreTokens()){
            //Log.d(TAG, "parseJSON: "+ st.nextToken());
            StringTokenizer nexttoken = new StringTokenizer(st.nextToken(),"=");
            while(nexttoken.hasMoreTokens()){
                Log.d(TAG, "\tparseJSON: " + nexttoken.nextToken());
                int i = 0;
                String token = nexttoken.nextToken();
                while( i < token.length() ){
                    Log.d(TAG, "parseJSON: " + token.charAt(i) );
                }

            }
        }



    }


}