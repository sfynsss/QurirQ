package com.asa.asri_larisso.Activity.retail;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Penawaran;
import com.asa.asri_larisso.Table.kategori;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class frm_home extends Fragment {

    CarouselView carouselView;
    Api api;
    Session session;
    Call<BaseResponse<kategori>> getKategori;
    Call<BaseResponse<Penawaran>> getPenawaran;

    ArrayList<String> kd_kategori = new ArrayList<>();
    ArrayList<String> judul = new ArrayList<>();
    ArrayList<String> gambar = new ArrayList<>();
    ArrayList<String> gambar_penawaran = new ArrayList<>();

    TextView lihat_semua, ke_halaman_pencarian, nama_pengguna;
    RecyclerView kategoriBarang;
    AdapterKategoriBarang adapterKategori;
    RequestOptions requestOptions;
    Spinner pilih_outlet;

    SwipeRefreshLayout swipeRefreshLayout;
    Handler handler = new Handler();
    ShimmerFrameLayout shimmer;

    RelativeLayout btn_voucher, btn_point;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_home, container, false);

        nama_pengguna = view.findViewById(R.id.nama_pengguna);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        lihat_semua = view.findViewById(R.id.lihat_semua);
        kategoriBarang = view.findViewById(R.id.kategori_barang);
        ke_halaman_pencarian = view.findViewById(R.id.ke_halaman_pencarian);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        shimmer = view.findViewById(R.id.shimmer);
        btn_voucher = view.findViewById(R.id.btn_voucher);
        btn_point = view.findViewById(R.id.btn_point);


        session = new Session(getActivity());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        nama_pengguna.setText(session.getUsername());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tampilKategori();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmer.stopShimmer();
                shimmer.hideShimmer();
                shimmer.setVisibility(View.GONE);
                kategoriBarang.setVisibility(View.VISIBLE);
            }
        },1850);

        lihat_semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_category_retail.class));
            }
        });

        ke_halaman_pencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), act_search_result.class));
            }
        });

        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_point_retail.class));
            }
        });

        btn_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_voucher.class));
            }
        });

        tampilKategori();
        requestOptions = new RequestOptions().centerCrop();
        getPenawaran = api.getPenawaran();
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
                    .load("http://larisso.co.id/storage/" + gambar_penawaran.get(position) + "")
                    .into(imageView);
        }
    };

    public void tampilKategori(){
        getKategori = api.getKategoriBarang("6");
        getKategori.enqueue(new Callback<BaseResponse<kategori>>() {
            @Override
            public void onResponse(Call<BaseResponse<kategori>> call, Response<BaseResponse<kategori>> response) {
                if (response.isSuccessful()) {
                    kd_kategori.clear();
                    judul.clear();
                    gambar.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_kategori.add(response.body().getData().get(i).getKdKatAndroid());
                        judul.add(response.body().getData().get(i).getNmKatAndroid());
                        gambar.add(response.body().getData().get(i).getGbrKatAndroid());
                    }

                    adapterKategori = new AdapterKategoriBarang(getContext(), getActivity(), kd_kategori, judul, gambar);
                    kategoriBarang.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    kategoriBarang.setAdapter(adapterKategori);
                } else {
                    Toasty.error(getContext(), "Error Bad Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<kategori>> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
