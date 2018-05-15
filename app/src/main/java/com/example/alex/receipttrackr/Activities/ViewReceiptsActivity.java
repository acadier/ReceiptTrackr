package com.example.alex.receipttrackr.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.alex.receipttrackr.Classes.DataStore;
import com.example.alex.receipttrackr.R;
import com.example.alex.receipttrackr.Classes.Receipt;
import com.example.alex.receipttrackr.Adaptors.ReceiptListAdaptor;

import java.util.ArrayList;

public class ViewReceiptsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Receipt> receipts;
    private ListView receiptLstView;
    private DataStore dataStore;
    private ProgressBar progressBar;
    private TextView noReceiptsInfoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipts);

        dataStore = new DataStore(this);
        receipts = dataStore.loadReceipts();
        progressBar = findViewById(R.id.progressBar);
        noReceiptsInfoTxt = findViewById(R.id.noReceiptsInfoTxt);

        Log.i("yoga", Integer.toString(receipts.size()));

        receiptLstView = findViewById(R.id.receiptLstView);

        ReceiptListAdaptor receiptListAdaptor = new ReceiptListAdaptor(this, receipts);
        receiptLstView.setAdapter(receiptListAdaptor);
        receiptLstView.setOnItemClickListener(this);
        checkforNoReceipts();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        int itemId = item.getItemId();

        if (itemId == R.id.action_budget)  {
            Intent myIntent = new Intent(ViewReceiptsActivity.this, BudgetActivity.class);
            startActivity(myIntent);
            progressBar.setVisibility(View.GONE);
        }
        else {
            Intent myIntent = new Intent(ViewReceiptsActivity.this, CaptureReceiptActivity.class);
            startActivity(myIntent);
            progressBar.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent myIntent = new Intent(ViewReceiptsActivity.this, ViewReceiptActivity.class);
        myIntent.putExtra("index", i);

        startActivity(myIntent);
    }

    private void checkforNoReceipts() {
        if (receipts.size() == 0) {
            noReceiptsInfoTxt.setVisibility(View.VISIBLE);
        }
    }
}