package com.example.alex.receipttrackr.Activities;

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
import android.widget.TextView;

import com.example.alex.receipttrackr.Classes.DataStore;
import com.example.alex.receipttrackr.Classes.Item;
import com.example.alex.receipttrackr.Adaptors.ItemListAdaptor;
import com.example.alex.receipttrackr.R;
import com.example.alex.receipttrackr.Classes.Receipt;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Receipt receipt;
    private static final long serialVersionUID = 1L;
    private EditText storeNameTxt, receiptDate, paymentMethodTxt;
    private TextView totalTxt;
    private ListView itemList;
    private Integer noItems;
    private ArrayList<Item> items;
    private Button saveBtn, retakeBtn, homeBtn, addDiscountBtn;
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
        retakeBtn = findViewById(R.id.retakeBtn);
        homeBtn = findViewById(R.id.homeBtn);
        addDiscountBtn = findViewById(R.id.addDiscountBtn);


        receipt = dataStore.loadReceipt();

        items = receipt.getItems();

        setReceiptFields();

        setupItemList();

        saveBtn.setOnClickListener(this);
        retakeBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        addDiscountBtn.setOnClickListener(this);
    }

    private void setReceiptFields() {
        storeNameTxt.setText(receipt.getStoreName());
        receiptDate.setText(receipt.getPrintedDate());
        totalTxt.setText(receipt.getTotalString());
        paymentMethodTxt.setText(receipt.getPaymentMethod());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveBtn:
                saveReceipt();
                showViewReceiptsActivity();
                break;

            case R.id.homeBtn:
                showViewReceiptsActivity();
                break;

            case R.id.retakeBtn:
                showCaptureReceiptActivity();
                break;

            case R.id.addDiscountBtn:
                addDiscount();
                break;
        }
    }

    private void showCaptureReceiptActivity() {
        Intent myIntent = new Intent(ReviewActivity.this, CaptureReceiptActivity.class);
        startActivity(myIntent);
    }

    private void showViewReceiptsActivity() {
        Intent myIntent = new Intent(ReviewActivity.this, ViewReceiptsActivity.class);
        startActivity(myIntent);
    }

    private void saveReceipt() {
        dataStore.saveReceiptToArray(receipt);

        ArrayList<Receipt> receipts = dataStore.loadReceipts();
        Integer size = receipts.size();

        Log.i("arrayCountSaved", Integer.toString(receipts.get(size-1).getTotalPrice()));
    }


    private void setupItemList() {
        itemListAdaptor = new ItemListAdaptor(this, items);
        itemList.setAdapter(itemListAdaptor);
        itemList.setOnItemClickListener(this);
    }

    private void addDiscount() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReviewActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.discount_dialog, null);
        final EditText itemName = mView.findViewById(R.id.itemNameTxt);
        final EditText itemPrice = mView.findViewById(R.id.itemPriceTxt);
        Button saveBtn = mView.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item discount = new Item();

                discount.setName(itemName.getText().toString());
                discount.setPrice(itemPrice.getText().toString(), true);

                items.add(discount);
                totalTxt.setText(receipt.getTotalString());

                setupItemList();
                dialog.hide();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
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
                items.get(i).setPrice(itemPrice.getText().toString(), false);
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
