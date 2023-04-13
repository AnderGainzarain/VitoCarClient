package Network;

import java.util.List;

import Model.Viaje;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiViaje {
    @GET("api/viajes")
    Call<List<Viaje>> getViajes();
    @GET("api/viajes/{dni}/pasajero")
    Call<List<Viaje>> getMisReservas(@Path("dni") int dni);
    @GET("api/viajes/viajeConcreto")
    Call<List<Viaje>> getViajeConcreto(@Path("origen") String origen,
                                       @Path("destino") String destino,
                                       @Path("fechaSalida") String fechaSalida);
}
