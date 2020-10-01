package com.android.apartmentmanagementsystem.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.apartmentmanagementsystem.FlatActivity;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.guard.GuardHomeActivity;
import com.android.apartmentmanagementsystem.guard.GuardTaskActivity;
import com.android.apartmentmanagementsystem.model.Flat;
import com.android.apartmentmanagementsystem.model.Task;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardTaskAdapter extends RecyclerView.Adapter<GuardTaskAdapter.MyViewHolder> {

    private List<Task> tasks;
    Context context;

    public GuardTaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public GuardTaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guard_task_item, parent, false);
        return new GuardTaskAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuardTaskAdapter.MyViewHolder holder, int position) {
        holder.renter.setText(tasks.get(position).getRenter_name());
        holder.cell.setText(tasks.get(position).getCell());
        holder.flat.setText(tasks.get(position).getFlat_no());
        holder.floor.setText(tasks.get(position).getFloor_no());
        holder.task.setText(tasks.get(position).getTask());
        holder.date.setText(tasks.get(position).getDate());
        holder.time.setText(tasks.get(position).getTime());
        holder.status.setText(tasks.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView renter,cell,flat,floor,task,date,time,status;
        public MyViewHolder(View itemView) {
            super(itemView);
            renter = itemView.findViewById(R.id.renter_name_tv);
            cell = itemView.findViewById(R.id.renter_cell_tv);
            flat = itemView.findViewById(R.id.flat_no_tv);
            floor = itemView.findViewById(R.id.floor_no_tv);
            task = itemView.findViewById(R.id.task_tv);
            date = itemView.findViewById(R.id.date_tv);
            time = itemView.findViewById(R.id.time_tv);
            status = itemView.findViewById(R.id.status_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

                    final String[] taskList = {"Complete", "Pending"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Task Complete ?");
                    builder.setCancelable(false);
                    builder.setItems(taskList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            switch (position) {
                                case 0:
                                    String task= "Complete";
                                    submitRequest(tasks.get(getAdapterPosition()).getId(),task);
                                    break;
                                case 1:
                                    String tasktwo= "Pending";
                                    submitRequest(tasks.get(getAdapterPosition()).getId(),tasktwo);
                                    break;
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            dialog.dismiss();
                        }
                    });


                    AlertDialog accountTypeDialog = builder.create();

                    accountTypeDialog.show();
                }
        private void submitRequest(String id,String task) {

            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<Task> call = apiInterface.submitTask(id,task);
            call.enqueue(new Callback<Task>() {
                @Override
                public void onResponse(Call<Task> call, Response<Task> response) {

                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if (value.equals("success"))
                    {
                        Toasty.success(context, message, Toasty.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, GuardHomeActivity.class);
                        context.startActivity(intent);
                    }



                    else {
                        //loading.dismiss();
                        Toasty.error(context, message, Toasty.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Task> call, Throwable t) {

                    //loading.dismiss();
                    Toasty.error(context, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
                }
            });
        }
    }
}
