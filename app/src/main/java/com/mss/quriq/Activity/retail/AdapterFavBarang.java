package com.mss.quriq.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mss.quriq.R;
import com.mss.quriq.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterFavBarang extends ArrayAdapter<String> {

    private Activity context;
    private Context mContext;
    private ArrayList<String> kd_brg = new ArrayList<>();
    private ArrayList<String> nm_brg = new ArrayList<>();
    private ArrayList<String> hrg_brg = new ArrayList<>();
    private ArrayList<String> qty = new ArrayList<>();
    private ArrayList<String> berat = new ArrayList<>();
    private ArrayList<String> volume = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private ArrayList<String> kat = new ArrayList<>();
    private OnEditLocationListener cart;
    private OnEditLocationListener delete;
    Session session;

    public AdapterFavBarang(Activity context,
                            ArrayList<String> kd_brg,
                            ArrayList<String> nm_brg,
                            ArrayList<String> hrg_brg,
                            ArrayList<String> berat,
                            ArrayList<String> volume,
                            ArrayList<String> gambar,
                            ArrayList<String> kat,
                            OnEditLocationListener cart, OnEditLocationListener delete) {
        super(context, R.layout.adapter_fav, kd_brg);

        this.context = context;
        this.mContext = context;
        this.kd_brg = kd_brg;
        this.nm_brg = nm_brg;
        this.hrg_brg = hrg_brg;
        this.berat = berat;
        this.volume = volume;
        this.gambar = gambar;
        this.kat = kat;
        this.cart = cart;
        this.delete = delete;
        session = new Session(mContext);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_fav, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.nama_barang.setText(nm_brg.get(position));
        viewHolder.harga_barang.setText(hrg_brg.get(position));
        viewHolder.v_berat.setText(berat.get(position));
        viewHolder.v_volume.setText(volume.get(position));
        if (!TextUtils.isEmpty(gambar.get(position))) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.signature(
                    new ObjectKey(String.valueOf(System.currentTimeMillis())));
            Glide.with(getContext())
                    .setDefaultRequestOptions(requestOptions)
//                    .load("http://192.168.1.16:8000/storage/" + gambar.get(position) + "").into(viewHolder.gambar);
                    .load("http://"+session.getBaseUrl()+"/storage/" + gambar.get(position) + "").into(viewHolder.gambar);
        }

        viewHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("tambah");
                if (cart != null) {
                    System.out.println("di click");
                    cart.onClickAdapter(position);
                }
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete != null) {
                    System.out.println("Hapus");
                    delete.onClickAdapter(position);
                }
            }
        });

        return v;
    }

    class ViewHolder{
        ImageView gambar, cart, delete;
        TextView nama_barang, harga_barang, v_berat, v_volume;
        ViewHolder(View view){
            gambar = view.findViewById(R.id.gambar);
            cart = view.findViewById(R.id.btn_cart);
            delete = view.findViewById(R.id.btn_delete);
            nama_barang = view.findViewById(R.id.nama_barang);
            harga_barang = view.findViewById(R.id.harga_barang);
            v_berat = view.findViewById(R.id.v_berat);
            v_volume = view.findViewById(R.id.v_volume);
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
