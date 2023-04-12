package Network;

import java.util.List;

import Model.User;
import Model.Viaje;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiUser {
    @GET("api/usuarios/{dni}")
    Call<User> getUser(@Path("dni") int dni);
    @GET("api/usuarios/{dni}/viajes")
    Call<List<Viaje>> getMisViajes(@Path("dni") int dni);

}
