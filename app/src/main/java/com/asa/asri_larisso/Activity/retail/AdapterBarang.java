package com.asa.asri_larisso.Activity.retail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sfyn on 29/06/2018.
 */

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.MyViewHolder> {

    private Context mContext;
    private Activity activity;
    private ArrayList<String> kd_brg = new ArrayList<String>();
    private ArrayList<String> gambar = new ArrayList<String>();
    private ArrayList<String> nm_brg = new ArrayList<String>();
    private ArrayList<String> kat_brg = new ArrayList<String>();
    private ArrayList<String> harga_jl = new ArrayList<String>();
    private ArrayList<String> harga_jl2 = new ArrayList<String>();
    private ArrayList<String> harga_jl3 = new ArrayList<String>();
    private ArrayList<String> harga_jl4 = new ArrayList<String>();
    private ArrayList<String> qty_min2 = new ArrayList<String>();
    private ArrayList<String> qty_min3 = new ArrayList<String>();
    private ArrayList<String> qty_min4 = new ArrayList<String>();
    private ArrayList<String> satuan = new ArrayList<String>();
    private ArrayList<String> harga_asli = new ArrayList<String>();
    private ArrayList<String> disc = new ArrayList<String>();
    private ArrayList<String> harga_disc = new ArrayList<String>();
    RecyclerView recyclerView;
    RequestOptions option;
    int pos;
    Intent it;
    Session session;

    NumberFormat formatRupiah;

    public AdapterBarang(Context mContext, Activity activity,
                         ArrayList<String> kd_brg,
                         ArrayList<String> gambar,
                         ArrayList<String> nm_brg,
                         ArrayList<String> kat_brg,
                         ArrayList<String> harga_asli,
                         ArrayList<String> harga_jl,
                         ArrayList<String> harga_jl2,
                         ArrayList<String> harga_jl3,
                         ArrayList<String> harga_jl4,
                         ArrayList<String> qty_min2,
                         ArrayList<String> qty_min3,
                         ArrayList<String> qty_min4,
                         ArrayList<String> satuan,
                         ArrayList<String> disc,
                         ArrayList<String> harga_disc) {
        this.mContext = mContext;
        this.activity = activity;
        this.kd_brg = kd_brg;
        this.gambar = gambar;
        this.nm_brg = nm_brg;
        this.kat_brg = kat_brg;
        this.harga_jl = harga_jl;
        this.harga_jl2 = harga_jl2;
        this.harga_jl3 = harga_jl3;
        this.harga_jl4 = harga_jl4;
        this.qty_min2 = qty_min2;
        this.qty_min3 = qty_min3;
        this.qty_min4 = qty_min4;
        this.satuan = satuan;
        this.disc = disc;
        this.harga_disc = harga_disc;
        this.harga_asli = harga_asli;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.ic_not_found).error(R.drawable.ic_not_found);
        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.adapter_barang, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = viewHolder.getAdapterPosition();
                Intent it = new Intent(activity, act_detail_barang_retail.class);
                it.putExtra("kd_brg", kd_brg.get(pos));
                it.putExtra("gambar", gambar.get(pos));
                it.putExtra("nm_brg", nm_brg.get(pos));
                it.putExtra("kat_brg", kat_brg.get(pos));
                if (disc.get(pos).equals("0")) {
                    it.putExtra("harga_jl", harga_asli.get(pos));
                } else {
                    it.putExtra("harga_jl", (Double.parseDouble(harga_asli.get(pos)) - Double.parseDouble(harga_disc.get(pos)))+"");
                }
                it.putExtra("harga_jl2", harga_jl2.get(pos));
                it.putExtra("harga_jl3", harga_jl3.get(pos));
                it.putExtra("harga_jl4", harga_jl4.get(pos));
                it.putExtra("qty_min2", qty_min2.get(pos));
                it.putExtra("qty_min3", qty_min3.get(pos));
                it.putExtra("qty_min4", qty_min4.get(pos));
                it.putExtra("satuan", satuan.get(pos));
                it.putExtra("diskon", disc.get(pos));
                it.putExtra("harga_diskon", harga_disc.get(pos));
                activity.startActivity(it);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nama_brg.setText(nm_brg.get(position));
        holder.kategori_brg.setText(kat_brg.get(position));
        if (gambar.get(position) == null){
            holder.gambar.setImageResource(R.drawable.ic_highlight_off_24);
        } else {
            if (gambar.get(position).equals("")) {
                holder.gambar.setImageResource(R.drawable.ic_highlight_off_24);
            } else {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.signature(
                        new ObjectKey(String.valueOf(System.currentTimeMillis())));
                Glide.with(mContext)
                        .setDefaultRequestOptions(requestOptions)
                        .load("http://" + session.getBaseUrl() + "/storage/" + gambar.get(position) + "").into(holder.gambar);
//                    .load("http://asarasa.id/larisso/storage/" + gambar.get(position) + "").into(holder.gambar);
            }
        }
        if (disc.get(position).equals("0")) {
            holder.harga_brg.setText(harga_jl.get(position));
            holder.disc.setVisibility(View.INVISIBLE);
            holder.harga_disc.setVisibility(View.INVISIBLE);
        } else {
            holder.harga_brg.setText(formatRupiah.format((Double.parseDouble(harga_asli.get(position)) - Double.parseDouble(harga_disc.get(position)))));
            holder.disc.setVisibility(View.VISIBLE);
            holder.harga_disc.setVisibility(View.VISIBLE);
            holder.disc_value.setText("Disc "+disc.get(position)+"%");
            holder.harga_disc_value.setText(harga_jl.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return kd_brg.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_brg;
        TextView harga_brg;
        TextView kategori_brg;
        ImageView gambar;
        RelativeLayout disc, harga_disc;
        TextView disc_value, harga_disc_value;

        public MyViewHolder(View itemView) {
            super(itemView);

            nama_brg = (TextView) itemView.findViewById(R.id.nama_barang);
            harga_brg = (TextView) itemView.findViewById(R.id.harga_barang);
            kategori_brg = (TextView) itemView.findViewById(R.id.kategori_barang);
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            disc = (RelativeLayout) itemView.findViewById(R.id.disc);
            harga_disc = (RelativeLayout) itemView.findViewById(R.id.harga_disc);
            disc_value = (TextView) itemView.findViewById(R.id.disc_value);
            harga_disc_value = (TextView) itemView.findViewById(R.id.harga_disc_value);
        }
    }

}
