package com.example.kallz2u.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.kallz2u.Models.ModelUsers;
import com.example.kallz2u.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {
    Context context;
    FirebaseAuth firebaseAuth;
    String uid;

    public AdapterUsers(Context context, List<ModelUsers> list) {
        this.context = context;
        this.list = list;
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
    }

    List<ModelUsers> list;


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout,parent,false);
        return new MyHolder(view);
    }

    public int getItemCount(){
        return list.size();
    }

    public void onBindViewHolder(@NonNull MyHolder holder,final int position){
        final String hisuid = list.get(position).getUid();
        String username = list.get(position).getName();
        String useremail = list.get(position).getEmail();
        holder.name.setText(username);
        holder.email.setText(useremail);
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, email;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtContactName);
            email = itemView.findViewById(R.id.txtContactEmail);
        }
    }
}
