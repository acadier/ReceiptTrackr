package com.example.alex.receipttrackr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView homeListView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
            Intent myIntent = new Intent(HomeActivity.this, CaptureReceipt.class);
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
}
