package Network;

import java.util.List;

import Model.Viaje;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiViaje {
    @GET("api/viajes")
    Call<List<Viaje>> getViajes();
}
