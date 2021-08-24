package com.mss.quriq.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mss.quriq.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterAlamat extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> nama_penerima = new ArrayList<>();
    private ArrayList<String> alamat = new ArrayList<>();
    private OnEditLocationListener ubah_alamat;

    public AdapterAlamat(Activity context, ArrayList<String> nama_penerima, ArrayList<String> alamat, OnEditLocationListener ubah_alamat) {
        super(context, R.layout.adapter_alamat, nama_penerima);

        this.context = context;
        this.nama_penerima = nama_penerima;
        this.alamat = alamat;
        this.ubah_alamat = ubah_alamat;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_alamat, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.nama_penerima.setText(nama_penerima.get(position));
        viewHolder.alamat.setText(alamat.get(position));
        viewHolder.ubah_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ubah_alamat != null) {
                    ubah_alamat.onClickAdapter(position);
                }
            }
        });

        return v;
    }

    class ViewHolder{
        TextView nama_penerima;
        TextView alamat, ubah_alamat;
        ViewHolder(View view){
            nama_penerima = view.findViewById(R.id.nama_penerima);
            alamat = view.findViewById(R.id.alamat);
            ubah_alamat = view.findViewById(R.id.ubah_alamat);
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
