package com.ander.vitocarclient.Network;

import java.util.List;

import com.ander.vitocarclient.Model.User;
import com.ander.vitocarclient.Model.Viaje;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiUser {
    @GET("api/usuarios/{dni}")
    Call<User> getUser(@Path("dni") int dni);
    @GET("api/usuarios/{dni}/viajes")
    Call<List<Viaje>> getMisViajes(@Path("dni") int dni);
    @GET("api/usuarios/mail/{mail}")
    Call<User> getUserMail(String eMail);
}
