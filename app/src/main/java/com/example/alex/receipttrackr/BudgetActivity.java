package com.example.alex.receipttrackr;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class BudgetActivity extends AppCompatActivity {

    String budgetValue;
    TextView currBudgetTxt, newBudgetTxt;
    Button newBudgetBtn;
    SeekBar seekBar;
    Integer currBudgetValue = 0, seekBarValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        currBudgetTxt = findViewById(R.id.currBudgetTxt);
        newBudgetTxt = findViewById(R.id.selectedBudgetTxt);
        newBudgetBtn = findViewById(R.id.setBudgetBtn);
        seekBar = findViewById(R.id.budgetBar);

        loadPreferences();

        newBudgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currBudgetValue = seekBarValue;
                currBudgetTxt.setText("£" + currBudgetValue.toString());
                savePreferences();
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

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences("budgetPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("savedBudget", seekBarValue);
        editor.apply();

    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences("budgetPref", Context.MODE_PRIVATE);


        Integer a = settings.getInt("savedBudget", 0);
        if (a != 0) {
            currBudgetTxt.setText("£" + a.toString());

        } else {
            currBudgetTxt.setText("Select a budget");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }



}
