package com.mss.qurirq.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Response.BaseResponse;
import com.mss.qurirq.Session.Session;
import com.mss.qurirq.Table.Penawaran;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class frm_favourite extends Fragment {

    Api api;
    Session session;
    NumberFormat formatRupiah;
    Call<BaseResponse<Penawaran>> getPenawaran;
    RequestOptions requestOptions;
    ArrayList<String> gambar_penawaran = new ArrayList<>();
    CarouselView carouselView;
    CardView dalam_kota;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_favourite, container, false);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(getContext());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        requestOptions = new RequestOptions().centerCrop();
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        dalam_kota = view.findViewById(R.id.dalam_kota);

        dalam_kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), act_qsend.class));
            }
        });

        getPenawaran = api.getPenawaranQsend();
        getPenawaran.enqueue(new Callback<BaseResponse<Penawaran>>() {
            @Override
            public void onResponse(Call<BaseResponse<Penawaran>> call, Response<BaseResponse<Penawaran>> response) {
                if (response.isSuccessful()) {
                    gambar_penawaran.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        gambar_penawaran.add(response.body().getData().get(i).getGambar());
                        System.out.println(response.body().getData().get(i).getGambar());
                    }

                    carouselView.setPageCount(response.body().getData().size());
                } else {
                    Toasty.error(getContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Penawaran>> call, Throwable t) {
                Toasty.error(getContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        carouselView.setImageListener(imageListener);

        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(getActivity())
                    .setDefaultRequestOptions(requestOptions)
                    .load("http://" + session.getBaseUrl() + "/storage/" + gambar_penawaran.get(position) + "")
                    .into(imageView);
        }
    };
}