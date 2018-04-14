package com.example.alex.receipttrackr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class DataStore {
    private Context context;
    private SharedPreferences sharedPreferences;
    private ArrayList<Receipt> receipts;
    private Gson gson;
    private String json;
    private Type type;

    public DataStore(Context context) {
        this.context = context;
    }

    public ArrayList<Receipt> loadReceipts() {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences("sharedp", 0);
        gson = new Gson();
        json = sharedPreferences.getString("receipts", null);
        type = new TypeToken<ArrayList<Receipt>>() {}.getType();
        receipts = gson.fromJson(json, type);

        if (receipts == null) {
            receipts = new ArrayList<>();
        }

        return receipts;
    }

    public void saveReceiptToArray(Receipt receipt) {
        receipts.add(receipt);

//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences("sharedp", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();
        json = gson.toJson(receipts);
        editor.putString("receipts", json);
        editor.apply();
    }

    public Receipt loadReceipt() {
        sharedPreferences = context.getSharedPreferences("IDvalue",0);
        gson = new Gson();
        json = sharedPreferences.getString("receiptObj", "");
        Receipt receipt = gson.fromJson(json, Receipt.class);
        return receipt;
    }

    public void saveReceipt(Receipt receipt) {
        sharedPreferences = context.getSharedPreferences("IDvalue",0);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        gson = new Gson();
        json = gson.toJson(receipt);
        prefsEditor.putString("receiptObj", json);
        prefsEditor.commit();
    }

    public void saveBudget(Integer budgetValue) {
        sharedPreferences = context.getSharedPreferences("budgetPref", 0);  //Context.MODE_PRIVATE
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        prefsEditor.putInt("savedBudget", budgetValue);
        prefsEditor.apply();
    }

    public Integer loadBudget() {
        sharedPreferences = context.getSharedPreferences("budgetPref", 0);  //Context.MODE_PRIVATE

        Integer budgetValue = sharedPreferences.getInt("savedBudget", 0);
        return budgetValue;
    }

}
