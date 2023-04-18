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
import android.widget.TextView;
import android.widget.Toast;

import com.ander.vitocarclient.Controller.Uils.FormValidation;
import com.ander.vitocarclient.R;

import Model.ActiveUser;
import Model.Viaje;
import Network.ApiClient;
import Network.ApiViaje;
import Vista.ToastControll;

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
        sOrigen = view.findViewById(R.id.sOrigenPublicar);
        sDestino = view.findViewById(R.id.sDestinoPublicar);
        // get the date and precio from the xml
        fecha = view.findViewById(R.id.etFechaSalidaPublicar);
        precio = view.findViewById(R.id.etPrecioPublicar);
       // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptador);
        // Set the event listener to publicar
        publicar = view.findViewById(R.id.btnPublicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do something when the button is clicked
                String origen = sOrigen.getSelectedItem().toString();
                String destino = sDestino.getSelectedItem().toString();
                String fechaSalida = fecha.getText().toString();
                String coste = precio.getText().toString();
                if(FormValidation.validate(getContext(),origen,destino,fechaSalida,coste).equals(false)) return;

                publicarViaje(origen,destino,fechaSalida,Integer.parseInt(coste));
            }
        });
    }
    public void publicarViaje(String origen, String destino, String fechaSalida, int coste) {
        Viaje viaje = new Viaje(coste, origen, destino, fechaSalida);
        ApiClient.getClient().create(ApiViaje.class).publicarViaje(au.getDNI(), viaje);
    }
}