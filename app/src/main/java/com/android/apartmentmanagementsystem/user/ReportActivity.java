package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.apartmentmanagementsystem.ConnectionDetector;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.user.history.ComplainHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.GuestHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.RentHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.TaskHistoryActivity;
import com.android.apartmentmanagementsystem.user.history.UtilityHistoryActivity;

public class ReportActivity extends AppCompatActivity {
    LinearLayout rentLayout,utilityLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Report");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(ReportActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        rentLayout=findViewById(R.id.layout1);
        utilityLayout=findViewById(R.id.layout2);

        rentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReportActivity.this, RentReportActivity.class);
                startActivity(intent);
            }
        });
        utilityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReportActivity.this, UtilityReportActivity.class);
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