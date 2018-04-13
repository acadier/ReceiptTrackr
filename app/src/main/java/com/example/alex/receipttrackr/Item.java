package com.example.alex.receipttrackr;

import android.util.Log;

@org.parceler.Parcel
public class Item {
    private String itemName;
    private Integer itemPrice;
//    private static final long serialVersionUID = 1L;

    public Item() {

    }
    public Item(String inItemName) {
        this.itemName = inItemName;
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
//        Log.e("Item price", itemPrice.toString());
        this.itemPrice = Integer.parseInt(sb.toString());

    }

    public String getName() {
        Log.e("ItemName", this.itemName);
        return this.itemName;
    }

    public Integer getPrice() {
        return this.itemPrice;
    }

    public String getPriceString() {
        String str = Integer.toString(getPrice());
        str = new StringBuffer(str).insert(str.length()-2, ".").toString();
        return str;
    }
 }
