package com.android.apartmentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class RentActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {
    RecyclerView recyclerView;
    ArrayList arrayList;

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
        arrayList = new ArrayList();
        arrayList.add(new DataModel("Flat 1", R.drawable.apartment, "#3E51B1"));
        arrayList.add(new DataModel("Flat 2", R.drawable.apartment, "#3E51B1"));
        arrayList.add(new DataModel("Flat 3", R.drawable.apartment, "#3E51B1"));
        arrayList.add(new DataModel("Flat 4", R.drawable.apartment, "#3E51B1"));
        arrayList.add(new DataModel("Flat 5", R.drawable.apartment, "#3E51B1"));
        arrayList.add(new DataModel("Flat 6", R.drawable.apartment, "#3E51B1"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        /*AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);*/


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

    }
    @Override
    public void onItemClick(DataModel item) {
        Intent intent=new Intent(RentActivity.this,FlatActivity.class);
        startActivity(intent);
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

