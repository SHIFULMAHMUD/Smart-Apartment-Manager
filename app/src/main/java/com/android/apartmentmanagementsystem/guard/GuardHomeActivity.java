package com.android.apartmentmanagementsystem.guard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GuardHomeActivity extends AppCompatActivity {
    private LinearLayout scanqr, entermanually;
    TextView qrcode_tv;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_home);
        scanqr=findViewById(R.id.scanqr);
        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GuardHomeActivity.this,GuardMainActivity.class);
                startActivity(intent);
            }
        });
        /*String myData = getIntent().getStringExtra("qrvalue");
        qrcode_tv=findViewById(R.id.qrcode_tv);
        qrcode_tv.setText(myData);*/
        entermanually = (LinearLayout) findViewById(R.id.manualcode);
        entermanually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Codedialog();
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
                   // TodaysdataList(codes.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}