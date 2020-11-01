package com.android.apartmentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;

import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;
import com.android.apartmentmanagementsystem.user.GuestActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RegisterActivity extends AppCompatActivity {
    EditText etxtName, etxtCell, etxtPassword,etxtAccount,etxtGender,etxtNidNumber;
    Button btnSignUp;
    ImageView viewImage;
    String text,mediaPath,user_name,user_cell,user_password,user_account,user_gender,user_nid,user_token,user_status;
    LinearLayout linearLayoutGotoLogin,linearLayoutNidPic;
    private ProgressDialog loading;
    private static final String CHANNEL_ID = "apartment_manager";
    private static final String CHANNEL_NAME= "Apartment Manager";
    private static final String CHANNEL_DESC = "Android Push Notification Tutorial";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Create New Account");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            user_token =task.getResult().getToken();
                        }
                    }
                });

        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(RegisterActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        etxtName = findViewById(R.id.editTextRegisterName);
        etxtCell = findViewById(R.id.editTextRegisterPhone);
        etxtPassword = findViewById(R.id.editTextRegisterPassword);
        etxtAccount = findViewById(R.id.editTextRegisterAccountType);
        etxtGender = findViewById(R.id.editTextRegisterGender);
        etxtNidNumber = findViewById(R.id.editTextRegisterNid);
        linearLayoutGotoLogin=findViewById(R.id.ll9);
        linearLayoutNidPic=findViewById(R.id.ll7);
        viewImage=findViewById(R.id.viewImage);
        btnSignUp = findViewById(R.id.cirRegisterButton);
        etxtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] accountList = {"Renter", "Guard"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Choose Account Type");
                builder.setCancelable(false);
                builder.setItems(accountList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtAccount.setText(accountList[position]);
                                text = accountList[position];
                                break;

                            case 1:
                                etxtAccount.setText(accountList[position]);
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
        etxtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] accountList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Choose Gender");
                builder.setCancelable(false);
                builder.setItems(accountList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtGender.setText(accountList[position]);
                                text = accountList[position];
                                break;

                            case 1:
                                etxtGender.setText(accountList[position]);
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
        linearLayoutGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        linearLayoutNidPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);

    }
        });
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true); //default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true); //default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true); //default is true
                startActivityForResult(intent, 1213);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Internet connection checker
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                // Check if Internet present
                if (!cd.isConnectingToInternet()) {
                    // Internet Connection is not present
                    Toasty.error(RegisterActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
                }else {
                    //taking values
                    user_name = etxtName.getText().toString();
                    user_cell = etxtCell.getText().toString();
                    user_password = etxtPassword.getText().toString();
                    user_account = etxtAccount.getText().toString();
                    user_gender = etxtGender.getText().toString();
                    user_nid = etxtNidNumber.getText().toString();
                    user_status = "Pending";

                    //validation
                    if (user_name.isEmpty()) {
                        etxtName.setError("Name can not be empty! ");
                        etxtName.requestFocus();

                    } else if (user_cell.length() != 11 || !user_cell.startsWith("01")) {
                        etxtCell.setError("Invalid cell!");
                        etxtCell.requestFocus();

                    } else if (user_password.isEmpty()) {
                        etxtPassword.setError("Password can not be empty! ");
                        etxtPassword.requestFocus();

                    } else if ((user_password.length() < 4)) {
                        etxtPassword.setError("Password should be more than 3 characters!");
                        etxtPassword.requestFocus();

                    } else if (user_account.isEmpty()) {
                        etxtAccount.setError("Account Type can not be empty! ");
                        etxtAccount.requestFocus();
                        Toasty.error(RegisterActivity.this, "Please select account type !", Toast.LENGTH_SHORT).show();
                    } else if (user_gender.isEmpty()) {
                        etxtGender.setError("Please select gender ! ");
                        etxtGender.requestFocus();
                        Toasty.error(RegisterActivity.this, "Please select gender !", Toast.LENGTH_SHORT).show();
                    } else if (user_nid.isEmpty()) {
                        etxtNidNumber.setError("NID number can not be empty! ");
                        etxtNidNumber.requestFocus();
                    } else {
                        //call signup method
                        sign_up(user_name, user_cell, user_password, user_account, user_gender, user_nid,user_status,user_token);
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            // When an Image is picked
            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {


                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                Bitmap selectedImage = BitmapFactory.decodeFile(mediaPath);
                viewImage.setImageBitmap(selectedImage);

            }


        } catch (Exception e) {
            Toasty.error(this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
        }

    }

    //signup method
    private void sign_up(String name,String cell,String password,String account,String gender,String nid,String status,String token) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody p_name = RequestBody.create(MediaType.parse("text/plain"), user_name);
        RequestBody p_cell = RequestBody.create(MediaType.parse("text/plain"), user_cell);
        RequestBody p_password = RequestBody.create(MediaType.parse("text/plain"), user_password);
        RequestBody p_account = RequestBody.create(MediaType.parse("text/plain"), user_account);
        RequestBody p_gender = RequestBody.create(MediaType.parse("text/plain"), user_gender);
        RequestBody p_nid = RequestBody.create(MediaType.parse("text/plain"),user_nid);
        RequestBody p_status = RequestBody.create(MediaType.parse("text/plain"),user_status);
        RequestBody p_token = RequestBody.create(MediaType.parse("text/plain"),user_token);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Contacts> call = apiInterface.signUp(fileToUpload, filename,p_name, p_cell, p_password,p_account,p_gender,p_nid,p_status,p_token);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(RegisterActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

                else if (value.equals("exists"))
                {
                    loading.dismiss();
                    Toasty.error(RegisterActivity.this, message, Toasty.LENGTH_SHORT).show();


                }


                else {
                    loading.dismiss();
                    Toasty.error(RegisterActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toasty.error(RegisterActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }


    public void setTitle(String title){
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

}