package com.asa.asri_larisso.Activity.retail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asa.asri_larisso.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterLacakPesanan extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> manifest_desc = new ArrayList<>();
    private ArrayList<String> manifest_date = new ArrayList<>();
    private ArrayList<String> manifest_time = new ArrayList<>();
    private ArrayList<String> courier_name = new ArrayList<>();


    public AdapterLacakPesanan(Activity context, ArrayList<String> manifest_desc, ArrayList<String> manifest_date, ArrayList<String> manifest_time, ArrayList<String> courier_name) {
        super(context, R.layout.adapter_lacak_pesanan);

        this.context = context;
        this.manifest_desc = manifest_desc;
        this.manifest_date = manifest_date;
        this.manifest_time = manifest_time;
        this.courier_name = courier_name;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_lacak_pesanan, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.manifest_desc.setText(manifest_desc.get(position));
        viewHolder.manifest_date.setText(manifest_date.get(position));
        viewHolder.manifest_time.setText(manifest_time.get(position));
        viewHolder.courier_name.setText(courier_name.get(position));


        return v;
    }

    class ViewHolder{
        TextView manifest_desc , manifest_date, manifest_time, courier_name;
        ViewHolder(View view){
            manifest_desc = view.findViewById(R.id.manifest_desc);
            manifest_date = view.findViewById(R.id.manifest_date);
            manifest_time = view.findViewById(R.id.manifest_time);
            courier_name = view.findViewById(R.id.courier_name);
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
