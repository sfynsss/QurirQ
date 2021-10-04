package com.mss.qurirq.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mss.qurirq.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterLacakPesanan extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> manifest_desc = new ArrayList<>();
    private ArrayList<String> manifest_date = new ArrayList<>();
    private ArrayList<String> manifest_time = new ArrayList<>();


    public AdapterLacakPesanan(Activity context, ArrayList<String> manifest_desc, ArrayList<String> manifest_date, ArrayList<String> manifest_time) {
        super(context, R.layout.adapter_lacak_pesanan, manifest_desc);

        this.context = context;
        this.manifest_desc = manifest_desc;
        this.manifest_date = manifest_date;
        this.manifest_time = manifest_time;

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

        System.out.println(position+"pos");
        if (position == 0) {
            viewHolder.manifest_desc.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.manifest_date.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.manifest_time.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.icon.setBackground(context.getResources().getDrawable(R.drawable.bulet_primary));
        } else {
            viewHolder.manifest_desc.setTextColor(Color.parseColor("#AAAAAA"));
            viewHolder.manifest_date.setTextColor(Color.parseColor("#AAAAAA"));
            viewHolder.manifest_time.setTextColor(Color.parseColor("#AAAAAA"));
            viewHolder.icon.setBackground(context.getResources().getDrawable(R.drawable.bulet_grey));
        }

        viewHolder.manifest_desc.setText(manifest_desc.get(position));
        viewHolder.manifest_date.setText(manifest_date.get(position));
        viewHolder.manifest_time.setText(manifest_time.get(position));

        return v;
    }

    class ViewHolder{
        ImageView icon;
        TextView manifest_desc , manifest_date, manifest_time, courier_name;
        ViewHolder(View view){
            icon = view.findViewById(R.id.icon);
            manifest_desc = view.findViewById(R.id.manifest_desc);
            manifest_date = view.findViewById(R.id.manifest_date);
            manifest_time = view.findViewById(R.id.manifest_time);
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
