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

import com.ander.vitocarclient.Controller.Uils.DateAndTimePickers;
import com.ander.vitocarclient.Controller.Uils.FormValidation;
import com.ander.vitocarclient.R;

import java.time.LocalDateTime;

public class Buscar extends Fragment {

    private Spinner sOrigen;
    private Spinner sDestino;
    private final String[] ciudades = {"Vitoria", "Donostia", "Bilbo"};
    private EditText fecha;

    public Buscar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Get the spinners from the xml
        sOrigen = view.findViewById(R.id.sOrigenBuscar);
        sDestino = view.findViewById(R.id.sDestinoBuscar);
        // get the date from the xml
        fecha = view.findViewById(R.id.etFechaBuscar);
        Button buscar = view.findViewById(R.id.btnBuscar);
        ImageButton ibFechaSalida = view.findViewById(R.id.ibFechaBuscar);
        // set the current date and time as the default
        fecha.setText(LocalDateTime.now().toString().substring(0,10));
        // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptador);
        // Set the event listener to buscar
        buscar.setOnClickListener(view1 -> {
            // do something when the button is clicked
            String origen = sOrigen.getSelectedItem().toString();
            String destino = sDestino.getSelectedItem().toString();
            String fechaSalida = fecha.getText().toString().replace("/","-");
            if(FormValidation.validate(getContext(),origen,destino,fechaSalida).equals(false)) return;

            // store the query data
            Bundle bundle = new Bundle();
            bundle.putString("origen", origen);
            bundle.putString("destino", destino);
            bundle.putString("fechaSalida", fechaSalida);
            getParentFragmentManager().setFragmentResult("query", bundle);
            // change the fragment
            getParentFragmentManager().beginTransaction().replace(R.id.flMain, new ResultadosBusqueda()).commit();
        });
        ibFechaSalida.setOnClickListener(view2 -> DateAndTimePickers.mostrarFecha(view2,getContext(),fecha));

    }
}