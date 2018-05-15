package com.example.alex.receipttrackr.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.alex.receipttrackr.DataStore;
import com.example.alex.receipttrackr.R;
import com.example.alex.receipttrackr.Receipt;

import java.util.ArrayList;
import java.util.Date;

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



        totalSpentTxt.setText(priceToString(getTotalSpentSinceLastWeek()));

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
        Integer length = str.length();

        if (length < 2) {
            str = new StringBuffer(str).insert(0, "00").toString();
        }
        else if (length < 3) {
            str = new StringBuffer(str).insert(0, "0").toString();
        }

        str = new StringBuffer(str).insert(str.length()-2, ".").toString();

        Log.e("getPric", str);
        return str;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setBudget();
    }

    public Integer getTotalSpent() {
        Integer totalSpent = 0;
        for (Receipt r : receipts) {
            totalSpent = totalSpent + r.getTotalPrice();
        }
        return totalSpent;
    }

    public Integer getTotalSpentSinceLastWeek() {
        Date lastWeek = getPreviousWeekDate();
        Integer totalSpent = 0;
        for (Receipt r : receipts) {
            if (r.getCaptureDate().after(lastWeek)) {
                totalSpent = totalSpent + r.getTotalPrice();
            }
        }
        return totalSpent;
    }

    private Date getPreviousWeekDate(){
        return new Date(System.currentTimeMillis()-7*24*60*60*1000);
    }
}
