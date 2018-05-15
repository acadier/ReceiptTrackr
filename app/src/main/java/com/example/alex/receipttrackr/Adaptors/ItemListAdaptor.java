package com.example.alex.receipttrackr.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alex.receipttrackr.Classes.Item;
import com.example.alex.receipttrackr.R;

import java.util.ArrayList;

/**
 * Created by Alex on 13/04/2018.
 */

public class ItemListAdaptor extends BaseAdapter {
    private Integer count;
    private Context context;
    private ArrayList<Item> items;

    public ItemListAdaptor(Context context, ArrayList<Item> items) {
        this.count = items.size();
        this.context = context;
        this.items = items;
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
        view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, null);

        TextView itemName = view.findViewById(R.id.itemNameTxt);
        TextView itemPrice = view.findViewById(R.id.itemPriceTxt);

        Item item = items.get(i);

        itemName.setText(item.getName());
        itemPrice.setText(item.getPriceString());

        return view;
    }
}
