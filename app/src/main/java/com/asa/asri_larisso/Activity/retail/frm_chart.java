package com.asa.asri_larisso.Activity.retail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Cart;
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
 * Use the {@link frm_chart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_chart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_chart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_chart.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_chart newInstance(String param1, String param2) {
        frm_chart fragment = new frm_chart();
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
    Call<BaseResponse<Cart>> getDataCart;
    Call<BaseResponse> updateCart;
    Call<BaseResponse> deleteCart;

    private ArrayList<String> kd_brg = new ArrayList<>();
    private ArrayList<String> nm_brg = new ArrayList<>();
    private ArrayList<String> hrg_brg = new ArrayList<>();
    private ArrayList<String> hrg_asli = new ArrayList<>();
    private ArrayList<String> qty = new ArrayList<>();
    private ArrayList<String> berat = new ArrayList<>();
    private ArrayList<String> volume = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private ArrayList<String> kategori = new ArrayList<>();
    private ArrayList<String> sts_point = new ArrayList<>();
    NumberFormat formatRupiah;
    double tot = 0;
    double total_berat = 0;
    double total_volume = 0;

    AdapterCartBarang adapterCartBarang;
    ListView list_cart;
    TextView total, v_total_berat, v_total_volume;
    LinearLayout linearLayout1, linearLayout2;
    Button checkout;
    ImageView not_found;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_chart, container, false);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        list_cart = view.findViewById(R.id.list_cart);
        total = view.findViewById(R.id.total);
        checkout = view.findViewById(R.id.checkout);
        not_found = view.findViewById(R.id.not_found);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        v_total_berat = view.findViewById(R.id.berat_total);
        v_total_volume = view.findViewById(R.id.volume_total);

        session = new Session(getContext());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getData();

        System.out.println("Total beratnya adalah " + total_berat);

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

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.getUserActivation() == false) {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Silahkan Registrasi Dahulu");
                    pDialog.setCancelable(false);
                    pDialog.show();
                } else {
                    if (kd_brg.size() > 0) {
                        Intent i = new Intent(getContext(), act_checkout.class);
                        i.putExtra("kd_brg", kd_brg);
                        i.putExtra("nm_brg", nm_brg);
                        i.putExtra("hrg_brg", hrg_brg);
                        i.putExtra("hrg_asli", hrg_asli);
                        i.putExtra("qty", qty);
                        i.putExtra("total_berat", v_total_berat.getText());
                        i.putExtra("total_volume", v_total_volume.getText());
                        i.putExtra("gambar", gambar);
                        i.putExtra("subtot", tot + "");
                        i.putExtra("sts_point", sts_point);
                        startActivity(i);
                    } else {
                        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Silahkan Masukkan Barang !!");
                        pDialog.setCancelable(false);
                        pDialog.show();
                    }
                }
            }
        });

        not_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        return view;
    }

    public void getData() {
        getDataCart = api.getDataCart(session.getIdUser(), session.getKdOutlet());
        getDataCart.enqueue(new Callback<BaseResponse<Cart>>() {
            @Override
            public void onResponse(Call<BaseResponse<Cart>> call, Response<BaseResponse<Cart>> response) {
                if (response.isSuccessful()) {
                    not_found.setVisibility(View.INVISIBLE);
                    list_cart.setVisibility(View.VISIBLE);
                    kd_brg.clear();
                    nm_brg.clear();
                    hrg_brg.clear();
                    hrg_asli.clear();
                    qty.clear();
                    berat.clear();
                    volume.clear();
                    gambar.clear();
                    kategori.clear();
                    sts_point.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        hrg_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        qty.add(response.body().getData().get(i).getQty().toString());
                        berat.add(response.body().getData().get(i).getBerat().toString());
                        volume.add(response.body().getData().get(i).getVolume().toString());
                        gambar.add(response.body().getData().get(i).getGambar());
                        kategori.add(response.body().getData().get(i).getKategoriBarang());
                        sts_point.add(response.body().getData().get(i).getSts_point());
                    }
                    System.out.println("sts_point : "+sts_point);
                    if (getActivity()!=null) {
                        adapterCartBarang = new AdapterCartBarang(getActivity(), kd_brg, nm_brg, hrg_brg, qty, berat, volume, gambar, kategori, new AdapterCartBarang.OnEditLocationListener() {
                            @Override
                            public void onClickAdapter(final int position) {
                                int kurang = Integer.parseInt(qty.get(position)) - 1;
                                if (kurang > 0) {
                                    qty.set(position, kurang + "");
                                    adapterCartBarang.notifyDataSetChanged();
                                    sumTot();
                                    updateQty(kd_brg.get(position), kurang + "");
                                } else {
                                    final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Apakah Anda akan menghapus barang ini ?");
                                    pDialog.setCancelable(false);
                                    pDialog.setConfirmText("YA");
                                    pDialog.setCancelText("TIDAK");
                                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            deleteBarang(kd_brg.get(position), position);
                                            pDialog.dismiss();
                                        }
                                    });
                                    pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            pDialog.dismiss();
                                        }
                                    });
                                    pDialog.show();
                                    adapterCartBarang.notifyDataSetChanged();
                                    sumTot();
                                }
                            }
                        }, new AdapterCartBarang.OnEditLocationListener() {
                            @Override
                            public void onClickAdapter(int position) {
                                int tambah = Integer.parseInt(qty.get(position)) + 1;
                                qty.set(position, tambah + "");
                                adapterCartBarang.notifyDataSetChanged();
                                sumTot();
                                updateQty(kd_brg.get(position), tambah + "");
                            }
                        }, new AdapterCartBarang.OnEditLocationListener() {
                            @Override
                            public void onClickAdapter(final int position) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Apakah Anda akan menghapus barang ini ?");
                                pDialog.setCancelable(false);
                                pDialog.setConfirmText("YA");
                                pDialog.setCancelText("TIDAK");
                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        deleteBarang(kd_brg.get(position), position);
                                        pDialog.dismiss();
                                    }
                                });
                                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        pDialog.dismiss();
                                    }
                                });
                                pDialog.show();
                                adapterCartBarang.notifyDataSetChanged();
                                sumTot();
                            }
                        });

//                    linearLayout1.setVisibility(View.VISIBLE);
//                    linearLayout2.setVisibility(View.VISIBLE);
                        adapterCartBarang.notifyDataSetChanged();
                        list_cart.setAdapter(adapterCartBarang);
                        sumTot();
                    }
                } else {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Keranjang Anda Kosong !!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            list_cart.setVisibility(View.INVISIBLE);
                            not_found.setVisibility(View.VISIBLE);
                            pDialog.dismiss();
                        }
                    });
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Cart>> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateQty(String kode_barang, String quantity) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        updateCart = api.updateCart(session.getIdUser(), kode_barang, quantity);
        updateCart.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    adapterCartBarang.notifyDataSetChanged();
                } else {
                    pDialog.dismiss();
                    final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dialog.setTitleText("Error");
                    dialog.setCancelable(false);
                    dialog.show();
                    adapterCartBarang.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteBarang(String kode_barang, final int pos) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        deleteCart = api.deleteCart(session.getIdUser(), kode_barang);
        deleteCart.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                    dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dialog.setTitleText("Barang berhasil dihapus !!!");
                    dialog.setCancelable(false);
                    dialog.show();
                    kd_brg.remove(pos);
                    nm_brg.remove(pos);
                    hrg_brg.remove(pos);
                    hrg_asli.remove(pos);
                    qty.remove(pos);
                    berat.remove(pos);
                    volume.remove(pos);
                    gambar.remove(pos);
                    kategori.remove(pos);
                    adapterCartBarang.notifyDataSetChanged();
                    sumTot();
                } else {
                    pDialog.dismiss();
                    final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dialog.setTitleText("Error");
                    dialog.setCancelable(false);
                    dialog.show();
                    adapterCartBarang.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sumTot() {
        tot = 0;
        total_berat = 0;
        total_volume = 0;

        for (int i = 0; i < qty.size(); i++) {
            tot += Double.parseDouble(qty.get(i)) * Double.parseDouble(hrg_asli.get(i));
            total_berat += Double.parseDouble(qty.get(i)) * Double.parseDouble(berat.get(i));
            total_volume += Double.parseDouble(qty.get(i)) * Double.parseDouble(volume.get(i));
        }

        total.setText(formatRupiah.format(tot).replace(",00", ""));
        v_total_berat.setText(total_berat+"");
        v_total_volume.setText(total_volume+"");
    }
}