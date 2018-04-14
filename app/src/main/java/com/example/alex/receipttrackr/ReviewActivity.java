package com.example.alex.receipttrackr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private Receipt receipt;
    private static final long serialVersionUID = 1L;
    private EditText storeNameTxt, receiptDate, totalTxt;
    private ListView itemList;
    private Spinner payTypeSpin;
    private Integer noItems;
    private ArrayList<Item> items;
    private Button saveBtn;
    private ArrayList<Receipt> receipts;
    private DataStore dataStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        dataStore = new DataStore(this);
        receipts = dataStore.loadReceipts();
        Log.i("arrayCount", Integer.toString(receipts.size()));

        storeNameTxt = findViewById(R.id.storeNameTxt);
        receiptDate = findViewById(R.id.receiptDate);
        totalTxt = findViewById(R.id.totalTxt);
        itemList = findViewById(R.id.itemsLst);
        payTypeSpin = findViewById(R.id.payTypeSpin);
        saveBtn = findViewById(R.id.saveBtn);

        receipt = dataStore.loadReceipt();

        items = receipt.getItems();

        storeNameTxt.setText(receipt.getStoreName());
        receiptDate.setText(receipt.getCaptureDate());
        totalTxt.setText(receipt.getTotalString());

        ItemListAdaptor ItemListAdaptor = new ItemListAdaptor(this, items);
        itemList.setAdapter(ItemListAdaptor);

        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dataStore.saveReceiptToArray(receipt);

        ArrayList<Receipt> receipts = dataStore.loadReceipts();
        Integer size = receipts.size();

        Log.i("arrayCountSaved", Integer.toString(receipts.get(size-1).getTotalPrice()));
    }
}
