package com.example.alex.receipttrackr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 14/04/2018.
 */

public class ReceiptListAdaptor extends BaseAdapter {
    private Integer count;
    private Context context;
    private ArrayList<Receipt> receipts;

    public ReceiptListAdaptor(Context context, ArrayList<Receipt> receipts) {
        this.count = receipts.size();
        this.context = context;
        this.receipts = receipts;
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.receipt_list_layout, null);

        TextView dateTxt = view.findViewById(R.id.dateTxt);
        TextView totalTxt = view.findViewById(R.id.totalTxt);
        TextView storeNameTxt = view.findViewById(R.id.storeNameTxt);

        dateTxt.setText(receipts.get(i).getCaptureDate());
        totalTxt.setText(receipts.get(i).getTotalString());
        storeNameTxt.setText(receipts.get(i).getStoreName());

        return view;
    }
}
