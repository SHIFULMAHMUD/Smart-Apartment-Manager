package com.android.apartmentmanagementsystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText etxtName, etxtCell, etxtPassword,etxtAccount,etxtGender,etxtNidNumber;
    Button btnSignUp;
    ImageView viewImage;
    String text;
    LinearLayout linearLayoutGotoLogin,linearLayoutNidPic;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Create New Account");
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

                //taking values
                String name = etxtName.getText().toString();
                String cell = etxtCell.getText().toString();
                String password = etxtPassword.getText().toString();
                String account = etxtAccount.getText().toString();
                String gender = etxtGender.getText().toString();
                String nid = etxtNidNumber.getText().toString();
                String nid_pic= String.valueOf(viewImage);

                //validation
                if (name.isEmpty())
                {
                    etxtName.setError("Name can not be empty! ");
                    etxtName.requestFocus();

                }

                else if (cell.length()!=11 || !cell.startsWith("01"))
                {
                    etxtCell.setError("Invalid cell!");
                    etxtCell.requestFocus();

                }


                else if (password.isEmpty())
                {
                    etxtPassword.setError("Password can not be empty! ");
                    etxtPassword.requestFocus();

                }else if ((password.length() < 4)) {
                    etxtPassword.setError("Password should be more than 3 characters!");
                    etxtPassword.requestFocus();

                }
                else if (account.isEmpty())
                {
                    etxtAccount.setError("Account Type can not be empty! ");
                    etxtAccount.requestFocus();
                    Toasty.error(RegisterActivity.this, "Please select account type !", Toast.LENGTH_SHORT).show();
                }
                else if (gender.isEmpty())
                {
                    etxtGender.setError("Please select gender ! ");
                    etxtGender.requestFocus();
                    Toasty.error(RegisterActivity.this, "Please select gender !", Toast.LENGTH_SHORT).show();
                }
                else if (nid.isEmpty())
                {
                    etxtNidNumber.setError("NID number can not be empty! ");
                    etxtNidNumber.requestFocus();
                }
                else
                {
                    //call signup method
                    sign_up(name,cell,password,account,gender,nid,nid_pic);
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


                String mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                Bitmap selectedImage = BitmapFactory.decodeFile(mediaPath);
                viewImage.setImageBitmap(selectedImage);

            }


        } catch (Exception e) {
            Toasty.error(this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
        }

    }

    //signup method
    private void sign_up(String name,String cell,String password,String account,String gender,String nid,String nid_pic) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.signUp(name, cell, password,account,gender,nid,nid_pic);
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