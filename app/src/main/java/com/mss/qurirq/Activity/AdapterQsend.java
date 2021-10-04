package com.mss.qurirq.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.imageview.ShapeableImageView;
import com.mss.qurirq.R;
import com.mss.qurirq.Session.Session;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterQsend extends ArrayAdapter<String> {

    private Activity context;
    private Context mContext;
    Session session;
    private ArrayList<String> nama_penerima = new ArrayList<>();
    private ArrayList<String> detail_alamat = new ArrayList<>();
    private ArrayList<String> no_telp = new ArrayList<>();
    private OnEditLocationListener delete;

    public AdapterQsend(Activity context,
                        ArrayList<String> nama_penerima,
                        ArrayList<String> detail_alamat,
                        ArrayList<String> no_telp,
                        OnEditLocationListener delete) {
        super(context, R.layout.adapter_qsend, nama_penerima);

        this.mContext = context;
        session = new Session(mContext);
        this.context = context;
        this.nama_penerima = nama_penerima;
        this.detail_alamat = detail_alamat;
        this.no_telp = no_telp;
        this.delete = delete;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_qsend, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.nama_penerima.setText(nama_penerima.get(position));
        viewHolder.detail_alamat.setText(detail_alamat.get(position));
        viewHolder.no_telp.setText(no_telp.get(position));

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
        ImageView delete;
        TextView nama_penerima, detail_alamat, no_telp;
        ViewHolder(View view){
            delete = view.findViewById(R.id.delete);
            nama_penerima = view.findViewById(R.id.nama_penerima);
            detail_alamat = view.findViewById(R.id.detail_alamat);
            no_telp = view.findViewById(R.id.no_telp);
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
