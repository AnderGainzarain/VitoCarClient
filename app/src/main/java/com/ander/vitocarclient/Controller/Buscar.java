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

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Buscar extends Fragment {

    private Spinner sOrigen;
    private Spinner sDestino;
    private final String[] ciudades = {"Vitoria", "Donostia", "Bilbo"};
    private EditText fecha;
    private Button buscar;
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
        sOrigen = view.findViewById(R.id.sOrigen);
        sDestino = view.findViewById(R.id.sDestino);
        // get the date from the xml
        fecha = view.findViewById(R.id.etFecha);
        buscar = view.findViewById(R.id.btnBuscar);
        // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptador);
        // Set tge event listener to buscar
        buscar.setOnClickListener(Search);
    }
    private View.OnClickListener Search = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            String origen = sOrigen.getSelectedItem().toString();
            String destino = sDestino.getSelectedItem().toString();
            String fechaSalida = fecha.getText().toString();
            // imput data controll
            if (fechaSalida == null){
                Toast.makeText(getContext(),"Introduzca una fecha, por favor", Toast.LENGTH_LONG).show();
                return;
            }
            if(origen==destino){
                Toast.makeText(getContext(),"El origen y el destino no pueden ser el mismo", Toast.LENGTH_LONG).show();
                return;
            }

            Map<String,String> data = new HashMap<>();
            data.put("origen",origen);
            System.out.println(data.get("origen"));
            data.put("destnino", destino);
            System.out.println(data.get("destino"));
            data.put("fechaSalida", fechaSalida);
            System.out.println(data.get("fechaSalida"));
            ((MainActivity) getActivity()).setQuery(data);

        }
    };
}