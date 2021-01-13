package com.asa.asri_larisso.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asa.asri_larisso.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.cardview.widget.CardView;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterVoucher extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> kd_voucher = new ArrayList<>();
    private ArrayList<String> nama_voucher = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private ArrayList<String> nilai_voucher = new ArrayList<>();
    private ArrayList<String> sk = new ArrayList<>();
    private ArrayList<String> tgl_berlaku = new ArrayList<>();
    NumberFormat formatRupiah;
    private OnEditLocationListener btn_gunakan;

    public AdapterVoucher(Activity context, ArrayList<String> kd_voucher, ArrayList<String> nama_voucher, ArrayList<String> gambar, ArrayList<String> nilai_voucher, ArrayList<String> sk, ArrayList<String> tgl_berlaku, OnEditLocationListener btn_gunakan) {
        super(context, R.layout.adapter_voucher, kd_voucher);

        this.context = context;
        this.kd_voucher = kd_voucher;
        this.nama_voucher = nama_voucher;
        this.gambar = gambar;
        this.nilai_voucher = nilai_voucher;
        this.sk = sk;
        this.tgl_berlaku = tgl_berlaku;
        this.btn_gunakan = btn_gunakan;

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
            v = layoutInflater.inflate(R.layout.adapter_voucher, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.judul_voucher.setText(nama_voucher.get(position));
        viewHolder.sk.setText(sk.get(position));
        viewHolder.tgl_berlaku.setText(tgl_berlaku.get(position));
        viewHolder.btn_gunakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_gunakan != null) {
                    System.out.println("di click");
                    btn_gunakan.onClickAdapter(position);
                }
            }
        });

        return v;
    }

    class ViewHolder{
        CardView item_list;
        ImageView gambar;
        TextView judul_voucher, sk, tgl_berlaku;
        Button btn_gunakan;
        ViewHolder(View view){
            item_list = view.findViewById(R.id.item_list);
            item_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            judul_voucher = view.findViewById(R.id.judul_voucher);
            gambar = view.findViewById(R.id.gambar_voucher);
            sk = view.findViewById(R.id.sk);
            tgl_berlaku = view.findViewById(R.id.tgl_berlaku);
            btn_gunakan = view.findViewById(R.id.btn_gunakan);
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
