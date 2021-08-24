package com.mss.quriq.Activity.retail;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mss.quriq.Api.Api;
import com.mss.quriq.Api.RetrofitClient;
import com.mss.quriq.R;
import com.mss.quriq.Response.BaseResponse;
import com.mss.quriq.Session.Session;
import com.mss.quriq.Table.Notif;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_notification extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_notification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_notification.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_notification newInstance(String param1, String param2) {
        frm_notification fragment = new frm_notification();
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

    ListView list_notif;
    ImageView not_found;
    Session session;
    Api api;
    Call<BaseResponse<Notif>> getNotif;
    AdapterNotif adapterNotif;

    private ArrayList<String> id_notif = new ArrayList<>();
    private ArrayList<String> judul = new ArrayList<>();
    private ArrayList<String> isi = new ArrayList<>();
    private ArrayList<String> jenis = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_notification, container, false);

        list_notif = view.findViewById(R.id.list_notif);
        not_found = view.findViewById(R.id.not_found);

        session = new Session(getContext());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        getNotif = api.getNotif(session.getIdUser());
        getNotif.enqueue(new Callback<BaseResponse<Notif>>() {
            @Override
            public void onResponse(Call<BaseResponse<Notif>> call, Response<BaseResponse<Notif>> response) {
                if (response.isSuccessful()) {
                    not_found.setVisibility(View.INVISIBLE);
                    list_notif.setVisibility(View.VISIBLE);

                    id_notif.clear();
                    judul.clear();
                    isi.clear();
                    jenis.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        id_notif.add(response.body().getData().get(i).getIdNotif()+"");
                        judul.add(response.body().getData().get(i).getJudul());
                        isi.add(response.body().getData().get(i).getNotif());
                        jenis.add(response.body().getData().get(i).getJenisNotif()+"");
                    }
                    if (getActivity()!=null) {
                        adapterNotif = new AdapterNotif(getActivity(), id_notif, judul, isi, jenis);
                        adapterNotif.notifyDataSetChanged();
                        list_notif.setAdapter(adapterNotif);
                    }
                } else {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Data Tidak Ditemukan !!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            list_notif.setVisibility(View.INVISIBLE);
                            not_found.setVisibility(View.VISIBLE);
                            pDialog.dismiss();
                        }
                    });
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Notif>> call, Throwable t) {
                Toasty.error(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}