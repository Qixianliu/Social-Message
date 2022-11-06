package com.example.kallz2u.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kallz2u.R;
import com.example.kallz2u.bean.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GroupMemberFragment extends Fragment {

    private View ContactsView;
    private RecyclerView myContactList;
    private DatabaseReference databaseReference, UsersRef,database2;
    private FirebaseAuth firebaseAuth;
    private String currentUserId,groupName;
    FirebaseDatabase firebaseDatabase;
    private Button button;
    public GroupMemberFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ContactsView = inflater.inflate(R.layout.fragment_group_member,container,false);

        myContactList = ContactsView.findViewById(R.id.recyclerViewContact);
        myContactList.setLayoutManager(new LinearLayoutManager(ContactsView.getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");

        button = ContactsView.findViewById(R.id.floatButton);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        //get group name
        getParentFragmentManager().setFragmentResultListener("GN", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                groupName = result.getString("GroupName");
            }
        });
        return ContactsView;
    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(databaseReference,User.class)
                        .build();

        FirebaseRecyclerAdapter<User, GroupMemberViewHolder> adapter
                = new FirebaseRecyclerAdapter<User, GroupMemberViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GroupMemberViewHolder holder, int position, @NonNull User model) {
                String userIDs = getRef(position).getKey();
                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.username.setText(model.getUsername());
                        holder.email.setText(model.getEmail());
                        holder.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(),"Contact Added",Toast.LENGTH_SHORT).show();
                                database2=FirebaseDatabase.getInstance().getReference().child("GroupMember");
                                database2.child(firebaseAuth.getUid()).child("groups").child(groupName).child("members").child(model.getUsername()).child("email").setValue(model.getEmail());
                                holder.view.setVisibility(View.GONE);//click to hide user
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public GroupMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout,parent,false);
                GroupMemberViewHolder viewHolder = new GroupMemberViewHolder(view);
                return viewHolder;
            }
        };

        myContactList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class GroupMemberViewHolder extends RecyclerView.ViewHolder{

        TextView username, email;
        View view;

        public GroupMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txtContactName);
            email = itemView.findViewById(R.id.txtContactEmail);
            view=itemView;
        }
    }
}