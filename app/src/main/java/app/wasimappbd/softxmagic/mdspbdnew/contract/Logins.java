package app.wasimappbd.softxmagic.mdspbdnew.contract;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface Logins {

    String SERVER_URL = "/mdspbd_app/logins.php";


    interface MainView{
        void loginValidation();
        void loginSuccess(String output);
        void loginError();
    }

    interface Presenter{
    }

    @FormUrlEncoded
    @POST(SERVER_URL)
    public void insertUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("token") String token,
            Callback<Response> callback);
}
