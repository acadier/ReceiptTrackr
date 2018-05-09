package com.example.alex.receipttrackr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewReceiptsActivity extends AppCompatActivity {
    private ArrayList<Receipt> receipts;
    private ListView receiptLstView;
    private DataStore dataStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipts);

        dataStore = new DataStore(this);
        receipts = dataStore.loadReceipts();

        Log.i("yoga", Integer.toString(receipts.size()));

        receiptLstView = findViewById(R.id.receiptLstView);

        ReceiptListAdaptor receiptListAdaptor = new ReceiptListAdaptor(this, receipts);
        receiptLstView.setAdapter(receiptListAdaptor);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(ViewReceiptsActivity.this, CaptureReceiptActivity.class);
        startActivity(myIntent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
