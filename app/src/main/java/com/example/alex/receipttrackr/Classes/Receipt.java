package com.example.alex.receipttrackr.Classes;

import android.util.Log;

import com.example.alex.receipttrackr.Classes.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@org.parceler.Parcel
public class Receipt {
    private String rawText, storeName, paymentMethod, printedDate;
    private ArrayList<Item> items;
    private Date receiptDate, captureDate;
    private transient DateFormat dateFormat;

    public Receipt() {
        items = new ArrayList<>();
        captureDate = new Date();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        rawText = "";
        storeName = "";
        paymentMethod = "";
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Integer setItemNames(String itemLines) throws IOException {
        Integer count = 0;
        StringReader stringReader = new StringReader(itemLines);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        String itemName = null;
        while ((itemName = readLine(bufferedReader)) != null) {
            Item newItem = new Item(itemName);
            items.add(newItem);
            count++;
        }
        Log.e("namesCount", count.toString());
        return count;
    }

    public Boolean setItemPrices(String priceLines, Integer noItems) throws IOException {
        StringReader stringReader = new StringReader(priceLines);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Integer index = 0;

        String itemPrice = null;
        while ((itemPrice = readLine(bufferedReader)) != null) {
            if (index >= noItems) {
                Log.e("count","hit");
                return false;
            }

            Log.e("count", index.toString() + " " + noItems.toString());
            items.get(index).setPrice(itemPrice, false);
            index++;

        }
        if (index == noItems) {
            Log.e("pass",index.toString() + " " + noItems.toString());
            return true;
        } else {
            Log.e("fail","fail");
            return false;
        }

    }

    public Integer countLines(String lines) throws IOException {
        StringReader stringReader = new StringReader(lines);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Integer count = 0;

        while ((bufferedReader.readLine()) != null) {
            count++;
        }
        return count;
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

    public String getNoItemsAsString() {
        return String.valueOf(items.size());
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

        Integer length = str.length();

        if (length < 2) {
            str = new StringBuffer(str).insert(0, "00").toString();
        }
        else if (length < 3) {
            str = new StringBuffer(str).insert(0, "0").toString();
        }

        str = new StringBuffer(str).insert(str.length()-2, ".").toString();

        Log.e("getPrice", str);
        return str;

//        return getPriceString(getTotalPrice());
    }

    public String getCaptureDateString() {
        return dateFormat.format(captureDate).toString();
    }

    public Date getCaptureDate() {
        return this.captureDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getReceiptDate() {
        return receiptDate.toString();
    }


    public void setStoreName(String[] supermarkets) {
        for (String supermarket : supermarkets) {
            if (rawText.toLowerCase().contains(supermarket.toLowerCase())) {
                storeName = supermarket;
            }
        }
    }

    public void setPaymentMethod(String[] methods) {
        for (String paymentMethod : methods) {
            if (rawText.toLowerCase().contains(paymentMethod.toLowerCase())) {
                this.paymentMethod = paymentMethod;
            }
        }
    }

    public void setPrintedDate() {
//        try {
//            List<Date> dates = new Parser().parse(rawText).get(0).getDates();
//            printedDate = dates.get(0).toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public String getPrintedDate() {
        return this.printedDate;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
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
