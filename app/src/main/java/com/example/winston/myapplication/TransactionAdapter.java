package com.example.winston.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Winston on 10/16/2016.
 */

public class TransactionAdapter extends ArrayAdapter<Transaction>{
    public TransactionAdapter(Context context, ArrayList<Transaction> itemName) {
        super(context, R.layout.card_view_row, itemName);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.card_view_row, parent, false);

        Transaction transaction = getItem(position);
        TextView company = (TextView) customView.findViewById(R.id.nameofcompany);
        TextView date = (TextView) customView.findViewById(R.id.transactiondate);
        TextView payer = (TextView) customView.findViewById(R.id.payer);
        TextView cost = (TextView) customView.findViewById(R.id.cost);



        company.setText(transaction.getCompany());
        date.setText(transaction.getDate());
        payer.setText(transaction.getName());
        cost.setText(transaction.getCost());

        return customView;
    }
}
