package com.example.alex.receipttrackr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.alex.receipttrackr.Activities.BudgetActivity;
import com.example.alex.receipttrackr.Activities.CaptureReceiptActivity;
import com.example.alex.receipttrackr.Activities.ViewReceiptsActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView homeListView;
    private ArrayAdapter<String> listAdapter;
    final int requestCode = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkPermissions();

        homeListView = findViewById(R.id.homeLstView);
        homeListView.setOnItemClickListener(this);

        String[] options = new String[] {  "Capture Receipt",
                                            "View summary",
                                            "Manage budget",
                                            "View spendings",
                                            "Remove receipts"};

        ArrayList<String> menuLst = new ArrayList<String>();
        menuLst.addAll(Arrays.asList(options));

        listAdapter = new ArrayAdapter(this, R.layout.listrow, menuLst);

        homeListView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (i == 0) {
            Intent myIntent = new Intent(HomeActivity.this, CaptureReceiptActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }

        if (i == 2) {
            Intent myIntent = new Intent(HomeActivity.this, BudgetActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }

        if (i == 3) {
            Intent myIntent = new Intent(HomeActivity.this, ViewReceiptsActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }
    }

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED); {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED); {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED); {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }

}
