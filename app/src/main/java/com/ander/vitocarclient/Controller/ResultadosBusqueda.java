package com.ander.vitocarclient.Controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ander.vitocarclient.Controller.Uils.DateManager;
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ander.vitocarclient.Controller.Adapter.ViajeAdapter;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.Vista.ToastControll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultadosBusqueda extends Fragment implements RvInterface {

    private List<Viaje> viajes;
    private ViajeAdapter adapter;
    private RecyclerView rv;
    private final ActiveUser au = ActiveUser.getActiveUser();
    private final Map<String,String> queryData = new HashMap<>();
    public ResultadosBusqueda() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("query", this, (requestKey, result) -> {
            queryData.put("origen",result.getString("origen"));
            queryData.put("destino", result.getString("destino"));
            queryData.put("fechaSalida",result.getString("fechaSalida"));
            busqueda();
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resultados_busqueda, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rvResultadosBusqueda);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
    private void busqueda(){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getViajeConcreto(queryData.get("origen"),queryData.get("destino"), DateManager.parseDate(queryData.get("fechaSalida"),DateManager.getMinutes()));
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
               if(response.isSuccessful()){
                    viajes=response.body();
                    if(viajes == null || viajes.isEmpty()){
                        Toast.makeText(getContext(), ToastControll.noHayBusqueda(), Toast.LENGTH_SHORT).show();
                    }else{
                        /*if(au!=null){
                            viajes = viajes.stream().filter(v-> !Objects.equals(v.getConductor().getDni(), au.getDNI())).collect(Collectors.toList());
                        }*/
                        adapter = new ViajeAdapter(viajes,getContext(),ResultadosBusqueda.this);
                        rv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(), ToastControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        //TODO: create the popup
        PopupWindow popupWindow = new PopupWindow(getContext());
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.mas_info, null);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(androidx.appcompat.R.style.Animation_AppCompat_Dialog);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.rgb(225,225,225)));
        Button closeButton = popupView.findViewById(R.id.close_button);
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });




    }
}