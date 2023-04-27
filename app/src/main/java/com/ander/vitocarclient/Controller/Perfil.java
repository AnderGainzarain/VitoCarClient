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
import android.widget.Toast;

import com.ander.vitocarclient.R;

import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.Vista.ToastControll;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Perfil extends Fragment {

    private User user;
    private final ActiveUser au = ActiveUser.getActiveUser();
    private TextView nombre;
    private TextView apellido;
    private TextView mail;
    private TextView telefono;
    private TextView coche;
    private ImageView foto;
    private FloatingActionButton logOut;
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
        nombre = view.findViewById(R.id.nombre);
        apellido = view.findViewById(R.id.apellido);
        mail = view.findViewById(R.id.mail);
        telefono = view.findViewById(R.id.telefono);
        coche = view.findViewById(R.id.coche);
        foto = view.findViewById(R.id.profilePicture);
        logOut = view.findViewById(R.id.btnLogOut);
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
        telefono.setText(au.getTelefono());
        coche.setText(au.getCoche());
    }
}