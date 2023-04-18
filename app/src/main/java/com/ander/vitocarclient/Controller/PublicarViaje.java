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
import android.widget.Spinner;
import android.widget.Toast;

import com.ander.vitocarclient.R;

import java.util.List;

import Adapter.ViajeAdapter;
import Model.ActiveUser;
import Model.Viaje;
import Network.ApiClient;
import Network.ApiUser;
import Network.ApiViaje;
import Vista.ToastControll;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicarViaje extends Fragment {

    private Spinner sOrigen;
    private Spinner sDestino;
    private final String[] ciudades = {"Vitoria", "Donostia", "Bilbo"};
    private EditText precio;
    private EditText fecha;
    private Button publicar;
    private ActiveUser au = ActiveUser.getActiveUser();

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
        sOrigen = view.findViewById(R.id.sOrigen);
        sDestino = view.findViewById(R.id.sDestino);
        // get the date from the xml
        fecha = view.findViewById(R.id.etFecha);
        precio = view.findViewById(R.id.tvPrecio);
        publicar = view.findViewById(R.id.btnPublicar);
        // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        ArrayAdapter<String> adaptadord = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptadord);
        // Set the event listener to buscar
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do something when the button is clicked
                String origen = sOrigen.getSelectedItem().toString();
                String destino = sDestino.getSelectedItem().toString();
                String fechaSalida = fecha.getText().toString();
                int coste = Integer.parseInt(precio.getText().toString());
                // imput data controll
                if (fechaSalida.isEmpty()){
                    Toast.makeText(getContext(), ToastControll.fechaVacia(), Toast.LENGTH_LONG).show();
                    return;
                }
                if(origen.equals(destino)){
                    Toast.makeText(getContext(),ToastControll.origenDestinoIguales(), Toast.LENGTH_LONG).show();
                    return;
                }
                // send an error if the date is a date earlier than today
                // Send error if there is no precio
                if(coste < 1){
                    Toast.makeText(getContext(),ToastControll.precioMenorUno(), Toast.LENGTH_LONG).show();
                }
                publicarViaje(origen,destino,fechaSalida,coste);
            }
        });
    }
    public void publicarViaje(String origen, String destino, String fechaSalida, int coste) {
        Viaje viaje = new Viaje(coste, origen, destino, fechaSalida);
        ApiClient.getClient().create(ApiViaje.class).publicarViaje(au.getDNI(), viaje);
    }
}