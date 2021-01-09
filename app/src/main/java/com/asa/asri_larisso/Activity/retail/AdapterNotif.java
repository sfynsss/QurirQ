package com.asa.asri_larisso.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asa.asri_larisso.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterNotif extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> id_notif = new ArrayList<>();
    private ArrayList<String> judul = new ArrayList<>();
    private ArrayList<String> isi = new ArrayList<>();
    private ArrayList<String> jenis = new ArrayList<>();


    public AdapterNotif(Activity context, ArrayList<String> id_notif, ArrayList<String> judul, ArrayList<String> isi, ArrayList<String> jenis) {
        super(context, R.layout.adapter_notif, id_notif);

        this.context = context;
        this.id_notif = id_notif;
        this.judul = judul;
        this.isi = isi;
        this.jenis = jenis;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_notif, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        System.out.println(jenis.get(position));
        if (jenis.get(position).equals("0")) {
            viewHolder.warna_samping.setBackgroundColor(v.getResources().getColor(R.color.green_notif));
            viewHolder.icon_notif.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_notif_green));
        } else if (jenis.get(position).equals("1")) {
            viewHolder.warna_samping.setBackgroundColor(v.getResources().getColor(R.color.blue_notif));
            viewHolder.icon_notif.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_notif_blue));
        } else if (jenis.get(position).equals("2")) {
            viewHolder.warna_samping.setBackgroundColor(v.getResources().getColor(R.color.yellow_notif));
            viewHolder.icon_notif.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_notif_yellow));
        }
        viewHolder.judul_notif.setText(judul.get(position));
        viewHolder.isi_notif.setText(isi.get(position));

        return v;
    }

    class ViewHolder{
        TextView judul_notif;
        TextView isi_notif;
        ImageView warna_samping;
        ImageView icon_notif;
        ViewHolder(View view){
            judul_notif = (TextView) view.findViewById(R.id.judul_notif);
            isi_notif = (TextView) view.findViewById(R.id.isi_notif);
            warna_samping = view.findViewById(R.id.warna_samping);
            icon_notif = view.findViewById(R.id.icon_notif);
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
