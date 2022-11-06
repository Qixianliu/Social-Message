
package com.example.kallz2u.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kallz2u.R;
import com.example.kallz2u.activites.DashBoardActivity;
import com.example.kallz2u.activites.MymessageActivity;
import com.example.kallz2u.bean.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ContactFragment extends Fragment {

    private View ContactsView;
    private RecyclerView myContactsList;
    private DatabaseReference databaseReference, UsersRef;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private ImageButton imageButton84;

    public ContactFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ContactsView = inflater.inflate(R.layout.fragment_contact,container,false);

        myContactsList = ContactsView.findViewById(R.id.usersRecyclerView);
        myContactsList.setLayoutManager(new LinearLayoutManager(ContactsView.getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        imageButton84 = ContactsView.findViewById(R.id.imageButton130);
        imageButton84.setOnClickListener(view -> startActivity(new Intent(getActivity(), MymessageActivity.class)));
        // Inflate the layout for this fragment
        return ContactsView;
    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(databaseReference,User.class)
                        .build();

        FirebaseRecyclerAdapter<User,ContactsViewHolder> adapter
                = new FirebaseRecyclerAdapter<User, ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ContactsViewHolder holder, int position, @NonNull User model) {
                String userIDs = getRef(position).getKey();
                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(),"You clicked a contact",Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.username.setText(model.getUsername());
                        holder.email.setText(model.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout,parent,false);
                ContactsViewHolder viewHolder = new ContactsViewHolder(view);
                return viewHolder;
            }
        };

        myContactsList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{

        TextView username, email;
        View view;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txtContactName);
            email = itemView.findViewById(R.id.txtContactEmail);
            view = itemView;
        }
    }
}