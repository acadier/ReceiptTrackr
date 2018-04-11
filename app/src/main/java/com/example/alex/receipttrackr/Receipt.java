package com.example.alex.receipttrackr;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Receipt {
    private String rawText, storeName;
    private ArrayList<Item> items;
    private StringReader stringReader;
    private BufferedReader bufferedReader;
    private Date receiptDate, captureDate;
    private DateFormat dateFormat;

    public Receipt() {
        this.items = new ArrayList<>();
        this.captureDate = new Date();
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public Boolean setItemNames(String itemLines) throws IOException {
        stringReader = new StringReader(itemLines);
        bufferedReader = new BufferedReader(stringReader);

        String itemName = null;
        while ((itemName = readLine(bufferedReader)) != null) {
            Item newItem = new Item(itemName);
            items.add(newItem);
        }
        return true;
    }

    public Boolean setItemPrices(String priceLines) throws IOException {
        stringReader = new StringReader(priceLines);
        bufferedReader = new BufferedReader(stringReader);
        Integer index = 0;

        String itemPrice = null;
        while ((itemPrice = readLine(bufferedReader)) != null) {
            items.get(index).setItemPrice(itemPrice);
            index++;
        }
        return true;
    }

    private String readLine(BufferedReader bufferedReader) throws IOException {
        String line = null;

        if ((line = bufferedReader.readLine()) != null) {
            return line;
        }

        return null;
    }

    public ArrayList<Item> getItems()  {
        return items;
    }

    public Integer getTotal() {
        Integer total = 0;
        for (Item item : items) {
            total = total + item.getItemPrice();
        }
        return total;
    }

    public String getCaptureDate() {
        return null;
    }

    public String getReceiptDate() {
        return dateFormat.format(receiptDate);
    }

    public Boolean setStoreName(String[] supermarkets) {
        for (String supermarket : supermarkets) {
            if (rawText.toLowerCase().contains(supermarket.toLowerCase())) {
                storeName = supermarket;
                return true;
            }
        }
        return false;
    }

    private Boolean isPrice(String inLine) {
        Integer length = inLine.length();


        if (length == 4) {

            Log.e("theLength",length.toString());

            char one = inLine.charAt(length-1);
            char ten = inLine.charAt(length - 2);
            char decimal = inLine.charAt(length - 3);
            char hundred = inLine.charAt(length - 4);

            if (Character.isDigit(one) && Character.isDigit(ten) && decimal == '.' && Character.isDigit(hundred)) { // && Character.isDigit(ten) && decimal == '.' && Character.isDigit(hundred)
                Log.e("theLine",inLine);
//                Log.e("found1", String.valueOf(decimal));
//                Log.e("found2", String.valueOf(one));
//                Log.e("found3", String.valueOf(ten));
                return true;
            }
        }
        Log.e("sorry","sorry");
        return false;
    }
}
