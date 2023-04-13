package Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static Retrofit getClient(){
        String ip = "172.20.10.4";
        String puerto = "8080";
        retrofit = new Retrofit.Builder().baseUrl("http://" + ip + ":" + puerto)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
