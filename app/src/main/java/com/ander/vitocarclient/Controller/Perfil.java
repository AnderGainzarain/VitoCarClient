package com.ander.vitocarclient.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ander.vitocarclient.R;

import com.ander.vitocarclient.Model.ActiveUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;


public class Perfil extends Fragment {

    private final ActiveUser au = ActiveUser.getActiveUser();

    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        TextView nombre = view.findViewById(R.id.nombre);
        TextView apellido = view.findViewById(R.id.apellido);
        TextView mail = view.findViewById(R.id.mail);
        TextView telefono = view.findViewById(R.id.telefono);
        TextView coche = view.findViewById(R.id.coche);
        ImageView foto = view.findViewById(R.id.profilePicture);
        FloatingActionButton logOut = view.findViewById(R.id.btnLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActiveUser.logOut();
                getParentFragmentManager().beginTransaction().replace(R.id.flMain, new LogIn()).commit();
            }
        });
        Picasso.get().load(au.getFoto()).into(foto);
        nombre.setText(au.getNombre());
        apellido.setText(au.getApellido());
        mail.setText(au.getMail());
        telefono.setText(String.valueOf(au.getTelefono()));
        coche.setText(au.getCoche());
    }
}