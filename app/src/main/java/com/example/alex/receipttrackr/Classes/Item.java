package com.example.alex.receipttrackr.Classes;

import android.util.Log;

@org.parceler.Parcel
public class Item {
    private String itemName;
    private Integer itemPrice;
//    private static final long serialVersionUID = 1L;

    public Item() {
        this.itemName = "";
        this.itemPrice = 0;
    }
    public Item(String inItemName) {
        this.itemName = inItemName;
        this.itemPrice = 0;
    }

    public void setName(String inItemName) {
        this.itemName = inItemName;
    }

    public void setPrice(String inItemPrice) {
        StringBuilder sb = new StringBuilder();

        for (Character c : inItemPrice.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }

        if (sb.toString() == "") {
            this.itemPrice = 0;
        }
        else
        {
            this.itemPrice = Integer.parseInt(sb.toString());
//            Log.e("price", itemPrice.toString());
        }

    }

    public String getName() {
//        Log.e("ItemName", this.itemName);
        return this.itemName;
    }

    public Integer getPrice() {
        return this.itemPrice;
    }

    public String getPriceString() {
        String str = Integer.toString(getPrice());
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
    }
 }
