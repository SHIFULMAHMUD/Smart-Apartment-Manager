package com.android.apartmentmanagementsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Guest;
import com.android.apartmentmanagementsystem.user.GuestActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuestHistoryAdapter extends RecyclerView.Adapter<GuestHistoryAdapter.MyViewHolder> {

    private List<Guest> guests;
    Context context;

    public GuestHistoryAdapter(Context context, List<Guest> guests) {
        this.context = context;
        this.guests = guests;
    }

    @Override
    public GuestHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_history_item, parent, false);
        return new GuestHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestHistoryAdapter.MyViewHolder holder, int position) {
        holder.name.setText(guests.get(position).getGuest_name());
        holder.cell.setText(guests.get(position).getGuest_cell());
        holder.total.setText(guests.get(position).getTotal_guest());
        holder.purpose.setText(guests.get(position).getPurpose());
        holder.qrcode.setText(guests.get(position).getQr_code());
        holder.date.setText(guests.get(position).getDate());
        holder.time.setText(guests.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,cell,total,purpose,qrcode,date,time;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.guest_name_tv);
            cell = itemView.findViewById(R.id.guest_cell_tv);
            total = itemView.findViewById(R.id.total_guest_tv);
            purpose = itemView.findViewById(R.id.purpose_tv);
            qrcode = itemView.findViewById(R.id.qrcode_tv);
            date = itemView.findViewById(R.id.date_tv);
            time = itemView.findViewById(R.id.time_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*Intent i = new Intent(context, GuestActivity.class);
            i.putExtra("name", guests.get(getAdapterPosition()).getGuest_name());
            i.putExtra("cell", guests.get(getAdapterPosition()).getGuest_cell());
            i.putExtra("total", guests.get(getAdapterPosition()).getTotal_guest());
            i.putExtra("purpose", guests.get(getAdapterPosition()).getPurpose());
            context.startActivity(i);*/
        }
    }
}

