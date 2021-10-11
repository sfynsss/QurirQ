package com.mss.qurirq.Activity;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Response.BaseResponse;
import com.mss.qurirq.Response.BaseResponse1;
import com.mss.qurirq.Session.Session;
import com.mss.qurirq.Table.GambarPromo;
import com.mss.qurirq.Table.KategoriOutlet;
import com.mss.qurirq.Table.Penawaran;
import com.mss.qurirq.Table.PoinVoucher;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    Call<BaseResponse<KategoriOutlet>> getKategori;
    Call<BaseResponse<Penawaran>> getPenawaran;
    Call<BaseResponse> getStatusUpdate;

    ArrayList<String> kd_kategori = new ArrayList<>();
    ArrayList<String> judul = new ArrayList<>();
    ArrayList<String> gambar = new ArrayList<>();
    ArrayList<String> gambar_penawaran = new ArrayList<>();

    TextView lihat_semua, ke_halaman_pencarian, nama_pengguna;
    RecyclerView kategoriBarang;
    AdapterKategoriBarang adapterKategori;
    RequestOptions requestOptions;

    SwipeRefreshLayout swipeRefreshLayout;
    Handler handler = new Handler();
    ShimmerFrameLayout shimmer;

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

        session = new Session(getActivity());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        nama_pengguna.setText(session.getUsername());
        requestOptions = new RequestOptions().centerCrop();
        kategoriBarang.setFocusable(false);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tampilKategori();
//                dataPoinVoucher();
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
                startActivity(new Intent(getContext(), act_category.class));
            }
        });

        ke_halaman_pencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), act_search_result.class));
            }
        });

        tampilKategori();
//        checkUpdate();

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
                    .load("http://" + session.getBaseUrl() + "/storage/" + gambar_penawaran.get(position) + "")
                    .into(imageView);
        }
    };

    public void checkUpdate(){
        getStatusUpdate = api.getStatusUpdate(getContext().getString(R.string.version));
        getStatusUpdate.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("update")) {
                        popUpUpdate();
                    }
                } else {
                    System.out.println("Nothing to update");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void popUpUpdate() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle("Gambar Barang");
        View v = getLayoutInflater().inflate(R.layout.popup_update, null);
        dialog.setContentView(v);
        Button update = v.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.asa.asri_larisso"));
                startActivity(intent);
            }
        });

        dialog.show();
    }

    public void tampilKategori(){
        getKategori = api.getKategoriOutlet("6");
        getKategori.enqueue(new Callback<BaseResponse<KategoriOutlet>>() {
            @Override
            public void onResponse(Call<BaseResponse<KategoriOutlet>> call, Response<BaseResponse<KategoriOutlet>> response) {
                if (response.isSuccessful()) {
                    kd_kategori.clear();
                    judul.clear();
                    gambar.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_kategori.add(response.body().getData().get(i).getId().toString());
                        judul.add(response.body().getData().get(i).getNmKategoriOutlet());
                        gambar.add(response.body().getData().get(i).getGbrKategoriOutlet());
                    }
                    if (getActivity()!=null) {
                        adapterKategori = new AdapterKategoriBarang(getContext(), getActivity(), kd_kategori, judul, gambar);
                        kategoriBarang.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        kategoriBarang.setAdapter(adapterKategori);
                    }
                } else {
                    Toasty.error(getContext(), "Error Bad Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<KategoriOutlet>> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
