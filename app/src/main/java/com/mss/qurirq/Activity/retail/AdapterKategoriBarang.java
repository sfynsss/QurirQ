package com.mss.qurirq.Activity.retail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mss.qurirq.R;
import com.mss.qurirq.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;

/**
 * Created by Sfyn on 29/06/2018.
 */


public class AdapterKategoriBarang extends RecyclerView.Adapter<AdapterKategoriBarang.MyViewHolder> {

    private Context mContext;
    private Activity activity;
    private ArrayList<String> kd_kat = new ArrayList<String>();
    private ArrayList<String> judul = new ArrayList<String>();
    private ArrayList<String> gambar = new ArrayList<String>();
    RecyclerView recyclerView;
    RequestOptions option;
    int pos;
    Intent it;

    boolean isShimmer = true;
    int shimmerNumber = 5;
    Session session;

    public AdapterKategoriBarang(Context mContext, Activity activity, ArrayList<String> kd_kat, ArrayList<String> judul, ArrayList<String> gambar) {
        this.mContext = mContext;
        this.activity = activity;
        this.kd_kat = kd_kat;
        this.judul = judul;
        this.gambar = gambar;
        option = new RequestOptions().circleCrop().placeholder(R.drawable.ic_hourglass_empty_24).error(R.drawable.ic_highlight_off_24);
        session = new Session(mContext);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.adapter_kategori, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = viewHolder.getAdapterPosition();
                Intent it = new Intent(mContext, act_browse_barang.class);
                it.putExtra("judul", judul.get(pos));
                it.putExtra("kd_kategori", kd_kat.get(pos));
                activity.startActivity(new Intent(it));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.judul.setText(judul.get(position));
        if (TextUtils.isEmpty(gambar.get(position))) {
            holder.gambar.setImageResource(R.drawable.ic_highlight_off_24);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.circleCrop().signature(
                    new ObjectKey(String.valueOf(System.currentTimeMillis())));
            Glide.with(mContext)
                    .setDefaultRequestOptions(requestOptions)
//                    .load("http://192.168.1.16:8000/storage/" + gambar.get(position) + "").into(holder.gambar);
                    .load("http://" + session.getBaseUrl() + "/storage/" + gambar.get(position) + "").into(holder.gambar);
        }
    }

    @Override
    public int getItemCount() {
        return judul.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ShimmerFrameLayout shimmerFrameLayout;
        TextView judul;
        ImageView gambar;

        public MyViewHolder(View itemView) {
            super(itemView);
            judul = (TextView) itemView.findViewById(R.id.judul);
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
        }
    }


}
