package com.android.apartmentmanagementsystem.guard;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.LoginActivity;
import com.android.apartmentmanagementsystem.ProfileActivity;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.UpdateProfileActivity;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Guest;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class GuardHomeActivity extends AppCompatActivity {
    private LinearLayout scanqr, entermanually;
    private Dialog dialog;
    String qrCode,guestName,guestCell,totalGuest,purpose,hostCell;
    ImageView logout_iv;
    Button chkBtn;
    TextView guestNameTv,guestCellTv,totalGuestTv,purposeTv,hostCellTv;
    private List<Guest> guestList;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_home);
        logout_iv=findViewById(R.id.logout);
        logout_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(GuardHomeActivity.this);
                builder.setTitle("Want to Logout ?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                Intent intent=new Intent(GuardHomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
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

        scanqr=findViewById(R.id.scanqr);
        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GuardHomeActivity.this,GuardMainActivity.class);
                startActivity(intent);
            }
        });
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        qrCode = getIntent().getStringExtra("qrvalue");

        chkBtn=findViewById(R.id.buttonCheckQr);
        chkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQrData("");
            }
        });
        entermanually = (LinearLayout) findViewById(R.id.manualcode);
        entermanually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Codedialog();
            }
        });

    }
    public void getQrData(final String qr_code) {
        Call<List<Guest>> call = apiInterface.getQr(qr_code);
        call.enqueue(new Callback<List<Guest>>() {
            @Override
            public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {
                guestList = response.body();

                for (int i = 0; i < guestList.size(); i++) {
                    if (qrCode !=null && qrCode.equals(guestList.get(i).getQr_code())){
                        getGuestData(qrCode);
                        Toasty.success(GuardHomeActivity.this,"QR Code Matched !",Toasty.LENGTH_LONG).show();
                    }
                    else if (qr_code != null && qr_code.equals(guestList.get(i).getQr_code()))
                    {
                        getGuestData(qr_code);
                        Toasty.success(GuardHomeActivity.this,"QR Code Matched !",Toasty.LENGTH_LONG).show();
                    }
                    else {
                        Toasty.error(GuardHomeActivity.this,"QR Code not matched !",Toasty.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Guest>> call, Throwable t) {

            }
        });
    }
    private void Codedialog() {

        dialog = new Dialog(GuardHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.codedailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        final EditText codes;
        Button submit;
        ImageView delete;
        submit = dialog.findViewById(R.id.submit);
        codes = dialog.findViewById(R.id.code);
        delete = dialog.findViewById(R.id.deleteicon);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codes.getText().toString().equals("")) {
                    Toast.makeText(GuardHomeActivity.this, "Please Enter Correct Code!!", Toast.LENGTH_SHORT).show();
                } else {
                    getQrData(codes.getText().toString());
                }
            }
        });
        dialog.show();
    }
    private void Qrdialog() {

        dialog = new Dialog(GuardHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.qrdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        ImageView delete;
        guestNameTv=dialog.findViewById(R.id.qr_guest_name_tv);
        guestCellTv=dialog.findViewById(R.id.qr_guest_cell_tv);
        totalGuestTv=dialog.findViewById(R.id.qr_total_guest_tv);
        purposeTv=dialog.findViewById(R.id.qr_purpose_tv);
        hostCellTv=dialog.findViewById(R.id.qr_host_cell_tv);
        //getGuestData("");
        guestNameTv.setText(guestName);
        guestCellTv.setText(guestCell);
        totalGuestTv.setText(totalGuest);
        purposeTv.setText(purpose);
        hostCellTv.setText(hostCell);

        delete = dialog.findViewById(R.id.deleteicon);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void getGuestData(String qrCode) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Guest>> call;
        call = apiInterface.getQrGuest(qrCode);

        call.enqueue(new Callback<List<Guest>>() {
            @Override
            public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Guest> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                        Toasty.warning(GuardHomeActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();

                    } else {

                        guestName = profileData.get(0).getGuest_name();
                        guestCell = profileData.get(0).getGuest_cell();
                        totalGuest = profileData.get(0).getTotal_guest();
                        purpose = profileData.get(0).getPurpose();
                        hostCell = profileData.get(0).getHost_cell();
                        Qrdialog();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Guest>> call, Throwable t) {

                Toast.makeText(GuardHomeActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });


    }
}