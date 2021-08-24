package com.mss.quriq.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mss.quriq.R;
import com.mss.quriq.Session.Session;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterRedeem extends ArrayAdapter<String> {

    private Activity context;
    private Context mContext;
    private ArrayList<String> nmr = new ArrayList<>();
    private ArrayList<String> nama_voucher = new ArrayList<>();
    private ArrayList<String> nilai_voucher = new ArrayList<>();
    private ArrayList<String> ketentuan = new ArrayList<>();
    private ArrayList<String> masa_berlaku = new ArrayList<>();
    private OnEditLocationListener klaim;
    NumberFormat formatRupiah;
    Session session;

    public AdapterRedeem(Activity context, ArrayList<String> nmr, ArrayList<String> nama_voucher, ArrayList<String> nilai_voucher, ArrayList<String> ketentuan, ArrayList<String> masa_berlaku, OnEditLocationListener klaim) {
        super(context, R.layout.adapter_voucher_reedem, nmr);

        this.context = context;
        this.mContext = context;
        this.nmr = nmr;
        this.nama_voucher = nama_voucher;
        this.nilai_voucher = nilai_voucher;
        this.ketentuan = ketentuan;
        this.masa_berlaku = masa_berlaku;
        this.klaim = klaim;
        session = new Session(mContext);

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
            v = layoutInflater.inflate(R.layout.adapter_voucher_reedem, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.sk.setText("Nikmati voucher belanja "+formatRupiah.format(Double.parseDouble(nilai_voucher.get(position))).replace(",00", "")+" tanpa minimum pembelian di semua outlet LaRisso");
        viewHolder.judul.setText(nama_voucher.get(position));
        viewHolder.poin.setText(ketentuan.get(position));
        viewHolder.masa_berlaku.setText(masa_berlaku.get(position)+" hari");
        viewHolder.klaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (klaim != null) {
                    klaim.onClickAdapter(position);
                }
            }
        });

        return v;
    }

    class ViewHolder{
        TextView sk, judul, poin, masa_berlaku, klaim;
        ViewHolder(View view){
            sk = view.findViewById(R.id.sk);
            judul = view.findViewById(R.id.judul);
            poin = view.findViewById(R.id.poin);
            masa_berlaku = view.findViewById(R.id.masa_berlaku);
            klaim = view.findViewById(R.id.klaim);
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
