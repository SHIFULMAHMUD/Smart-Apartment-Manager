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
import android.widget.TextView;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.ConnectionDetector;
import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Flat;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.List;

public class PayUtilityBillActivity extends AppCompatActivity {
    EditText renter_name_et, renter_cell_et, gas_bill_amount_et,electricity_bill_amount_et, bkash_trx_id_et, bkash_cell_et, note_et;
    Spinner spinner_flat_no, spinner_floor_no, spinner_payment_month;
    Button submit_btn;
    String[] flat = { "101", "201", "102", "202","103", "203", "104", "204", "105", "205"};
    String[] floor = { "1st", "2nd", "3rd", "4th", "5th"};
    String[] month = { "January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"};
    String flat_no="";
    String floor_no="";
    String month_name="";
    String current_date,current_time,getCell,profileName,gasBill,electricityBill;
    private ProgressDialog loading;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_utility_bill);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Utility Bill Payment");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(PayUtilityBillActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
        getProfileName(getCell);
        getUtilityBill(getCell);
        }
        renter_name_et =findViewById(R.id.renter_name_et);
        renter_cell_et =findViewById(R.id.renter_cell_et);
        gas_bill_amount_et =findViewById(R.id.gas_bill_amount_et);
        electricity_bill_amount_et =findViewById(R.id.electricity_bill_amount_et);
        bkash_trx_id_et =findViewById(R.id.bkash_trx_id_et);
        bkash_cell_et =findViewById(R.id.bkash_cell_et);
        note_et =findViewById(R.id.note_et);
        renter_cell_et.setText(getCell);
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
        spinner_payment_month =findViewById(R.id.spinner3);
        spinner_payment_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month_name=month[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter cc = new ArrayAdapter(this,android.R.layout.simple_spinner_item, month);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_payment_month.setAdapter(cc);
        submit_btn=findViewById(R.id.submitUtilityButton);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PayUtilityBillActivity.this);
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
                                String rent_month = month_name;
                                String amount = gas_bill_amount_et.getText().toString();
                                String amounttwo = electricity_bill_amount_et.getText().toString();
                                String bkash_trx_id = bkash_trx_id_et.getText().toString();
                                String bkash_cell = bkash_cell_et.getText().toString();
                                String note = note_et.getText().toString();
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
                                    Toasty.error(PayUtilityBillActivity.this, "Please select flat number !", Toast.LENGTH_SHORT).show();
                                }
                                else if (floor_num.isEmpty()) {
                                    Toasty.error(PayUtilityBillActivity.this, "Please select floor number !", Toast.LENGTH_SHORT).show();
                                }
                                else if (rent_month.isEmpty()) {
                                    Toasty.error(PayUtilityBillActivity.this, "Please select month !", Toast.LENGTH_SHORT).show();
                                }
                                else if (amount.isEmpty()) {
                                    gas_bill_amount_et.setError("Gas bill amount can't be empty! ");
                                    gas_bill_amount_et.requestFocus();

                                }
                                else if (amounttwo.isEmpty()) {
                                    electricity_bill_amount_et.setError("Electricity bill amount can't be empty! ");
                                    electricity_bill_amount_et.requestFocus();

                                }
                                else if (bkash_trx_id.isEmpty()) {
                                    bkash_trx_id_et.setError("bKash Trx ID can't be empty! ");
                                    bkash_trx_id_et.requestFocus();
                                }
                                else if (bkash_cell.length() != 11 || !bkash_cell.startsWith("01")) {
                                    bkash_cell_et.setError("Invalid cell!");
                                    bkash_cell_et.requestFocus();

                                }
                                else {
                                    //call login method
                                    payUtility(name,cell, flat_num,floor_num,rent_month,amount,amounttwo,bkash_trx_id,bkash_cell,note,date,time);
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

                        Toasty.warning(PayUtilityBillActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {

                        profileName = profileData.get(0).getName();
                        renter_name_et.setText(profileName);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {

                Toast.makeText(PayUtilityBillActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });


    }
    public void getUtilityBill(String cell) {

        loading=new ProgressDialog(PayUtilityBillActivity.this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Flat>> call;
        call = apiInterface.getUtilityBill(cell);

        call.enqueue(new Callback<List<Flat>>() {
            @Override
            public void onResponse(Call<List<Flat>> call, Response<List<Flat>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Flat> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                        Toasty.warning(PayUtilityBillActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {


                        gasBill = profileData.get(0).getGas_bill();
                        electricityBill = profileData.get(0).getElectricity_bill();
                        gas_bill_amount_et.setText(gasBill);
                        electricity_bill_amount_et.setText(electricityBill);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Flat>> call, Throwable t) {

                loading.dismiss();
                Toast.makeText(PayUtilityBillActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });


    }
    private void payUtility(String name,String cell,String flat_no,String floor_no, String month, String amount, String amounttwo, String trx_id, String bkash_cell, String note, String date, String time) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.payUtility(name,cell,flat_no,floor_no,month,amount,amounttwo,trx_id,bkash_cell,note,date,time);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(PayUtilityBillActivity.this, message, Toasty.LENGTH_SHORT).show();
                    /*Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();*/
                }



                else {
                    loading.dismiss();
                    Toasty.error(PayUtilityBillActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toasty.error(PayUtilityBillActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
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