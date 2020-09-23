package com.android.apartmentmanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Report;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UtilityReportAdapter extends RecyclerView.Adapter<UtilityReportAdapter.MyViewHolder> {

    private List<Report> reports;
    Context context;

    public UtilityReportAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @Override
    public UtilityReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_report_item, parent, false);
        return new UtilityReportAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilityReportAdapter.MyViewHolder holder, int position) {
        holder.month_tv.setText(reports.get(position).getRent_of_month());
        holder.gas_bill_tv.setText("Tk "+ reports.get(position).getGas_bill_amount());
        holder.electricity_bill_tv.setText("Tk "+ reports.get(position).getElectricity_bill_amount());
        holder.date_tv.setText(reports.get(position).getPaying_date());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView month_tv,gas_bill_tv,electricity_bill_tv,date_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            month_tv = itemView.findViewById(R.id.month_tv);
            gas_bill_tv = itemView.findViewById(R.id.gas_bill_tv);
            electricity_bill_tv = itemView.findViewById(R.id.electricity_bill_tv);
            date_tv = itemView.findViewById(R.id.date_tv);

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
