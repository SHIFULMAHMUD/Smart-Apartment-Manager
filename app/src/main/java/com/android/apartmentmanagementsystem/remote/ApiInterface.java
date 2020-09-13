package com.android.apartmentmanagementsystem.remote;

import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.model.Contacts;

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




    //for get profile data
    @GET("profile.php")
    Call<List<Contacts>> getProfile(
            @Query("name") String name,
            @Query("cell") String cell,
            @Query("password") String password,
            @Query("account") String account,
            @Query("gender") String gender,
            @Query("nid") String nid
    );

    @GET("notice.php")
    Call<List<Contacts>> getNotice(
            @Query("date") String date,
            @Query("notice") String notice
    );

    @GET("event.php")
    Call<List<Contacts>> getEvent(
            @Query("date") String date,
            @Query("event") String event
    );
    @GET("guard.php")
    Call<List<Contacts>> getGuard(
            @Query("name") String name,
            @Query("cell") String cell
    );
}