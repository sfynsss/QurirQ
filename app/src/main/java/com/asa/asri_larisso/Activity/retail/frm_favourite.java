package com.asa.asri_larisso.Activity.retail;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Cart;
import com.asa.asri_larisso.Table.Wishlist;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_favourite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_favourite extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_favourite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_favourite.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_favourite newInstance(String param1, String param2) {
        frm_favourite fragment = new frm_favourite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Api api;
    Session session;
    NumberFormat formatRupiah;
    Call<BaseResponse<Wishlist>> getDataWishlist;
    Call<BaseResponse> inputToCart;
    Call<BaseResponse> deleteWishlist;

    private ArrayList<String> kd_brg = new ArrayList<>();
    private ArrayList<String> nm_brg = new ArrayList<>();
    private ArrayList<String> hrg_brg = new ArrayList<>();
    private ArrayList<String> hrg_asli = new ArrayList<>();
    private ArrayList<String> qty = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private ArrayList<String> kategori = new ArrayList<>();
    String tmp_kd_brg = "", tmp_nm_brg = "", tmp_satuan = "", tmp_harga_jl = "", tmp_qty = "", tmp_gbr = "", tmp_kat = "";

    ListView list_fav;
    ImageView not_found;
    AdapterFavBarang adapterFavBarang;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_favourite, container, false);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(getContext());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        list_fav = view.findViewById(R.id.list_fav);
        not_found = view.findViewById(R.id.not_found);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        getData();
        not_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        return view;
    }

    public void getData() {
        getDataWishlist = api.getDataWishlist(session.getIdUser());
        getDataWishlist.enqueue(new Callback<BaseResponse<Wishlist>>() {
            @Override
            public void onResponse(Call<BaseResponse<Wishlist>> call, Response<BaseResponse<Wishlist>> response) {
                if (response.isSuccessful()) {
                    not_found.setVisibility(View.INVISIBLE);
                    list_fav.setVisibility(View.VISIBLE);
                    kd_brg.clear();
                    nm_brg.clear();
                    hrg_brg.clear();
                    hrg_asli.clear();
                    gambar.clear();
                    kategori.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        hrg_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        gambar.add(response.body().getData().get(i).getGambar());
                        kategori.add(response.body().getData().get(i).getKategoriBarang());
                    }

                    adapterFavBarang = new AdapterFavBarang(getActivity(), kd_brg, nm_brg, hrg_brg, gambar, kategori, new AdapterFavBarang.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            tambahKeCart(position);
                        }
                    }, new AdapterFavBarang.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            deleteData(kd_brg.get(position), position);
                        }
                    });

                    adapterFavBarang.notifyDataSetChanged();
                    list_fav.setAdapter(adapterFavBarang);
                } else {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Data Tidak Ditemukan !!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            list_fav.setVisibility(View.INVISIBLE);
                            not_found.setVisibility(View.VISIBLE);
                            pDialog.dismiss();
                        }
                    });
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Wishlist>> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tambahKeCart(final int position){
        tmp_kd_brg = kd_brg.get(position);
        tmp_nm_brg = nm_brg.get(position);
        tmp_satuan = "PCS";
        tmp_qty = "1";
        tmp_harga_jl = hrg_asli.get(position);
        tmp_gbr = gambar.get(position);
        tmp_kat = kategori.get(position);
        inputToCart = api.inputToCart(session.getIdUser(), tmp_kd_brg, tmp_nm_brg, tmp_satuan, tmp_harga_jl, tmp_qty, tmp_gbr, tmp_kat, session.getKdOutlet());
        inputToCart.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    Toasty.success(getContext(), "Success, data berhasil dimasukkan ke keranjang", Toast.LENGTH_SHORT).show();
                    deleteData(tmp_kd_brg, position);
                } else {
                    Toasty.error(getContext(), "Error, Input Data Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteData(String kode_barang, final int pos) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        deleteWishlist = api.deleteWishlist(session.getIdUser(), kode_barang);
        deleteWishlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    kd_brg.remove(pos);
                    nm_brg.remove(pos);
                    hrg_brg.remove(pos);
                    hrg_asli.remove(pos);
                    gambar.remove(pos);
                    kategori.remove(pos);
                    adapterFavBarang.notifyDataSetChanged();
                } else {
                    pDialog.dismiss();
                    final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dialog.setTitleText("Error");
                    dialog.setCancelable(false);
                    dialog.show();
                    adapterFavBarang.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}