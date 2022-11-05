package com.example.kallz2u.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kallz2u.R;
import com.example.kallz2u.activites.ContactItemInterface;
import com.example.kallz2u.bean.GroupMemberModel;
import com.example.kallz2u.bean.User;
import com.example.kallz2u.databinding.FragmentGroupMemberBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GroupMemberFragment extends Fragment implements ContactItemInterface{

    private View ContactsView;
    private RecyclerView myContactList;
    private DatabaseReference databaseReference, UsersRef;
    private FirebaseAuth firebaseAuth;
    private String currentUserId,groupName;
    ArrayList<User> selectedContacts;

    private ContactItemInterface contactItemInterface;

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

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        selectedContacts=new ArrayList<>();

        //get group name from AddNewGroupActivity
        Bundle bundle=getArguments();
        if (bundle!=null){
            groupName=bundle.getString("GroupName");
        }
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
            protected void onBindViewHolder(@NonNull GroupMemberFragment.GroupMemberViewHolder holder, int position, @NonNull User model) {
                String userIDs = getRef(position).getKey();
                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
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
            public GroupMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout,parent,false);
                GroupMemberViewHolder viewHolder = new GroupMemberViewHolder(view);
                return viewHolder;
            }
        };

        myContactList.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onContactClick(User user, int position, boolean isSelected) {
        if (isSelected){
            selectedContacts.remove(user);

        }else{

        }
    }

    public static class GroupMemberViewHolder extends RecyclerView.ViewHolder{

        TextView username, email;

        public GroupMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txtContactName);
            email = itemView.findViewById(R.id.txtContactEmail);
        }
    }

    private void addMember(){
        for (User user : selectedContacts){
        GroupMemberModel groupMemberModel = new GroupMemberModel();
        groupMemberModel.id = user.getuID();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(firebaseAuth.getUid())
                        .child("groups")
                        .child(groupName)
                        .child("Members")
                        .child(user.getuID())
                        .setValue(groupMemberModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }

    }
}