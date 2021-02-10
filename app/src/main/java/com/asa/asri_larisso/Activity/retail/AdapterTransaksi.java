package com.asa.asri_larisso.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
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
    private ArrayList<String> jml_item = new ArrayList<>();
    private ArrayList<String> tgl_transaksi = new ArrayList<>();
    private ArrayList<String> subtot = new ArrayList<>();
    private ArrayList<String> sts_byr = new ArrayList<>();
    private ArrayList<String> jns_pengiriman = new ArrayList<>();
    private ArrayList<String> sts_transaksi = new ArrayList<>();
    NumberFormat formatRupiah;
    OnEditLocationListener lihat_status;

    public AdapterTransaksi(Activity context, ArrayList<String> no_ent, ArrayList<String> jml_item, ArrayList<String> tgl_transaksi, ArrayList<String> subtot, ArrayList<String> sts_byr, ArrayList<String> jns_pengiriman, ArrayList<String> sts_transaksi, OnEditLocationListener lihat_status) {
        super(context, R.layout.adapter_transaksi, no_ent);

        this.context = context;
        this.no_ent = no_ent;
        this.jml_item = jml_item;
        this.tgl_transaksi = tgl_transaksi;
        this.subtot = subtot;
        this.sts_byr = sts_byr;
        this.jns_pengiriman = jns_pengiriman;
        this.sts_transaksi = sts_transaksi;
        this.lihat_status = lihat_status;

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_transaksi, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.no_ent.setText(no_ent.get(position));
        viewHolder.jml_item.setText(jml_item.get(position) + "");
        viewHolder.tgl_transaksi.setText(tgl_transaksi.get(position));
        viewHolder.subtot.setText(formatRupiah.format(Double.parseDouble(subtot.get(position))).replace(",00", ""));

        if (sts_transaksi.get(position).equals("SELESAI")) {
            viewHolder.status.setText("Transaksi Selesai");
            viewHolder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.blue_notif));
        } else if (sts_transaksi.get(position).equals("BATAL")) {
            viewHolder.status.setText("Transaksi Batal");
            viewHolder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else if (sts_byr.get(position).equals("0") && !jns_pengiriman.get(position).equals("pickup")) {
            viewHolder.status.setText("Menunggu Pembayaran");
            viewHolder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else if (jns_pengiriman.get(position).equals("pickup")) {
            viewHolder.status.setText("Lihat Status Transaksi");
            viewHolder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.aquamarine_primary));
        } else {
            viewHolder.status.setText("Sudah Terbayar");
            viewHolder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.green_notif));
        }

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lihat_status != null) {
                    System.out.println("di click");
                    lihat_status.onClickAdapter(position);
                }
            }
        });

        return v;
    }

    class ViewHolder {
        TextView no_ent, jml_item, tgl_transaksi, subtot, status;
        RelativeLayout relativeLayout;

        ViewHolder(View view) {
            no_ent = view.findViewById(R.id.no_ent);
            jml_item = view.findViewById(R.id.jml_item);
            tgl_transaksi = view.findViewById(R.id.tgl_transaksi);
            subtot = view.findViewById(R.id.subtot);
            status = view.findViewById(R.id.status);
            relativeLayout = view.findViewById(R.id.relative_lay);
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
