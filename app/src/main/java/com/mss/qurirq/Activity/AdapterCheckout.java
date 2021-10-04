package com.mss.qurirq.Activity;

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

import com.google.android.material.imageview.ShapeableImageView;
import com.mss.qurirq.R;
import com.mss.qurirq.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterCheckout extends ArrayAdapter<String> {

    private Activity context;
    private Context mContext;
    private ArrayList<String> kd_brg = new ArrayList<>();
    private ArrayList<String> nm_brg = new ArrayList<>();
    private ArrayList<String> qty = new ArrayList<>();
    private ArrayList<String> hrg_brg = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private OnEditLocationListener listener;
    NumberFormat formatRupiah;
    Session session;

    public AdapterCheckout(Activity context, ArrayList<String> kd_brg, ArrayList<String> nm_brg, ArrayList<String> qty, ArrayList<String> hrg_brg, ArrayList<String> gambar) {
        super(context, R.layout.adapter_checkout, kd_brg);

        this.context = context;
        this.mContext = context;
        this.kd_brg = kd_brg;
        this.nm_brg = nm_brg;
        this.qty = qty;
        this.hrg_brg = hrg_brg;
        this.gambar = gambar;
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
            v = layoutInflater.inflate(R.layout.adapter_checkout, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.nama_barang.setText(nm_brg.get(position));
        viewHolder.jumlah_barang.setText(qty.get(position));
        viewHolder.harga_barang.setText(formatRupiah.format(Double.parseDouble(hrg_brg.get(position))).replace(",00", ""));
        viewHolder.subtot.setText(formatRupiah.format(Double.parseDouble(hrg_brg.get(position)) * Double.parseDouble(qty.get(position))).replace(",00", ""));
        if (!TextUtils.isEmpty(gambar.get(position))) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.signature(
                    new ObjectKey(String.valueOf(System.currentTimeMillis())));
            Glide.with(getContext())
                    .setDefaultRequestOptions(requestOptions)
//                    .load("http://192.168.1.16:8000/storage/" + gambar.get(position) + "").into(viewHolder.gambar_barang);
                    .load("http://"+session.getBaseUrl()+"/storage/" + gambar.get(position) + "").into(viewHolder.gambar_barang);
        }

        return v;
    }

    class ViewHolder{
        ShapeableImageView gambar_barang;
        TextView nama_barang;
        TextView jumlah_barang;
        TextView harga_barang;
        TextView subtot;
        ViewHolder(View view){
            gambar_barang = view.findViewById(R.id.gambar_barang);
            nama_barang = view.findViewById(R.id.nama_barang);
            harga_barang = view.findViewById(R.id.harga_barang);
            jumlah_barang = view.findViewById(R.id.jumlah_barang);
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
