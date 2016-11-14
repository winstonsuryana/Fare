package com.example.winston.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



/*
    Sample code to implement recycler view for card list: http://www.truiton.com/2015/03/android-cardview-example/
    also taken from: https://developer.android.com/training/material/lists-cards.html#CardView
 */



public class HistoryActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HistoryCardAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);




    }


    @Override
    protected void onResume() {
        super.onResume();
        ((HistoryCardAdapter) mAdapter).setOnItemClickListener(new HistoryCardAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < 2; index++) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Float amount = new Float(0.00);
            DataObject obj = new DataObject("Restaurant " + index , "John Doe", amount, date);
            results.add(index, obj);
        }
        return results;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}

class HistoryCardAdapter extends RecyclerView.Adapter<HistoryCardAdapter.DataObjectHolder> {
    private static String LOG_TAG = "HistoryCardAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            //label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public HistoryCardAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
//        holder.label.setText(mDataset.get(position).getRestaurantName());
//        holder.dateTime.setText(mDataset.get(position).getPaidBy());
//        holder.dateTime.setText(mDataset.get(position).getDateValue());
        //holder.label.setText((int) mDataset.get(position).getAmount());
        //holder.dateTime.setText(mDataset.get(position).getPaidBy());
//        holder.label.setText("asdfa");
//        holder.dateTime.setText("ASDFASDf");
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}

/*
    Database information can be retrieved in the future using the code below.
 */
class DataObject {
    public String restaurantName;
    public String paidBy;
    public float amount;
    public String dateValue;

    DataObject (String restaurant, String paidBy, float amount, String dateValue){
        restaurantName = restaurant;
        paidBy = paidBy;
        amount = amount;
        dateValue = dateValue;

    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setPaidby(String paidBy){
        this.paidBy = paidBy;

    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public void setDateValue(String dateValue){
        this.dateValue = dateValue;
    }
    public String getRestaurantName(){
        return restaurantName;
    }
    public String getPaidBy() {
        return paidBy;
    }

    public String getDateValue(){
        return dateValue;

    }
    public float getAmount(){
        return amount;
    }
}