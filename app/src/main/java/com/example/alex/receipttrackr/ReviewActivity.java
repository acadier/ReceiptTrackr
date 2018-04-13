package com.example.alex.receipttrackr;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    Receipt receipt;
    private static final long serialVersionUID = 1L;
    EditText storeNameTxt, receiptDate, totalTxt;
    ListView itemsList;
    Spinner payTypeSpin;
    Integer noItems;
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        storeNameTxt = findViewById(R.id.storeNameTxt);
        receiptDate = findViewById(R.id.receiptDate);
        totalTxt = findViewById(R.id.totalTxt);
        itemsList = findViewById(R.id.itemsLst);
        payTypeSpin = findViewById(R.id.payTypeSpin);

        receipt = getReceipt();

        items = receipt.getItems();

        storeNameTxt.setText(receipt.getStoreName());
        receiptDate.setText(receipt.getCaptureDate());
        totalTxt.setText(receipt.getTotalString());

        CustomItemsAdaptor customItemsAdaptor = new CustomItemsAdaptor(this, items);
        itemsList.setAdapter(customItemsAdaptor);
    }

    private Receipt getReceipt() {
        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        Gson gson = new Gson();
        String json = mPrefs.getString("receiptObj", "");
        Receipt receipt = gson.fromJson(json, Receipt.class);
        return receipt;
    }
}
