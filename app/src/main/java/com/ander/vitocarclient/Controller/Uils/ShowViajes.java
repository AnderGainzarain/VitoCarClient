package com.ander.vitocarclient.Controller.Uils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ander.vitocarclient.Controller.Adapter.ViajeAdapter;
import com.ander.vitocarclient.Controller.RvInterface;
import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.Vista.TextControll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowViajes {
    private static List<Viaje> viajes = new ArrayList<>();
    private static ActiveUser au = ActiveUser.getActiveUser();
    public static List<Viaje> showSearchResults(Context context, Map<String,String> queryData, RecyclerView rv, RvInterface rvInterface){
        search(context,queryData,rv,rvInterface);
        //hideMisReservas(context);
        //hideMisViajes(context);
        //hideFullViajes(context);
        //show(rv, rvInterface, context);
        return viajes;
    }
    private static void search(Context context, Map<String, String> queryData, RecyclerView rv, RvInterface rvInterface){
        {
            // get all the viajes that match the query data
            Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getViajeConcreto(queryData.get("origen"),queryData.get("destino"), LocalDateTime.parse(queryData.get("fechaSalida") + "T" + queryData.get("horaSalida")));
            call.enqueue(new Callback<List<Viaje>>() {
                @Override
                public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                    if(response.isSuccessful()){
                        List<Viaje> busqueda=response.body();
                        // notify the user if there are not viajes that match the query data
                        if(busqueda == null || busqueda.isEmpty()){
                            System.out.println("vacio");
                            Toast.makeText(context, TextControll.noHayBusqueda(), Toast.LENGTH_SHORT).show();
                        }else{
                            System.out.println("Initial size: " + viajes.size());
                            viajes.addAll(busqueda);
                            System.out.println("Final size: " + viajes.size());
                        }
                        hideMisViajes(context, rv, rvInterface);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                    // return an error message if there is an error
                    Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        }

    private static void hideMisReservas(Context context, RecyclerView rv, RvInterface rvInterface){
        {
            System.out.println("Mis reservas");

            // if the user is logged in get their reservas
            if (au != null) {
                Call<List<Viaje>> call = ApiClient.getClient().create(ApiViaje.class).getMisReservas(au.getDNI());
                call.enqueue(new Callback<List<Viaje>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                        if (response.isSuccessful()) {
                            List<Viaje> reservas = response.body();
                            // delete the reservas from the results
                            if (reservas != null) {
                                viajes.removeAll(reservas);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                        Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private static void hideMisViajes(Context context, RecyclerView rv, RvInterface rvInterface){
            System.out.println("misViajes");
            // if the user is logged in get the viajes they are offering
            if(au!=null){
                Call<List<Viaje>> call = ApiClient.getClient().create(ApiUser.class).getMisViajes(au.getDNI());
                call.enqueue(new Callback<List<Viaje>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                        if(response.isSuccessful()){
                            List<Viaje>misViajes=response.body();
                            // remove the published viajes from the result
                            if(misViajes!=null){
                                System.out.println("Inicial: " + viajes.size());
                                viajes.removeAll(misViajes);
                                System.out.println("final: " + viajes.size());
                            }
                            show(rv, rvInterface, context);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                        Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }
    private static void hideFullViajes(Context context){
            System.out.println("llenos");

            for(int i = 0; i<viajes.size();i++){
                Call<List<User>> call = ApiClient.getClient().create(ApiUser.class).getPasajeros(viajes.get(i).getIdViaje());
                int finalI = i;
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        List<User>pasajeros = response.body();
                        if(pasajeros == null || pasajeros.isEmpty()){
                            return;
                        }else{
                            // delete the full viajes from the result
                            if(pasajeros.size()==3){
                                viajes.remove(finalI);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(context, TextControll.getConectionErrorMsg() + t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }
    private static void show(RecyclerView rv, RvInterface rvInterface, Context context){
        // sort the viajes
        System.out.println("show: " + viajes.size());
        if(viajes== null || viajes.isEmpty()){
            Toast.makeText(context, TextControll.noHayBusqueda(),Toast.LENGTH_SHORT).show();

        }else{
            viajes=viajes.parallelStream().sorted(Comparator.comparing(Viaje::getFechaSalida)
                    .thenComparing(Viaje::getOrigen)
                    .thenComparing(Viaje::getDestino)
                    .thenComparing(Viaje::getPrecio)).collect(Collectors.toList());
            // show the viajes
            rv.setAdapter(new ViajeAdapter(viajes,rvInterface));
        }
    }
}
