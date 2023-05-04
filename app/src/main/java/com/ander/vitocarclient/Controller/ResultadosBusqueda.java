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
import android.widget.TextView;
import android.widget.Toast;

import com.ander.vitocarclient.Controller.Uils.DateManager;
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ander.vitocarclient.Controller.Adapter.ViajeAdapter;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.Vista.TextControll;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultadosBusqueda extends Fragment implements RvInterface {

    private List<Viaje> viajes;
    private List<Viaje> misViajes;
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
            queryData.put("horaSalida",result.getString("horaSalida"));
            misViajes();
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
    private void misViajes(){
        if(au!=null){
            Call<List<Viaje>> call = ApiClient.getClient().create(ApiUser.class).getMisViajes(au.getDNI());
            call.enqueue(new Callback<List<Viaje>>() {
                @Override
                public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                    if(response.isSuccessful()){
                        List<Viaje>aux=response.body();
                        if(aux!=null){
                            misViajes = aux;
                        }
                        busqueda();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else busqueda();

    }
    private void busqueda(){
        Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getViajeConcreto(queryData.get("origen"),queryData.get("destino"), DateManager.parseDate(queryData.get("fechaSalida"),queryData.get("horaSalida")));
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                // Show the viajes in the recycler view
               if(response.isSuccessful()){
                    viajes=response.body();
                    if(viajes == null || viajes.isEmpty()){
                        Toast.makeText(getContext(), TextControll.noHayBusqueda(), Toast.LENGTH_SHORT).show();
                    }else{
                        if(au!=null){
                            for(int i = 0; i <viajes.size();i++){
                                for(int j = 0; j<misViajes.size();j++){
                                    if(viajes.get(i).getIdViaje()==misViajes.get(j).getIdViaje()){
                                        viajes.remove(i);
                                    }
                                }
                            }
                        }
                        adapter = new ViajeAdapter(viajes,getContext(),ResultadosBusqueda.this);
                        rv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                // return an error message if there is an error
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void reservar(int idViaje){
        Call<Viaje> call = ApiClient.getClient().create(ApiViaje.class).reservar(au.getDNI(),idViaje);
        call.enqueue(new Callback<Viaje>() {
            @Override
            public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), TextControll.reservaRealizada(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Viaje> call, Throwable t) {
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onItemClick(int position) {
        if(au!=null){
            Viaje viaje = viajes.get(position);
            PopupWindow popupWindow = new PopupWindow(getContext());
            View popupView = LayoutInflater.from(getContext()).inflate(R.layout.mas_info, null);
            popupWindow.setContentView(popupView);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(androidx.appcompat.R.style.Animation_AppCompat_Dialog);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.rgb(225,225,225)));
            Button closeButton = popupView.findViewById(R.id.close_button);
            TextView origen = popupView.findViewById(R.id.origenM);
            TextView destino = popupView.findViewById(R.id.destinoM);
            TextView fecha = popupView.findViewById(R.id.fechaSalidaM);
            TextView precio = popupView.findViewById(R.id.precioM);
            TextView conductor = popupView.findViewById(R.id.conductorM);
            TextView contacto = popupView.findViewById(R.id.contactoM);

            origen.setText(viaje.getOrigen());
            destino.setText(viaje.getDestino());
            fecha.setText(viaje.getFechaSalida());
            precio.setText(String.valueOf(viaje.getPrecio()));
            conductor.setText("a");
            contacto.setText("a@a");
            popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
            closeButton.setOnClickListener(v -> popupWindow.dismiss());
            Button reserva = popupView.findViewById(R.id.btnreservarM);
            reserva.setOnClickListener(view -> reservar(viajes.get(position).getIdViaje()));
        }
    }
}