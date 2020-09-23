package com.android.apartmentmanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Rent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RentHistoryAdapter extends RecyclerView.Adapter<RentHistoryAdapter.MyViewHolder>  {
    private List<Rent> rents;
    Context context;

    public RentHistoryAdapter(Context context, List<Rent> rents) {
        this.context = context;
        this.rents = rents;
    }

    @Override
    public RentHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_history_item, parent, false);
        return new RentHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentHistoryAdapter.MyViewHolder holder, int position) {
        holder.amount.setText("Tk "+ rents.get(position).getRent_amount());
        holder.month.setText(rents.get(position).getRent_of_month());
        holder.trx_id.setText(rents.get(position).getBkash_trx_id());
        holder.bkash_cell.setText(rents.get(position).getBkash_cell());
        holder.date.setText(rents.get(position).getPaying_date());
        holder.time.setText(rents.get(position).getPaying_time());
    }

    @Override
    public int getItemCount() {
        return rents.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView amount, month, trx_id,bkash_cell,date,time;
        public MyViewHolder(View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amount_tv);
            month = itemView.findViewById(R.id.month_tv);
            trx_id = itemView.findViewById(R.id.bkash_trx_id_tv);
            bkash_cell = itemView.findViewById(R.id.bkash_cell_tv);
            date = itemView.findViewById(R.id.date_tv);
            time = itemView.findViewById(R.id.time_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*Intent i = new Intent(context, EditorActivity.class);
            i.putExtra("id", contacts.get(getAdapterPosition()).getId());
            i.putExtra("name", contacts.get(getAdapterPosition()).getName());
            i.putExtra("email", contacts.get(getAdapterPosition()).getEmail());
            context.startActivity(i);*/
        }
    }
}
