package shiful.android.apartmentmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shiful.android.apartmentmanagementsystem.model.Contacts;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Contacts> contacts;
    Context context;

    public Adapter(Context context, List<Contacts> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(contacts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
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