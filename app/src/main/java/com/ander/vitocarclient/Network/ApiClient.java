package com.ander.vitocarclient.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit getClient(){
        String ip = "192.168.1.11";
        String puerto = "8080";
        return new Retrofit.Builder().baseUrl("http://" + ip + ":" + puerto)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
