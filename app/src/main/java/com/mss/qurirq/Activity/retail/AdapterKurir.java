package com.mss.qurirq.Activity.retail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.mss.qurirq.R;

import java.util.ArrayList;

public class AdapterKurir extends ArrayAdapter<Integer> {

    ArrayList<Integer> images = new ArrayList<>();
    private Context context;

    public AdapterKurir(Context context, ArrayList<Integer> images) {
        super(context, android.R.layout.simple_spinner_item, images);
        this.images = images;
        this.context=context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    private View getImageForPosition(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_kurir, parent, false);
        ImageView imageView = (ImageView)row.findViewById(R.id.logo_kurir);
        imageView.setImageResource(images.get(position));
        return row;
    }
}
