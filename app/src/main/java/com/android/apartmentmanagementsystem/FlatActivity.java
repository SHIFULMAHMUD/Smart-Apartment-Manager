package com.android.apartmentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.adapter.Adapter;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Flat;
import com.android.apartmentmanagementsystem.model.Slider;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;
import com.android.apartmentmanagementsystem.user.GuardActivity;
import com.android.apartmentmanagementsystem.user.GuestActivity;
import com.android.apartmentmanagementsystem.user.HomeActivity;
import com.android.apartmentmanagementsystem.user.PayRentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.android.apartmentmanagementsystem.Constant.BASE_URL;

public class FlatActivity extends AppCompatActivity {
    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    int currentPage = 0,NUM_PAGES=4;
    TextView flat_details_tv,flat_price_tv;
    Timer timer;String getCell,profileName,userCell;
    private ProgressDialog loading;
    Button confirmBtn;
    String flat_no,floor_no,flat_details,flat_price,slider_one,slider_two,slider_three;
    ArrayList<String> cc;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 10000; // time in milliseconds between successive task executions.
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    List<Slider> slider;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rent Flat");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        viewPager = (ViewPager)findViewById(R.id.pager);
        flat_details_tv=findViewById(R.id.room_details);
        flat_price_tv=findViewById(R.id.room_price);
        confirmBtn=findViewById(R.id.cirConfirmButton);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(FlatActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getProfileName(getCell);
            getFlatData(getCell);
        }
        slider = new ArrayList<>();
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        cc=new ArrayList<>();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        flat_no = getIntent().getStringExtra("flat");
        floor_no = getIntent().getStringExtra("floor");
        flat_details = getIntent().getStringExtra("details");
        flat_price = getIntent().getStringExtra("price");
        slider_one = getIntent().getStringExtra("one");
        slider_two = getIntent().getStringExtra("two");
        slider_three = getIntent().getStringExtra("three");
        flat_details_tv.setText(flat_details);
        flat_price_tv.setText("Tk "+ flat_price);
        cc.add(Constant.IMAGE_URL+slider_one);
        cc.add(Constant.IMAGE_URL+slider_two);
        cc.add(Constant.IMAGE_URL+slider_three);
        getSliderData("","","");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(FlatActivity.this);
                builder.setTitle("Confirm Submission?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                SharedPreferences preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                if (preferences.contains("isUserLogin")) {
                                    String request = "Pending";
                                    if (userCell=="0") {
                                        submitRequest(flat_no, floor_no, request, profileName, getCell);
                                    }else if (userCell.equals(getCell)){
                                        Toasty.error(FlatActivity.this,"Sorry! You can not request more than one flat.",Toasty.LENGTH_LONG).show();
                                    }

                                } else {
                                    Intent intent = new Intent(FlatActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                                break;
                            case 1:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog accountTypeDialog = builder.create();

                accountTypeDialog.show();
            }
        });
    }
    public void getSliderData(String imageone, String imagetwo,String imagethree) {
        Call<List<Slider>> call = apiInterface.getSliderImage(imageone,imagetwo,imagethree);
        call.enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {

                slider = response.body();
                myCustomPagerAdapter = new MyCustomPagerAdapter(FlatActivity.this, cc);
                viewPager.setAdapter(myCustomPagerAdapter);
                myCustomPagerAdapter.notifyDataSetChanged();

                /*After setting the adapter use the timer */
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES - 1) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                };

                timer = new Timer(); // This will create a new Thread
                timer.schedule(new TimerTask() { // task to be scheduled
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, DELAY_MS, PERIOD_MS);


                dotscount = myCustomPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(FlatActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));


            }
            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Toast.makeText(FlatActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getFlatData(String cell) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Flat>> call;
        call = apiInterface.getFlatRenter(cell);

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
    private void submitRequest(String flatno,String floorno, String request,String name,String cell) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Flat> call = apiInterface.requestFlat(flatno,floorno,request,name,cell);
        call.enqueue(new Callback<Flat>() {
            @Override
            public void onResponse(Call<Flat> call, Response<Flat> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(FlatActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(FlatActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }



                else {
                    loading.dismiss();
                    Toasty.error(FlatActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Flat> call, Throwable t) {

                loading.dismiss();
                Toasty.error(FlatActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
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

//                        Toasty.warning(FlatActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {

                        profileName = profileData.get(0).getName();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {

                Toast.makeText(FlatActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
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