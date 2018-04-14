package com.example.alex.receipttrackr;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BudgetActivity extends AppCompatActivity {

    String budgetValue;
    TextView currBudgetTxt, newBudgetTxt, totalSpentTxt;
    Button newBudgetBtn;
    SeekBar seekBar;
    Integer currBudgetValue = 0, seekBarValue = 0, totalSpent = 0;
    DataStore dataStore;
    private ArrayList<Receipt> receipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        dataStore = new DataStore(this);

        currBudgetTxt = findViewById(R.id.currBudgetTxt);
        newBudgetTxt = findViewById(R.id.selectedBudgetTxt);
        newBudgetBtn = findViewById(R.id.setBudgetBtn);
        seekBar = findViewById(R.id.budgetBar);
        totalSpentTxt = findViewById(R.id.totalSpentTxt);

        setBudget();

        receipts = dataStore.loadReceipts();

        for (Receipt r : receipts) {
            totalSpent = totalSpent + r.getTotalPrice();
        }

        totalSpentTxt.setText(priceToString(totalSpent));

        invalidateOptionsMenu();


        newBudgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currBudgetValue = seekBarValue;
                currBudgetTxt.setText("£" + currBudgetValue.toString());
                dataStore.saveBudget(seekBarValue);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                newBudgetTxt.setText("£" + String.valueOf(i));
                seekBarValue = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setBudget() {
        Integer a = dataStore.loadBudget();
        if (a != 0) {
            currBudgetTxt.setText("£" + a.toString());

        } else {
            currBudgetTxt.setText("Select a budget");
        }
    }

    public String priceToString(Integer price) {
        String str = Integer.toString(price);
        str = new StringBuffer(str).insert(str.length()-2, ".").toString();
        return str;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setBudget();
    }
}
