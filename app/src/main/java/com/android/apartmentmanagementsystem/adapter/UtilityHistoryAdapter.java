package com.android.apartmentmanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Utility;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UtilityHistoryAdapter extends RecyclerView.Adapter<UtilityHistoryAdapter.MyViewHolder> {

    private List<Utility> utilities;
    Context context;

    public UtilityHistoryAdapter(Context context, List<Utility> utilities) {
        this.context = context;
        this.utilities = utilities;
    }

    @Override
    public UtilityHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_history_item, parent, false);
        return new UtilityHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilityHistoryAdapter.MyViewHolder holder, int position) {
        holder.gas_bill.setText(utilities.get(position).getGas_bill_amount());
        holder.electricity_bill.setText(utilities.get(position).getElectricity_bill_amount());
        holder.month.setText(utilities.get(position).getRent_of_month());
        holder.trx_id.setText(utilities.get(position).getBkash_trx_id());
        holder.bkash_cell.setText(utilities.get(position).getBkash_cell());
        holder.date.setText(utilities.get(position).getPaying_date());
        holder.time.setText(utilities.get(position).getPaying_time());
    }

    @Override
    public int getItemCount() {
        return utilities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView gas_bill,electricity_bill,month, trx_id,bkash_cell,date,time;
        public MyViewHolder(View itemView) {
            super(itemView);
            gas_bill = itemView.findViewById(R.id.gas_amount_tv);
            electricity_bill = itemView.findViewById(R.id.electricity_amount_tv);
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
