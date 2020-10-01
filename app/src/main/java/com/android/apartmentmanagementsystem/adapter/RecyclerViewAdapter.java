package com.android.apartmentmanagementsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.FlatActivity;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Flat;
import com.android.apartmentmanagementsystem.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private List<Flat> flats;
    Context context;
    public RecyclerViewAdapter(Context context, List<Flat> flats) {
        this.context = context;
        this.flats = flats;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.apartment);
        holder.flat.setText(flats.get(position).getFlat_no());
        holder.floor.setText(flats.get(position).getFloor_no());
    }

    @Override
    public int getItemCount() { return flats.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView flat, floor;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.flat_imageView);
            flat = itemView.findViewById(R.id.flat_textView);
            floor = itemView.findViewById(R.id.floor_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, FlatActivity.class);
            i.putExtra("flat", flats.get(getAdapterPosition()).getFlat_no());
            i.putExtra("floor", flats.get(getAdapterPosition()).getFloor_no());
            i.putExtra("details", flats.get(getAdapterPosition()).getFlat_details());
            i.putExtra("price", flats.get(getAdapterPosition()).getFlat_price());
            i.putExtra("one", flats.get(getAdapterPosition()).getImageone());
            i.putExtra("two", flats.get(getAdapterPosition()).getImagetwo());
            i.putExtra("three", flats.get(getAdapterPosition()).getImagethree());
            context.startActivity(i);
        }
    }
}
