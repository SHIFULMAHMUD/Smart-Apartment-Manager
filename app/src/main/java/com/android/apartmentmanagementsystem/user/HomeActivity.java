package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.ConnectionDetector;
import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.ProfileActivity;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.adapter.RecyclerViewAdapter;
import com.android.apartmentmanagementsystem.guard.GuardHomeActivity;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Flat;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    LinearLayout linearLayoutRent,linearLayoutFeature;
    String getCell,profileName,userCell;
    TextView profileNameTv;
    private List<Flat> flatList;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Apartment Manager");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(HomeActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getProfileName(getCell);
            getFlatData(getCell);
        }
        // Refresher
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.equal_color,R.color.function_color,R.color.colorRed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        startActivity(getIntent());
                        finish();
                    }
                },3000);
            }
        });
        linearLayoutRent=findViewById(R.id.layout2);
        linearLayoutFeature=findViewById(R.id.layout3);
        linearLayoutRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, RentActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCell.equals(userCell)) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toasty.info(HomeActivity.this,"Rent a Flat to avail all features !",Toasty.LENGTH_LONG).show();
                }
            }
        });
    }
    public void getProfileName(String cell) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Contacts>> call;
        call = apiInterface.getProfileName(cell);

        call.enqueue(new Callback<List<Contacts>>() {
            @Override
            public void onResponse(Call<List<Contacts>> call, Response<List<Contacts>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Contacts> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                        Toasty.warning(HomeActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {

                        profileName = profileData.get(0).getName();
                        profileNameTv=findViewById(R.id.profile_name_tv);
                        profileNameTv.setText("WELCOME, "+ profileName);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {

                Toast.makeText(HomeActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });


    }
    public void getFlatData(String cell) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Flat>> call;
        call = apiInterface.getFlatOwner(cell);

        call.enqueue(new Callback<List<Flat>>() {
            @Override
            public void onResponse(Call<List<Flat>> call, Response<List<Flat>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Flat> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {
                        userCell = "0";
                    } else {

                        userCell = profileData.get(0).getRenter_cell();

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Flat>> call, Throwable t) {

            }
        });


    }
}