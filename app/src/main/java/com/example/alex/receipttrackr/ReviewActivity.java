package com.example.alex.receipttrackr;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Receipt receipt;
    private static final long serialVersionUID = 1L;
    private EditText storeNameTxt, receiptDate, totalTxt, paymentMethodTxt;
    private ListView itemList;
    private Integer noItems;
    private ArrayList<Item> items;
    private Button saveBtn;
    private ArrayList<Receipt> receipts;
    private DataStore dataStore;
    private ItemListAdaptor itemListAdaptor;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        dataStore = new DataStore(this);
        receipts = dataStore.loadReceipts();
        Log.i("arrayCount", Integer.toString(receipts.size()));

        storeNameTxt = findViewById(R.id.storeNameTxt);
        receiptDate = findViewById(R.id.receiptDateTxt);
        totalTxt = findViewById(R.id.totalTxt);
        itemList = findViewById(R.id.itemsLst);
        paymentMethodTxt = findViewById(R.id.paymentMethodTxt);
        saveBtn = findViewById(R.id.saveBtn);

        receipt = dataStore.loadReceipt();

        items = receipt.getItems();

        setReceiptFields();

        setupItemList();

        saveBtn.setOnClickListener(this);
    }

    private void setReceiptFields() {
        storeNameTxt.setText(receipt.getStoreName());
        receiptDate.setText(receipt.getPrintedDate());
        totalTxt.setText(receipt.getTotalString());
        paymentMethodTxt.setText(receipt.getPaymentMethod());
    }

    @Override
    public void onClick(View view) {

        dataStore.saveReceiptToArray(receipt);

        ArrayList<Receipt> receipts = dataStore.loadReceipts();
        Integer size = receipts.size();

        Log.i("arrayCountSaved", Integer.toString(receipts.get(size-1).getTotalPrice()));

        Intent myIntent = new Intent(ReviewActivity.this, ViewReceiptsActivity.class);
        startActivity(myIntent);
    }


    private void setupItemList() {
        itemListAdaptor = new ItemListAdaptor(this, items);
        itemList.setAdapter(itemListAdaptor);
        itemList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReviewActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.item_dialog, null);
        final EditText itemName = mView.findViewById(R.id.itemNameTxt);
        final EditText itemPrice = mView.findViewById(R.id.itemPriceTxt);
        Button saveBtn = mView.findViewById(R.id.saveBtn);
        Button deleteBtn = mView.findViewById(R.id.deleteBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(i).setName(itemName.getText().toString());
                items.get(i).setPrice(itemPrice.getText().toString());
                totalTxt.setText(receipt.getTotalString());
                itemListAdaptor.notifyDataSetChanged();
                dialog.hide();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(i);
                totalTxt.setText(receipt.getTotalString());
                setupItemList();
                dialog.hide();
            }
        });

        itemName.setText(items.get(i).getName().toString());
        itemPrice.setText(items.get(i).getPriceString());

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }
}
