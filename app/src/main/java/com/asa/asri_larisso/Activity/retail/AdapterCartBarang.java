package com.asa.asri_larisso.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;
import android.text.InputFilter;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterCartBarang extends ArrayAdapter<String> {

    private Activity context;
    private Context mContext;
    Session session;
    private ArrayList<String> kd_brg = new ArrayList<>();
    private ArrayList<String> nm_brg = new ArrayList<>();
    private ArrayList<String> hrg_brg = new ArrayList<>();
    private ArrayList<String> qty = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private ArrayList<String> kat = new ArrayList<>();
    private OnEditLocationListener min;
    private OnEditLocationListener plus;
    private OnEditLocationListener delete;

    public AdapterCartBarang(Activity context, ArrayList<String> kd_brg, ArrayList<String> nm_brg, ArrayList<String> hrg_brg, ArrayList<String> qty, ArrayList<String> gambar, ArrayList<String> kat, OnEditLocationListener min, OnEditLocationListener plus, OnEditLocationListener delete) {
        super(context, R.layout.adapter_cart, kd_brg);

        this.mContext = context;
        session = new Session(mContext);
        this.context = context;
        this.kd_brg = kd_brg;
        this.nm_brg = nm_brg;
        this.hrg_brg = hrg_brg;
        this.qty = qty;
        this.gambar = gambar;
        this.kat = kat;
        this.min = min;
        this.plus = plus;
        this.delete = delete;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_cart, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.nama_barang.setText(nm_brg.get(position));
        viewHolder.harga_barang.setText(hrg_brg.get(position).replace(",00", ""));
        viewHolder.qty.setText(qty.get(position));
        viewHolder.qty.setFilters( new InputFilter[]{ new QtyMinMax( "1" , "100" )}) ;
        if (!TextUtils.isEmpty(gambar.get(position))) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.signature(
                    new ObjectKey(String.valueOf(System.currentTimeMillis())));
            Glide.with(getContext())
                    .setDefaultRequestOptions(requestOptions)
//                    .load("http://192.168.1.16:8000/storage/" + gambar.get(position) + "").into(viewHolder.gambar);
                    .load("http://"+session.getBaseUrl()+"/storage/" + gambar.get(position) + "").into(viewHolder.gambar);
        }

        viewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("min");
                if (min != null) {
                    System.out.println("di click");
                    min.onClickAdapter(position);
                }
            }
        });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("tambah");
                if (plus != null) {
                    System.out.println("di click");
                    plus.onClickAdapter(position);
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
        ImageView gambar, min, plus, delete;
        TextView nama_barang, harga_barang, qty;
        ViewHolder(View view){
            gambar = view.findViewById(R.id.gambar);
            min = view.findViewById(R.id.min);
            plus = view.findViewById(R.id.plus);
            delete = view.findViewById(R.id.delete);
            nama_barang = view.findViewById(R.id.nama_barang);
            harga_barang = view.findViewById(R.id.harga_barang);
            qty = view.findViewById(R.id.qty);
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
