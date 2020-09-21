package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    EditText renter_name_et, renter_cell_et, task_et;
    Spinner spinner_flat_no, spinner_floor_no, spinner_guard_name;
    Button submit_btn;
    String[] flat = { "1st", "2nd", "3rd", "4th"};
    String[] floor = { "1st", "2nd", "3rd", "4th", "5th", "6th"};
    private ArrayList<String> guard;
    ArrayAdapter<String> cc;
    String flat_no="";
    String floor_no="";
    String guard_name="";
    private List<Contacts> contactsList;
    private ApiInterface apiInterface;
    String current_date,current_time;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Task");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        guard = new ArrayList<String>();
        renter_name_et =findViewById(R.id.renter_name_et);
        renter_cell_et =findViewById(R.id.renter_cell_et);
        task_et =findViewById(R.id.task_et);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        getGuardData("");
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

        spinner_guard_name =findViewById(R.id.spinner3);
        spinner_guard_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGuardData("");
                guard_name= guard.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Creating the ArrayAdapter instance having the country list

        cc = new ArrayAdapter<String>(TaskActivity.this,android.R.layout.simple_spinner_item, guard);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_guard_name.setAdapter(cc);

        submit_btn=findViewById(R.id.submitTaskButton);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
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
                                String guard = guard_name;
                                String task = task_et.getText().toString();
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
                                    Toasty.error(TaskActivity.this, "Please select flat number !", Toast.LENGTH_SHORT).show();
                                }
                                else if (floor_num.isEmpty()) {
                                    Toasty.error(TaskActivity.this, "Please select floor number !", Toast.LENGTH_SHORT).show();
                                }
                                else if (guard.isEmpty()) {
                                    Toasty.error(TaskActivity.this, "Please select guard name !", Toast.LENGTH_SHORT).show();
                                }
                                else if (task.isEmpty()) {
                                    task_et.setError("Task can't be empty! ");
                                    task_et.requestFocus();

                                }

                                else {
                                    //call login method
                                    assignTask(name,cell, flat_num,floor_num,guard,task,date,time);
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
    public void getGuardData(String name) {
        Call<List<Contacts>> call = apiInterface.getMyGuard(name);
        call.enqueue(new Callback<List<Contacts>>() {
            @Override
            public void onResponse(Call<List<Contacts>> call, Response<List<Contacts>> response) {
                contactsList = response.body();
                cc.clear();
                for (int i = 0; i < contactsList.size(); i++) {
                    cc.add(contactsList.get(i).getName());
                }

            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {

            }
        });
    }
    private void assignTask(String name,String cell,String flat_no,String floor_no, String guard_name, String task, String date, String time) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.assignTask(name,cell,flat_no,floor_no,guard_name,task,date,time);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(TaskActivity.this, message, Toasty.LENGTH_SHORT).show();
                    /*Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();*/
                }

                else {
                    loading.dismiss();
                    Toasty.error(TaskActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toasty.error(TaskActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
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