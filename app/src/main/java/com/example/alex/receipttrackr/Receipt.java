package com.example.alex.receipttrackr;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Alex on 01/04/2018.
 */

public class Receipt {
    String dateTime, storeName, itemCost, itemName, totalCost, ocrText;

    Receipt(String inString) throws IOException {
        ocrText = inString;
        readLine();

    }

    private String getStoreName(){
        return null;
    }

    private String readLine() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(ocrText));
        String line = null;

        while ((line = bufferedReader.readLine()) != null)
        {
            Log.e("OCR Line", line);
            isPrice(line);
        }
        return line;
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
