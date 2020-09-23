package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.adapter.RentReportAdapter;
import com.android.apartmentmanagementsystem.model.Report;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.util.List;

public class RentReportActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RentReportAdapter adapter;
    private List<Report> reportList;
    private ApiInterface apiInterface;
    String getCell;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Yearly Rent Report");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        getRentData(getCell);

    }

    public void getRentData(String cell) {
        Call<List<Report>> call = apiInterface.getRentReport(cell);
        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                reportList = response.body();
                adapter = new RentReportAdapter(RentReportActivity.this, reportList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(RentReportActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
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