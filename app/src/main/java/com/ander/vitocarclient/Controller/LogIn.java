package com.ander.vitocarclient.Controller;

import android.os.AsyncTask;
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
import com.ander.vitocarclient.Vista.ToastControll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogIn extends Fragment {

    private EditText mail;
    private EditText password;
    private Button logIn;
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
        mail = view.findViewById(R.id.etMailLogIn);
        password = view.findViewById(R.id.etPasswordLogIn);
        logIn = view.findViewById(R.id.btnLogIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(mail.getText().toString(), password.getText().toString());
            }
        });
    }
    private class LogInTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String eMail = params[0];
            String pwd = params[1];

            Call<User> call = ApiClient.getClient().create(ApiUser.class).getUserMail(eMail);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    User user = response.body();
                    if(user==null){
                        Toast.makeText(getContext(), ToastControll.mailIncorrecto(), Toast.LENGTH_LONG).show();
                        mail.setText("");
                        password.setText("");
                        return;
                    }
                    if(!user.getContraseña().equals(pwd)){
                        Toast.makeText(getContext(), ToastControll.pwdIncorrecto(), Toast.LENGTH_LONG).show();
                        mail.setText("");
                        password.setText("");
                        return;
                    }
                    ActiveUser.initialize(user);
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getContext(), ToastControll.getConectionErrorMsg(), Toast.LENGTH_LONG).show();
                }
            });

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            // Handle the result of the asynchronous task here, such as showing a Toast message or navigating to a new activity
            if (success) {
                Toast.makeText(getContext(), "Log in successful", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(R.id.flMain, new Perfil()).commit();

            } else {
                Toast.makeText(getContext(), "Log in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void logIn(String eMail, String pwd){
        Call<User> call = ApiClient.getClient().create(ApiUser.class).getUserMail(eMail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = response.body();
                if(user==null){
                    Toast.makeText(getContext(), ToastControll.mailIncorrecto(), Toast.LENGTH_LONG).show();
                    mail.setText("");
                    password.setText("");
                    return;
                }
                if(!user.getContraseña().equals(pwd)){
                    Toast.makeText(getContext(), ToastControll.pwdIncorrecto(), Toast.LENGTH_LONG).show();
                    mail.setText("");
                    password.setText("");
                    return;
                }
                ActiveUser.initialize(user);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
}