package Network;

import Model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiUser {
    @GET("api/usuarios/{dni}")
    Call<User> getUserData();
}
