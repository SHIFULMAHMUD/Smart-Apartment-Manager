package com.android.apartmentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    CardView cardViewProfile,cardViewPayRent,cardViewPayUtility,cardViewComplain,cardViewLogout,cardViewTask,cardViewGuard,cardViewGuest;
    LinearLayout linearLayoutNotice,linearLayoutEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Flat");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        linearLayoutNotice=findViewById(R.id.notice_layout);
        linearLayoutNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutEvent=findViewById(R.id.events_layout);
        linearLayoutEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EventsActivity.class);
                startActivity(intent);
            }
        });
        cardViewProfile=findViewById(R.id.cardview_profile);
        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        cardViewGuest=findViewById(R.id.cardview_guest);
        cardViewGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GuestActivity.class);
                startActivity(intent);
            }
        });
        cardViewGuard=findViewById(R.id.cardview_guard);
        cardViewGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GuardActivity.class);
                startActivity(intent);
            }
        });
        cardViewTask=findViewById(R.id.cardview_task);
        cardViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TaskActivity.class);
                startActivity(intent);
            }
        });
        cardViewPayRent=findViewById(R.id.cardview_pay_rent);
        cardViewPayRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PayRentActivity.class);
                startActivity(intent);
            }
        });
        cardViewPayUtility=findViewById(R.id.cardview_pay_utility);
        cardViewPayUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PayUtilityBillActivity.class);
                startActivity(intent);
            }
        });
        cardViewComplain=findViewById(R.id.cardview_complain);
        cardViewComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ComplainActivity.class);
                startActivity(intent);
            }
        });
        cardViewLogout=findViewById(R.id.logout_cv);
        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
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