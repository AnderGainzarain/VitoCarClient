package Network;

import java.util.List;

import Model.Viaje;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiViaje {
    @GET("api/viajes/{dni}/pasajero")
    Call<List<Viaje>> getMisReservas(@Path("dni") int dni);
    @GET("api/viajes/viajeConcreto")
    Call<List<Viaje>> getViajeConcreto(@Query("origen") String origen,
                                       @Query("destino") String destino,
                                       @Query("fecha") String fechaSalida);
    @POST("api/publicar/{dni}")
    void publicarViaje(@Path("dni") int dni, @Body Viaje viaje);
}
