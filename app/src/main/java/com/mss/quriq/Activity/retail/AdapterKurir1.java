package com.mss.quriq.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mss.quriq.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterKurir1 extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<String> nama_kurir = new ArrayList<>();

    public AdapterKurir1(Activity context, ArrayList<Integer> images, ArrayList<String> nama_kurir) {
        super(context, R.layout.adapter_kurir, nama_kurir);

        this.context = context;
        this.images = images;
        this.nama_kurir = nama_kurir;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_kurir, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.gambar_kurir.setImageResource(images.get(position));
        viewHolder.nama_kurir.setText(nama_kurir.get(position));

        return v;
    }

    class ViewHolder{
        ImageView gambar_kurir;
        TextView nama_kurir;
        ViewHolder(View view){
            gambar_kurir = view.findViewById(R.id.gambar_kurir);
            nama_kurir = view.findViewById(R.id.nama_kurir);
        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    public interface OnEditLocationListener {
        void onClickAdapter(int position);
    }

}
