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

public class AdapterDetailTransaksi extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> nm_brg = new ArrayList<>();
    private ArrayList<Integer> qty = new ArrayList<>();
    private ArrayList<Integer> subtot = new ArrayList<>();
    NumberFormat formatRupiah;

    public AdapterDetailTransaksi(Activity context, ArrayList<String> nm_brg, ArrayList<Integer> qty, ArrayList<Integer> subtot) {
        super(context, R.layout.adapter_detail_transaksi, nm_brg);

        this.context = context;
        this.nm_brg = nm_brg;
        this.qty = qty;
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
            v = layoutInflater.inflate(R.layout.adapter_detail_transaksi, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.nm_brg.setText(nm_brg.get(position));
        viewHolder.jml_brg.setText("x"+qty.get(position));
        viewHolder.subtot.setText(formatRupiah.format(subtot.get(position)));

        return v;
    }

    class ViewHolder{
        TextView nm_brg, jml_brg, subtot;
        ViewHolder(View view){
            nm_brg = view.findViewById(R.id.nm_brg);
            jml_brg = view.findViewById(R.id.jml_brg);
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
