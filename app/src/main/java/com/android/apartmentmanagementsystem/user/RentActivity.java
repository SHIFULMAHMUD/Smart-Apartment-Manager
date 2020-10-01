package com.android.apartmentmanagementsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.adapter.Adapter;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Flat;
import com.android.apartmentmanagementsystem.FlatActivity;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.adapter.RecyclerViewAdapter;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class RentActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private List<Flat> flatList;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose Flat");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        getFlatData("","");
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }
    public void getFlatData(String flat, String floor) {
        Call<List<Flat>> call = apiInterface.getFlatDetails(flat,floor);
        call.enqueue(new Callback<List<Flat>>() {
            @Override
            public void onResponse(Call<List<Flat>> call, Response<List<Flat>> response) {
                flatList = response.body();
                adapter = new RecyclerViewAdapter(RentActivity.this, flatList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Flat>> call, Throwable t) {
                Toast.makeText(RentActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
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

