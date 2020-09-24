package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.LoginActivity;
import com.android.apartmentmanagementsystem.ProfileActivity;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    CardView cardViewProfile,cardViewPayRent,cardViewPayUtility,cardViewComplain,cardViewLogout,cardViewTask,cardViewGuard,cardViewGuest,cardViewReport;
    LinearLayout linearLayoutNotice,linearLayoutEvent,linearLayoutHistory;
    private ApiInterface apiInterface;
    String getCell,profileName;
    TextView profileNameTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Flat");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        getProfileName(getCell);
        linearLayoutNotice=findViewById(R.id.notice_layout);
        linearLayoutNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutEvent=findViewById(R.id.events_layout);
        linearLayoutEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EventsActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutHistory=findViewById(R.id.history_layout);
        linearLayoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        cardViewProfile=findViewById(R.id.cardview_profile);
        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        cardViewReport=findViewById(R.id.cardview_report);
        cardViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });
        cardViewGuest=findViewById(R.id.cardview_guest);
        cardViewGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GuestHomeActivity.class);
                startActivity(intent);
            }
        });
        cardViewGuard=findViewById(R.id.cardview_guard);
        cardViewGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GuardActivity.class);
                startActivity(intent);
            }
        });
        cardViewTask=findViewById(R.id.cardview_task);
        cardViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TaskActivity.class);
                startActivity(intent);
            }
        });
        cardViewPayRent=findViewById(R.id.cardview_pay_rent);
        cardViewPayRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PayRentActivity.class);
                startActivity(intent);
            }
        });
        cardViewPayUtility=findViewById(R.id.cardview_pay_utility);
        cardViewPayUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PayUtilityBillActivity.class);
                startActivity(intent);
            }
        });
        cardViewComplain=findViewById(R.id.cardview_complain);
        cardViewComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ComplainActivity.class);
                startActivity(intent);
            }
        });
        cardViewLogout=findViewById(R.id.logout_cv);
        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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

                        Toasty.warning(MainActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {

                        profileName = profileData.get(0).getName();
                        profileNameTv=findViewById(R.id.profile_name_tv);
                        profileNameTv.setText("WELCOME, "+ profileName);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {

                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });


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