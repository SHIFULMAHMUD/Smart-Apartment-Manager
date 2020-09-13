package com.android.apartmentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private List<Contacts> contactsList;
    private ApiInterface apiInterface;
    TextView nameTv,cellTv,accountTv,genderTv,nidTv;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View / Update Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        nameTv=findViewById(R.id.profile_name_tv);
        cellTv=findViewById(R.id.profile_cell_tv);
        accountTv=findViewById(R.id.profile_account_tv);
        genderTv=findViewById(R.id.profile_gender_tv);
        nidTv=findViewById(R.id.profile_nid_tv);
       getProfileData("","","","","","");

    }


    public void getProfileData(String name, String cell, String password, String account, String gender, final String nid) {

        loading=new ProgressDialog(ProfileActivity.this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Contacts>> call;
        call = apiInterface.getProfile(name,cell,password,account,gender,nid);

        call.enqueue(new Callback<List<Contacts>>() {
            @Override
            public void onResponse(Call<List<Contacts>> call, Response<List<Contacts>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Contacts> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                        Toasty.warning(ProfileActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {


                        String userName = profileData.get(0).getName();
                        String userCell = profileData.get(0).getCell();
                        String userPassword = profileData.get(0).getPassword();
                        String userAccountType = profileData.get(0).getAccount();
                        String userGender = profileData.get(0).getGender();
                        String userNid = profileData.get(0).getNid();

                        //String productImage = profileData.get(0).getProductImage();

                        nameTv.setText(userName);
                        cellTv.setText(userCell);
                        accountTv.setText(userAccountType);
                        genderTv.setText(userGender);
                        nidTv.setText(userNid);


                        /*String imageUrl= Constant.PRODUCT_IMAGE_URL+productImage;

                        if (productImage != null) {
                            if (productImage.length() < 3) {

                                imgProduct.setImageResource(R.drawable.image_placeholder);
                            } else {


                                Glide.with(EditProductActivity.this)
                                        .load(imageUrl)
                                        .placeholder(R.drawable.loading)
                                        .error(R.drawable.image_placeholder)
                                        .into(imgProduct);

                            }
                        }*/


                    }

                }
            }

            @Override
        public void onFailure(Call<List<Contacts>> call, Throwable t) {

                loading.dismiss();
                Toast.makeText(ProfileActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });


    }
    //when activity is resumed this method is called
    @Override
    protected void onResume() {
        super.onResume();
        getProfileData("", "","","","","");
    }
    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



