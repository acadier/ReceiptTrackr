package com.example.alex.receipttrackr;

import android.util.Log;
import java.io.Serializable;

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

    public void setItemName(String inItemName) {
        this.itemName = inItemName;
    }

    public void setItemPrice(String inItemPrice) {
        StringBuilder sb = new StringBuilder();

        for (Character c : inItemPrice.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
//        Log.e("Item price", itemPrice.toString());
        this.itemPrice = Integer.parseInt(sb.toString());

    }

    public String getItemName() {
        Log.e("ItemName", this.itemName);
        return this.itemName;
    }

    public Integer getItemPrice() {
        return this.itemPrice;
    }
 }
