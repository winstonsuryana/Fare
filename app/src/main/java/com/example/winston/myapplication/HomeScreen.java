package com.example.winston.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("user");
        mTransactions = new ArrayList<>();
        Transaction transaction1 = new Transaction("McDonalds", "10/10/2016", "Payee1", "$47.72");
        Transaction transaction2 = new Transaction("Wendy's", "12/30/2016", "Payee2", "$34.50");


        //Q$@#^%#$@^#$%^@#$%^#$^#$
        //make this thing work...
        // null object blah blah.
        Log.d(TAG, "onCreate: " + transaction1.getCost());
        mTransactions.add(transaction1);
        mTransactions.add(transaction2);
        mTransactions.add(transaction1);
        mTransactions.add(transaction2);
        mTransactions.add(transaction1);
        mTransactions.add(transaction2);
        mTransactions.add(transaction1);
        mTransactions.add(transaction2);
        mTransactions.add(transaction1);
        mTransactions.add(transaction2);

        mTransListAdapter = new TransactionAdapter(HomeScreen.this, mTransactions);
        mTransListView = (ListView) findViewById(R.id.RecentListView);
        mTransListView.setAdapter(mTransListAdapter);



        mTransListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
//                Transaction item = (Transaction) parent.getItemAtPosition(position);
//                Intent i = new Intent(HomeScreen.this, TransactionDetails.class);
//                i.putExtra("name", item.getCompany());
//                i.putExtra("ticker", item.getTicker());
//                startActivity(i);

            }
        });


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

        }

        return super.onOptionsItemSelected(item);
    }

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

}