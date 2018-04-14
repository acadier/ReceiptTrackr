package com.example.alex.receipttrackr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
}
