package com.android.apartmentmanagementsystem.user.history;

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
import com.android.apartmentmanagementsystem.adapter.Adapter;
import com.android.apartmentmanagementsystem.adapter.GuestHistoryAdapter;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Guest;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.util.List;

public class GuestHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GuestHistoryAdapter adapter;
    private List<Guest> guestList;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    String getCell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Guest History");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        getGuestData(getCell);

    }
    public void getGuestData(String cell) {
        Call<List<Guest>> call = apiInterface.getGuest(cell);
        call.enqueue(new Callback<List<Guest>>() {
            @Override
            public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                guestList = response.body();
                adapter = new GuestHistoryAdapter(GuestHistoryActivity.this, guestList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Guest>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(GuestHistoryActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
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