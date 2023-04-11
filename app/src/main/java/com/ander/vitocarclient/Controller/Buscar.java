package com.ander.vitocarclient.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ander.vitocarclient.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Buscar extends Fragment {

    private Spinner sOrigen;
    private Spinner sDestino;
    private String[] ciudades = {"Vitoria", "Donostia", "Bilbo"};

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
        // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptador);
    }
}