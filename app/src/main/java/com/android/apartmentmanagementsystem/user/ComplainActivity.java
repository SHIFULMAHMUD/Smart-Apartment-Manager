package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.List;

public class ComplainActivity extends AppCompatActivity {
    EditText renter_name_et, renter_cell_et, complain_et;
    Spinner spinner_flat_no, spinner_floor_no;
    Button submit_btn;
    String[] flat = { "1st", "2nd", "3rd", "4th"};
    String[] floor = { "1st", "2nd", "3rd", "4th", "5th", "6th"};
    String flat_no="";
    String floor_no="";
    String current_date,current_time,getCell,profileName;
    private ApiInterface apiInterface;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Write Complain to Authority");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        getProfileName(getCell);
        renter_name_et =findViewById(R.id.renter_name_et);
        renter_cell_et =findViewById(R.id.renter_cell_et);
        renter_cell_et.setText(getCell);
        complain_et =findViewById(R.id.complain_et);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
                                String dateString = sdf.format(date);
                                current_date = dateString;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss a");
                                String dateString = sdf.format(date);
                                current_time = dateString;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();

        spinner_flat_no =findViewById(R.id.spinner);
        spinner_flat_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flat_no=flat[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, flat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_flat_no.setAdapter(aa);
        spinner_floor_no =findViewById(R.id.spinner2);
        spinner_floor_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                floor_no=floor[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item, floor);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_floor_no.setAdapter(bb);

        submit_btn=findViewById(R.id.submitComplainButton);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ComplainActivity.this);
                builder.setTitle("Confirm Submission?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String name = renter_name_et.getText().toString();
                                String cell = renter_cell_et.getText().toString();
                                String flat_num = flat_no;
                                String floor_num = floor_no;
                                String complain = complain_et.getText().toString();
                                String date = current_date;
                                String time = current_time;

                                //validation

                                if (name.isEmpty()) {
                                    renter_name_et.setError("Name can't be empty! ");
                                    renter_name_et.requestFocus();

                                }
                                else if (cell.length() != 11 || !cell.startsWith("01")) {
                                    renter_cell_et.setError("Invalid cell!");
                                    renter_cell_et.requestFocus();

                                }
                                else if (flat_num.isEmpty()) {
                                    Toasty.error(ComplainActivity.this, "Please select flat number !", Toast.LENGTH_SHORT).show();
                                }
                                else if (floor_num.isEmpty()) {
                                    Toasty.error(ComplainActivity.this, "Please select floor number !", Toast.LENGTH_SHORT).show();
                                }
                                else if (complain.isEmpty()) {
                                    complain_et.setError("Please put your complain ! ");
                                    complain_et.requestFocus();

                                }

                                else {
                                    //call login method
                                    submitComplain(name,cell, flat_num,floor_num,complain,date,time);
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

                        Toasty.warning(ComplainActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {

                        profileName = profileData.get(0).getName();
                        renter_name_et.setText(profileName);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {

                Toast.makeText(ComplainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });


    }
    private void submitComplain(String name,String cell,String flat_no,String floor_no, String complain, String date, String time) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.submitComplain(name,cell,flat_no,floor_no,complain,date,time);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(ComplainActivity.this, message, Toasty.LENGTH_SHORT).show();
                    /*Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();*/
                }



                else {
                    loading.dismiss();
                    Toasty.error(ComplainActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toasty.error(ComplainActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
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