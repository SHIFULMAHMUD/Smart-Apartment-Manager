package com.android.apartmentmanagementsystem;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;
import com.android.apartmentmanagementsystem.user.ComplainActivity;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText name_et, password_et, gender_et,nid_et;
    Button update_btn;
    String getCell,text,name,password,gender,nid;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        name_et=findViewById(R.id.editTextUpdateName);
        password_et=findViewById(R.id.editTextUpdatePassword);
        gender_et=findViewById(R.id.editTextUpdateGender);
        nid_et=findViewById(R.id.editTextUpdateNid);
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("pass");
        gender = getIntent().getStringExtra("gender");
        nid = getIntent().getStringExtra("nid");
        name_et.setText(name);
        password_et.setText(password);
        gender_et.setText(gender);
        nid_et.setText(nid);
        gender_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] accountList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Choose Gender");
                builder.setCancelable(false);
                builder.setItems(accountList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                gender_et.setText(accountList[position]);
                                text = accountList[position];
                                break;

                            case 1:
                                gender_et.setText(accountList[position]);
                                text = accountList[position];
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

        update_btn=findViewById(R.id.updateProfileButton);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Confirm Submission?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String name = name_et.getText().toString();
                                String password = password_et.getText().toString();
                                String gender = gender_et.getText().toString();
                                String nid = nid_et.getText().toString();

                                //validation

                                if (name.isEmpty()) {
                                    name_et.setError("Name can't be empty! ");
                                    name_et.requestFocus();

                                }
                                else if (password.isEmpty()) {
                                    password_et.setError("Password can't be empty! ");
                                    password_et.requestFocus();

                                }

                                else if (gender.isEmpty()) {
                                    gender_et.setError("Please select your gender ! ");
                                    gender_et.requestFocus();
                                    Toasty.error(UpdateProfileActivity.this,"Please select your gender ! ",Toasty.LENGTH_SHORT).show();
                                }
                                else if (nid.isEmpty()) {
                                    nid_et.setError("NID field can't be empty! ");
                                    nid_et.requestFocus();
                                }
                                else {
                                    //call login method
                                    updateProfile(name,password, gender,nid,getCell);
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
    private void updateProfile(String name,String password,String gender,String nid,String cell) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.updateProfile(name,password,gender,nid,cell);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(UpdateProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }



                else {
                    loading.dismiss();
                    Toasty.error(UpdateProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toasty.error(UpdateProfileActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
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