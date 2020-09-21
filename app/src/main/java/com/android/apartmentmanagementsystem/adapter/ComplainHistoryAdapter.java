package com.android.apartmentmanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Complain;
import com.android.apartmentmanagementsystem.model.Contacts;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComplainHistoryAdapter extends RecyclerView.Adapter<ComplainHistoryAdapter.MyViewHolder> {

    private List<Complain> complains;
    Context context;

    public ComplainHistoryAdapter(Context context, List<Complain> complains) {
        this.context = context;
        this.complains = complains;
    }

    @Override
    public ComplainHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_history_item, parent, false);
        return new ComplainHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainHistoryAdapter.MyViewHolder holder, int position) {
        holder.complain.setText(complains.get(position).getComplain());
        holder.date.setText(complains.get(position).getDate());
        holder.time.setText(complains.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return complains.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView complain,date,time;
        public MyViewHolder(View itemView) {
            super(itemView);
            complain = itemView.findViewById(R.id.complain_tv);
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
