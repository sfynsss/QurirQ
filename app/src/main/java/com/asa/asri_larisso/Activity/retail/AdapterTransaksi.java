package com.asa.asri_larisso.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asa.asri_larisso.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterTransaksi extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> no_ent = new ArrayList<>();
    private ArrayList<Integer> jml_item = new ArrayList<>();
    private ArrayList<String> tgl_transaksi = new ArrayList<>();
    private ArrayList<Integer> subtot = new ArrayList<>();
    NumberFormat formatRupiah;

    public AdapterTransaksi(Activity context, ArrayList<String> no_ent, ArrayList<Integer> jml_item, ArrayList<String> tgl_transaksi, ArrayList<Integer> subtot) {
        super(context, R.layout.adapter_transaksi, no_ent);

        this.context = context;
        this.no_ent = no_ent;
        this.jml_item = jml_item;
        this.tgl_transaksi = tgl_transaksi;
        this.subtot = subtot;

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_transaksi, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.no_ent.setText(no_ent.get(position));
        viewHolder.jml_item.setText(jml_item.get(position)+"");
        viewHolder.tgl_transaksi.setText(tgl_transaksi.get(position));
        viewHolder.subtot.setText(formatRupiah.format(subtot.get(position)));

        return v;
    }

    class ViewHolder{
        TextView no_ent, jml_item, tgl_transaksi, subtot;
        ViewHolder(View view){
            no_ent = view.findViewById(R.id.no_ent);
            jml_item = view.findViewById(R.id.jml_item);
            tgl_transaksi = view.findViewById(R.id.tgl_transaksi);
            subtot = view.findViewById(R.id.subtot);
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
