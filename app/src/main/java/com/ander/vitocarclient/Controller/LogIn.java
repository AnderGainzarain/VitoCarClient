package com.ander.vitocarclient.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ander.vitocarclient.Model.ActiveUser;
import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Network.ApiClient;
import com.ander.vitocarclient.Network.ApiUser;
import com.ander.vitocarclient.R;
import com.ander.vitocarclient.Vista.TextControll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogIn extends Fragment {

    private EditText mail;
    private EditText password;

    public LogIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Get the mail, password and log in button from the xml
        mail = view.findViewById(R.id.etMailLogIn);
        password = view.findViewById(R.id.etPasswordLogIn);
        Button logIn = view.findViewById(R.id.btnLogIn);
        // Set an on click listener to te log in button to log in
        logIn.setOnClickListener(view1 -> {
            if(mail.getText().toString().equals("")){
                Toast.makeText(getContext(), TextControll.mailVacio(), Toast.LENGTH_SHORT).show();
            }else if (password.getText().toString().equals("")){
                Toast.makeText(getContext(), TextControll.pwdVacia(), Toast.LENGTH_SHORT).show();
            }else{
                logIn(mail.getText().toString(), password.getText().toString());
            }
        });
    }

    private void logIn(String eMail, String pwd){
        Call<User> call = ApiClient.getClient().create(ApiUser.class).getUserMail(eMail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = response.body();
                // Notify that the mail is incorrect if it is not found by the api
                if(response.code()==404){
                    Toast.makeText(getContext(), TextControll.mailIncorrecto(), Toast.LENGTH_SHORT).show();
                    mail.setText("");
                    password.setText("");
                    return;
                }
                // If the mail is correct process it
                if(user!=null){
                    // if the password is incorrect notify it
                    if(!user.getContraseña().equals(pwd)){
                        Toast.makeText(getContext(), TextControll.pwdIncorrecto(), Toast.LENGTH_SHORT).show();
                        mail.setText("");
                        password.setText("");
                        return;
                    }
                    // If the log in data is correct log in the user to the app and show the profile section
                    ActiveUser.initialize(user);
                    MainActivity.setLogedIn(true);
                    getParentFragmentManager().beginTransaction().replace(R.id.flMain, new Perfil()).commit();
                    Toast.makeText(getContext(), TextControll.sesionIniciada(), Toast.LENGTH_SHORT).show();
                    }
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), TextControll.getConectionErrorMsg() + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}