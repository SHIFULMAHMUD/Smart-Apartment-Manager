package shiful.android.apartmentmanagementsystem.remote;

import shiful.android.apartmentmanagementsystem.Constant;
import shiful.android.apartmentmanagementsystem.model.Contacts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("retrofit/POST/readcontacts.php")
    Call<List<Contacts>> getContacts();

    @FormUrlEncoded
    @POST("retrofit/POST/addcontact.php")
    public Call<Contacts> insertUser(
            @Field("name") String name,
            @Field("email") String email);


    //for signup
    @FormUrlEncoded
    @POST("signup.php")
     Call<Contacts> signUp(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_ACCOUNT) String account,
            @Field(Constant.KEY_GENDER) String gender,
            @Field(Constant.KEY_NID) String nid,
            @Field(Constant.KEY_NID_PHOTO) String nid_pic);



    //for login
    @FormUrlEncoded
    @POST("login.php")
    Call<Contacts> login(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_ACCOUNT) String account);


    @FormUrlEncoded
    @POST("retrofit/POST/editcontact.php")
    public Call<Contacts> editUser(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email);


    @FormUrlEncoded
    @POST("retrofit/POST/deletecontact.php")
    Call<Contacts> deleteUser(
            @Field("id") int id
    );




    //for live data search
    @GET("retrofit/GET/getcontacts.php")
    Call<List<Contacts>> getContact(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

}