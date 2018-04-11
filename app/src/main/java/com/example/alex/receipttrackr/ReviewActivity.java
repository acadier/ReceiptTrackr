package com.example.alex.receipttrackr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.parceler.Parcels;

import java.io.Serializable;

public class ReviewActivity extends AppCompatActivity {
    Receipt receipt;
    private static final long serialVersionUID = 1L;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        txt = findViewById(R.id.storeNameLbl);

        Receipt receipt = Parcels.unwrap(getIntent().getParcelableExtra("example"));
//        Log.e("okay", receipt.getCaptureDate());

        if(receipt == null) {
            Log.i("itsnull","itsnull");
        }
    }
}
