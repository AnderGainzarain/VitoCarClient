package com.ander.vitocarclient.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ander.vitocarclient.Controller.Uils.DateAndTimePickers;
import com.ander.vitocarclient.Controller.Uils.FormValidation;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.R;


import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.Viaje;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiViaje;
import com.ander.vitocarclient.Vista.TextControll;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicarViaje extends Fragment {

    private Spinner sOrigen;
    private Spinner sDestino;
    private final String[] ciudades = {"Vitoria", "Donostia", "Bilbo"};
    private EditText precio;
    private EditText fecha;
    private EditText hora;
    private final ActiveUser au = ActiveUser.getActiveUser();

    public PublicarViaje() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publicar_viaje, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Get the spinners from the xml
        sOrigen = view.findViewById(R.id.sOrigenPublicar);
        sDestino = view.findViewById(R.id.sDestinoPublicar);
        // get the date and precio from the xml
        fecha = view.findViewById(R.id.etFechaSalidaPublicar);
        hora = view.findViewById(R.id.etHoraSalidaPublicar);
        precio = view.findViewById(R.id.etPrecioPublicar);
        // Get the fecha and hora salida image buttons
        ImageButton ibFechaSalida = view.findViewById(R.id.ibCalendarPublicar);
        ImageButton ibHoraSalida = view.findViewById(R.id.ibTimePublicar);
        // set the current date and time as the default
        fecha.setText(LocalDateTime.now().toString().substring(0,10));
        hora.setText(LocalDateTime.now().toString().substring(11,19));
       // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptador);
        // set a listener to show the date and time pickers
        ibFechaSalida.setOnClickListener(view2 -> DateAndTimePickers.mostrarFecha(getContext(),fecha));
        ibHoraSalida.setOnClickListener(view3 -> DateAndTimePickers.mostrarHora(getContext(),hora));
        // Set the event listener to publicar
        Button publicar = view.findViewById(R.id.btnPublicar);
        publicar.setOnClickListener(view1 -> {
            // do something when the button is clicked
            String origen = sOrigen.getSelectedItem().toString();
            String destino = sDestino.getSelectedItem().toString();
            String fechaSalida = fecha.getText().toString();
            String horaSalida = hora.getText().toString();
            // Check if the precio is introduced
            if(precio.getText().toString().equals("")){
                // notify if there is no precio introduced
                Toast.makeText(getContext(), TextControll.precioVacio(), Toast.LENGTH_SHORT).show();
            }else{
                // validate the viaje data
                String coste = precio.getText().toString();
                if(FormValidation.validate(getContext(),origen,destino,fechaSalida,horaSalida,coste).equals(false)) return;

                esViajeValido(origen,destino,fechaSalida,horaSalida,Integer.parseInt(coste));
            }
        });

    }
    private void publicarViajes(String origen, String destino, String fechaSalida, String horaS, int coste) {
        // Create a viaje with the viaje data
        Viaje viaje = new Viaje(coste, origen, destino, fechaSalida + " " + horaS);
        Call<Viaje> call = ApiClient.getClient().create(ApiViaje.class).publicarViaje(au.getDNI(), viaje);
        call.enqueue(new Callback<Viaje>() {
            @Override
            public void onResponse(@NonNull Call<Viaje> call, @NonNull Response<Viaje> response) {
                if(response.isSuccessful()){
                    // if the request is succesfull empty the form and notify the viaje has been published
                    Viaje viaje = response.body();
                    if (viaje!=null){
                        precio.setText("");
                        fecha.setText(LocalDateTime.now().toString().substring(0,10));
                        hora.setText(LocalDateTime.now().toString().substring(11,19));
                        Toast.makeText(getContext(), TextControll.viajePublicado(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Viaje> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), TextControll.errorPublicar() + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void esViajeValido(String origen, String destino, String fecha, String horaS,int coste){
        // if the user has a car check if the viaje can be published
        if(au.getCoche()!=null){
            Call<List<Viaje>> call = ApiClient.getClient().create(ApiUser.class).getMisViajes(au.getDNI());
            call.enqueue(new Callback<List<Viaje>>() {
                @Override
                public void onResponse(@NonNull Call<List<Viaje>> call, @NonNull Response<List<Viaje>> response) {
                    if(response.isSuccessful()){
                        List<Viaje>viajes=response.body();
                        // if the user has not published any viaje publish it
                        if(viajes==null||viajes.isEmpty()){
                            publicarViajes(origen,destino,fecha,horaS,coste);
                        }else {
                            // if the user has published some viajes check if there is another with the same data
                            if(viajes.stream().noneMatch(v ->
                                    Objects.equals(v.getOrigen(),origen) &&
                                            Objects.equals(v.getDestino(),destino) &&
                                            Objects.equals(v.getFechaSalida(),fecha + " " + horaS))){
                                publicarViajes(origen,destino,fecha,horaS,coste);
                            }else{
                                // if there is a viaje with that data notify it
                                Toast.makeText(getContext(), TextControll.viajeYaPublicado(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Viaje>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
