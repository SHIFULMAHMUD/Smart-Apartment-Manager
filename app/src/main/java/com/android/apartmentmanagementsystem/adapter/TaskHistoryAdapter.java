package com.android.apartmentmanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.model.Task;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskHistoryAdapter extends RecyclerView.Adapter<TaskHistoryAdapter.MyViewHolder> {

    private List<Task> tasks;
    Context context;

    public TaskHistoryAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public TaskHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_history_item, parent, false);
        return new TaskHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHistoryAdapter.MyViewHolder holder, int position) {
        holder.guard.setText(tasks.get(position).getGuard_name());
        holder.task.setText(tasks.get(position).getTask());
        holder.date.setText(tasks.get(position).getDate());
        holder.time.setText(tasks.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView guard,task,date,time;
        public MyViewHolder(View itemView) {
            super(itemView);
            guard = itemView.findViewById(R.id.guard_name_tv);
            task = itemView.findViewById(R.id.task_tv);
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
