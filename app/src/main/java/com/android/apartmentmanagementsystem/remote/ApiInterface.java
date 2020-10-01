package com.android.apartmentmanagementsystem.remote;

import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.model.Complain;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Flat;
import com.android.apartmentmanagementsystem.model.Guest;
import com.android.apartmentmanagementsystem.model.Rent;
import com.android.apartmentmanagementsystem.model.Report;
import com.android.apartmentmanagementsystem.model.Slider;
import com.android.apartmentmanagementsystem.model.Task;
import com.android.apartmentmanagementsystem.model.Utility;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    //for upload image and info
    //for signup
    @Multipart
    @POST("signup.php")
     Call<Contacts> signUp(
            @Part MultipartBody.Part file,
            @Part(Constant.KEY_FILE) RequestBody name,
            @Part(Constant.KEY_NAME) RequestBody username,
            @Part(Constant.KEY_CELL) RequestBody cell,
            @Part(Constant.KEY_PASSWORD) RequestBody password,
            @Part(Constant.KEY_ACCOUNT) RequestBody account,
            @Part(Constant.KEY_GENDER) RequestBody gender,
            @Part(Constant.KEY_NID) RequestBody nid);



    //for login
    @FormUrlEncoded
    @POST("login.php")
    Call<Contacts> login(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_ACCOUNT) String account);

    //for post rent payment
    @FormUrlEncoded
    @POST("pay_rent.php")
    Call<Contacts> payRent(
            @Field(Constant.KEY_RENTER_NAME) String renter_name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_FLAT_NO) String flat_no,
            @Field(Constant.KEY_FLOOR_NO) String floor_no,
            @Field(Constant.KEY_MONTH) String rent_of_month,
            @Field(Constant.KEY_AMOUNT) String rent_amount,
            @Field(Constant.KEY_BKASH_TRX_ID) String bkash_trx_id,
            @Field(Constant.KEY_BKASH_CELL) String bkash_cell,
            @Field(Constant.KEY_NOTE) String note,
            @Field(Constant.KEY_DATE) String paying_date,
            @Field(Constant.KEY_TIME) String paying_time);

    @FormUrlEncoded
    @POST("pay_utility.php")
    Call<Contacts> payUtility(
            @Field(Constant.KEY_RENTER_NAME) String renter_name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_FLAT_NO) String flat_no,
            @Field(Constant.KEY_FLOOR_NO) String floor_no,
            @Field(Constant.KEY_MONTH) String rent_of_month,
            @Field(Constant.KEY_AMOUNT_GAS) String gas_bill_amount,
            @Field(Constant.KEY_AMOUNT_ELECTRICITY) String electricity_bill_amount,
            @Field(Constant.KEY_BKASH_TRX_ID) String bkash_trx_id,
            @Field(Constant.KEY_BKASH_CELL) String bkash_cell,
            @Field(Constant.KEY_NOTE) String note,
            @Field(Constant.KEY_DATE) String paying_date,
            @Field(Constant.KEY_TIME) String paying_time);

    @FormUrlEncoded
    @POST("task.php")
    Call<Contacts> assignTask(
            @Field(Constant.KEY_RENTER_NAME) String renter_name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_FLAT_NO) String flat_no,
            @Field(Constant.KEY_FLOOR_NO) String floor_no,
            @Field(Constant.KEY_GUARD_NAME) String guard_name,
            @Field(Constant.KEY_TASK) String task,
            @Field(Constant.KEY_DATE) String paying_date,
            @Field(Constant.KEY_TIME) String paying_time);

    @FormUrlEncoded
    @POST("complain.php")
    Call<Contacts> submitComplain(
            @Field(Constant.KEY_RENTER_NAME) String renter_name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_FLAT_NO) String flat_no,
            @Field(Constant.KEY_FLOOR_NO) String floor_no,
            @Field(Constant.KEY_COMPLAIN) String complain,
            @Field(Constant.KEY_DATE) String paying_date,
            @Field(Constant.KEY_TIME) String paying_time);

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<Contacts> updateProfile(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_GENDER) String gender,
            @Field(Constant.KEY_NID) String nid,
            @Field(Constant.KEY_CELL) String cell);

    @FormUrlEncoded
    @POST("request_flat.php")
    Call<Flat> requestFlat(
            @Field("flat_no") String flat_no,
            @Field("floor_no") String floor_no,
            @Field("request") String request);

    @FormUrlEncoded
    @POST("task_status.php")
    Call<Task> submitTask(
            @Field("id") String id,
            @Field("status") String status);

    @FormUrlEncoded
    @POST("guest.php")
    Call<Contacts> guestRequest(
            @Field(Constant.KEY_GUEST_NAME) String guest_name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_TOTAL_GUEST) String total_guest,
            @Field(Constant.KEY_PURPOSE) String purpose,
            @Field(Constant.KEY_HOST_CELL) String host_cell,
            @Field(Constant.KEY_QR_CODE) String qr_code,
            @Field(Constant.KEY_DATE) String paying_date,
            @Field(Constant.KEY_TIME) String paying_time);


    //for get profile data
    @GET("profile.php")
    Call<List<Contacts>> getProfile(
            @Query("cell") String cell

    );
    @GET("find_qr.php")
    Call<List<Guest>> getQr(
            @Query("qr_code") String qr_code

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

    @GET("my_guard.php")
    Call<List<Contacts>> getMyGuard(
            @Query("name") String name
    );
    @GET("rent_history.php")
    Call<List<Rent>> getRent(
            @Query("getcell") String getcell
    );
    @GET("utility_history.php")
    Call<List<Utility>> getUtility(
            @Query("cell") String cell
    );
    @GET("guest_history.php")
    Call<List<Guest>> getGuest(
            @Query("host_cell") String host_cell
    );
    @GET("task_history.php")
    Call<List<Task>> getTask(
            @Query("cell") String cell
    );
    @GET("guard_task.php")
    Call<List<Task>> getGuardTask(
            @Query("guard_name") String guard_name
    );
    @GET("complain_history.php")
    Call<List<Complain>> getComplain(
            @Query("cell") String cell
    );
    @GET("rent_report.php")
    Call<List<Report>> getRentReport(
            @Query("cell") String cell
    );
    @GET("utility_report.php")
    Call<List<Report>> getUtilityReport(
            @Query("cell") String cell
    );
    @GET("my_guest.php")
    Call<List<Guest>> getQrGuest(
            @Query("qr_code") String qr_code
    );
    @GET("profile_name.php")
    Call<List<Contacts>> getProfileName(
            @Query("cell") String cell
    );
    @GET("slider_image.php")
    Call<List<Slider>> getSliderImage(
            @Query("imageone") String imageone,
            @Query("imagetwo") String imagetwo,
            @Query("imagethree") String imagethree
    );
    @GET("get_flat.php")
    Call<List<Flat>> getFlatDetails(
            @Query("flat_no") String flat_no,
            @Query("floor_no") String floor_no
    );
}