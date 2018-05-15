package com.example.alex.receipttrackr;

import android.util.Log;

public class Price {

    public String getPriceString(Integer price) {
        String str = Integer.toString(price);
        Integer length = str.length();

        if (length < 2) {
            str = new StringBuffer(str).insert(0, "00").toString();
        }
        else if (length < 3) {
            str = new StringBuffer(str).insert(0, "0").toString();
        }

        str = new StringBuffer(str).insert(str.length()-2, ".").toString();

        Log.e("getPri", str);
        return str;
    }
}
