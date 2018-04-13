package com.example.alex.receipttrackr;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private Receipt receipt;
    private static final long serialVersionUID = 1L;
    private EditText storeNameTxt, receiptDate, totalTxt;
    private ListView itemsList;
    private Spinner payTypeSpin;
    private Integer noItems;
    private ArrayList<Item> items;
    private Button saveBtn;
    private ArrayList<Receipt> receipts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        loadData();

        storeNameTxt = findViewById(R.id.storeNameTxt);
        receiptDate = findViewById(R.id.receiptDate);
        totalTxt = findViewById(R.id.totalTxt);
        itemsList = findViewById(R.id.itemsLst);
        payTypeSpin = findViewById(R.id.payTypeSpin);
        saveBtn = findViewById(R.id.saveBtn);


        receipt = getReceipt();

        items = receipt.getItems();

        storeNameTxt.setText(receipt.getStoreName());
        receiptDate.setText(receipt.getCaptureDate());
        totalTxt.setText(receipt.getTotalString());

        CustomItemAdaptor customItemAdaptor = new CustomItemAdaptor(this, items);
        itemsList.setAdapter(customItemAdaptor);

        saveBtn.setOnClickListener(this);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedp", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("receipts", null);
        Type type = new TypeToken<ArrayList<Receipt>>() {}.getType();
        receipts = gson.fromJson(json, type);

        if (receipts == null) {
            receipts = new ArrayList<>();
        }
    }

    private Receipt getReceipt() {
        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        Gson gson = new Gson();
        String json = mPrefs.getString("receiptObj", "");
        Receipt receipt = gson.fromJson(json, Receipt.class);
        return receipt;
    }

    

    @Override
    public void onClick(View view) {
        receipts.add(receipt);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedp", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(receipts);
        editor.putString("receipts", json);
        editor.apply();

        Log.i("arrayCount", Integer.toString(receipts.size()));
    }
}
