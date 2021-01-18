package com.asa.asri_larisso.Activity.retail;

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

import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterOutlet extends ArrayAdapter<String> {

    private Activity context;
    private Context mContext;
    private ArrayList<String> kd_outlet = new ArrayList<>();
    private ArrayList<String> nama_outlet = new ArrayList<>();
    private ArrayList<String> keterangan = new ArrayList<>();
    private ArrayList<String> alamat = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private OnEditLocationListener listener;
    NumberFormat formatRupiah;
    Session session;

    public AdapterOutlet(Activity context, ArrayList<String> kd_outlet, ArrayList<String> nama_outlet, ArrayList<String> keterangan, ArrayList<String> alamat, ArrayList<String> gambar) {
        super(context, R.layout.adapter_pilih_outlet, kd_outlet);

        this.context = context;
        this.mContext = context;
        this.nama_outlet = nama_outlet;
        this.keterangan = keterangan;
        this.alamat = alamat;
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
            v = layoutInflater.inflate(R.layout.adapter_pilih_outlet, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.nama_outlet.setText(nama_outlet.get(position));
        viewHolder.keterangan.setText(keterangan.get(position));
        viewHolder.alamat.setText(alamat.get(position));
        if (!TextUtils.isEmpty(gambar.get(position))) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.signature(
                    new ObjectKey(String.valueOf(System.currentTimeMillis())));
            Glide.with(getContext())
                    .setDefaultRequestOptions(requestOptions)
//                    .load("http://192.168.1.16:8000/storage/" + gambar.get(position) + "").into(viewHolder.gambar_barang);
                    .load("http://"+session.getBaseUrl()+"/storage/" + gambar.get(position) + "").into(viewHolder.gambar);
        }

        return v;
    }

    class ViewHolder{
        ImageView gambar;
        TextView nama_outlet;
        TextView keterangan;
        TextView alamat;
        ViewHolder(View view){
            gambar = view.findViewById(R.id.gambar_outlet);
            nama_outlet = view.findViewById(R.id.nama_outlet);
            keterangan = view.findViewById(R.id.keterangan);
            alamat = view.findViewById(R.id.alamat);
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
