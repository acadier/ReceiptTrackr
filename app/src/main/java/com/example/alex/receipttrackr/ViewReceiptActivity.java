package com.example.alex.receipttrackr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewReceiptActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView storeNameTxt, receiptDateTxt, totalTxt, paymentMethodTxt;
    private Receipt receipt;
    private ListView itemList;
    private DataStore dataStore;
    private ArrayList<Receipt> receipts;
    private ItemListAdaptor itemListAdaptor;
    private ArrayList<Item> items;
    private Button deleteBtn;
    private Integer receiptIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);

        storeNameTxt = findViewById(R.id.storeNameTxt);
        receiptDateTxt = findViewById(R.id.receiptDateTxt);
        totalTxt = findViewById(R.id.totalTxt);
        paymentMethodTxt = findViewById(R.id.paymentMethodTxt);
        itemList = findViewById(R.id.itemsLst);
        deleteBtn = findViewById(R.id.deleteBtn);


        deleteBtn.setOnClickListener(this);

        Intent intent = getIntent();

        dataStore = new DataStore(this);
        receipts = dataStore.loadReceipts();

        receiptIndex = intent.getIntExtra("index", 0);
        receipt = receipts.get(receiptIndex);
        items = receipt.getItems();

        setupItemList();
        setReceiptFields();

    }
    private void setupItemList() {
        itemListAdaptor = new ItemListAdaptor(this, items);
        itemList.setAdapter(itemListAdaptor);
    }

    private void setReceiptFields() {
        storeNameTxt.setText(receipt.getStoreName());
        receiptDateTxt.setText(receipt.getPrintedDate());
        totalTxt.setText(receipt.getTotalString());
        paymentMethodTxt.setText(receipt.getPaymentMethod());
    }

    @Override
    public void onClick(View view) {
        dataStore.removeReceiptFromArray(receiptIndex);
        Intent myIntent = new Intent(ViewReceiptActivity.this, ViewReceiptsActivity.class);
        startActivity(myIntent);
    }
}
