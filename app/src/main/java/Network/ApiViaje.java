package Network;

import Model.Viaje;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiViaje {
    @GET("api/viajes")
    Call<Viaje> getViajes();
}
