package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.user.history.ComplainHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.GuestHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.RentHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.TaskHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.UtilityHistoryActivity;

public class HistoryActivity extends AppCompatActivity {
    LinearLayout rentLayout,utilityLayout,guestLayout,taskLayout,complainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All History");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        rentLayout=findViewById(R.id.layout1);
        utilityLayout=findViewById(R.id.layout2);
        guestLayout=findViewById(R.id.layout3);
        taskLayout=findViewById(R.id.layout4);
        complainLayout=findViewById(R.id.layout5);
        rentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HistoryActivity.this, RentHistoryActivity.class);
                startActivity(intent);
            }
        });
        utilityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HistoryActivity.this, UtilityHistoryActivity.class);
                startActivity(intent);
            }
        });
        guestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HistoryActivity.this, GuestHistoryActivity.class);
                startActivity(intent);
            }
        });
        taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HistoryActivity.this, TaskHistoryActivity.class);
                startActivity(intent);
            }
        });
        complainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HistoryActivity.this, ComplainHistoryActivity.class);
                startActivity(intent);
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