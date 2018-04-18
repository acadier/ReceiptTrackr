package com.example.alex.receipttrackr;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@org.parceler.Parcel
public class Receipt {
    private String rawText, storeName;
    private ArrayList<Item> items;
//    private StringReader stringReader;
//    private BufferedReader bufferedReader;
    private Date receiptDate, captureDate;
    private transient DateFormat dateFormat;
//    private static final long serialVersionUID = 1L;

    public Receipt() {
        items = new ArrayList<>();
        captureDate = new Date();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        rawText = null;
        storeName = null;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public Boolean setItemNames(String itemLines) throws IOException {
        StringReader stringReader = new StringReader(itemLines);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        String itemName = null;
        while ((itemName = readLine(bufferedReader)) != null) {
            Item newItem = new Item(itemName);
            items.add(newItem);
        }
        return true;
    }

    public Boolean setItemPrices(String priceLines) throws IOException {
        StringReader stringReader = new StringReader(priceLines);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Integer index = 0;

        String itemPrice = null;
        while ((itemPrice = readLine(bufferedReader)) != null) {
            items.get(index).setPrice(itemPrice);
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

    public String getPriceToString(Integer price) {
        String str = Integer.toString(price);
        str = new StringBuffer(str).insert(str.length()-2, ".").toString();
        return str;
    }

    public Integer getTotalPrice() {
        Integer total = 0;
        Log.i("totalStart",total.toString());
        for (Item item : items) {
            total = total + item.getPrice();
            Log.i("totalLoop",total.toString());
        }
        return total;
    }

    public String getTotalString() {
        String str = Integer.toString(getTotalPrice());

        if (str.length() > 2) {
            str = new StringBuffer(str).insert(str.length()-2, ".").toString();
            return str;
        }
        return str;
    }

    public String getCaptureDate() {
        return dateFormat.format(captureDate).toString();
    }

    public String getStoreName() {
        return storeName;
    }

    public String getReceiptDate() {
        return receiptDate.toString();
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

    public Item getItem(Integer index) {
        return items.get(index);
    }

    public void setItem(Item item, Integer index) {
        items.set(index, item);
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
