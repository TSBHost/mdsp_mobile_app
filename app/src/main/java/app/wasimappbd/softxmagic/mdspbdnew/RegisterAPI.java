package app.wasimappbd.softxmagic.mdspbdnew;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Belal on 11/5/2015.
 */
public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/http://mdspbd.com/mdspbd_app/insert_retrofit.php")
    public void insertUser(
                @Field("shelid") String shelid,
                @Field("mtask") String mtask,
                @Field("stask") String stask,
                @Field("ucom") String ucom,
                @Field("latv") String latv,
                @Field("longv") String longv,
                @Field("area") String area,
                @Field("image1") String image1,
                @Field("image2") String image2,
                @Field("image3") String image3,
                @Field("image4") String image4,
                @Field("image5") String image5,
                @Field("imgname1") String imgname1,
                @Field("imgname2") String imgname2,
                @Field("imgname3") String imgname3,
                @Field("imgname4") String imgname4,
                @Field("imgname5") String imgname5,
                @Field("caption1") String caption1,
                @Field("caption2") String caption2,
                @Field("caption3") String caption3,
                @Field("caption4") String caption4,
                @Field("caption5") String caption5,
            Callback<Response> callback);
}
