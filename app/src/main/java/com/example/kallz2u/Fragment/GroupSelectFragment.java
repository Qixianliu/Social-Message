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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallz2u.R;
import com.example.kallz2u.activites.FifthActivity;
import com.example.kallz2u.activites.FourthActivity;
import com.example.kallz2u.bean.Group;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GroupSelectFragment extends Fragment {

    private ImageView btnAddGroup;
    private View GroupsView;
    private RecyclerView myGroupList;
    DatabaseReference databaseReference,UsersRef;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    private String currentUserId;
    int isUrgent;
    private ImageButton imageButton80;


    public GroupSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GroupsView = inflater.inflate(R.layout.fragment_group_select, container, false);
        myGroupList = GroupsView.findViewById(R.id.groupRecycleView);
        myGroupList.setLayoutManager(new LinearLayoutManager(GroupsView.getContext()));
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(firebaseAuth.getUid()).child("groups");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(firebaseAuth.getUid()).child("groups");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();


        btnAddGroup = GroupsView.findViewById(R.id.imageView66);
        Bundle bundle = this.getArguments();
        isUrgent = bundle.getInt("isUrgent");

        initClick();

        imageButton80 = GroupsView.findViewById(R.id.imageButton80);//back button
        imageButton80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
            }
        });
        return GroupsView;
    }

    private void initClick() {
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewGroupFragment addNewGroupFragment = new AddNewGroupFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,addNewGroupFragment).commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //recycler view object
        FirebaseRecyclerOptions<Group> options=
                new FirebaseRecyclerOptions.Builder<Group>()
                        .setQuery(databaseReference,Group.class)
                        .build();

        FirebaseRecyclerAdapter<Group, GroupViewHolder> adapter
                =new FirebaseRecyclerAdapter<Group, GroupViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GroupViewHolder holder, int position, @NonNull Group model) {
                String UserIDs = getRef(position).getKey();
                UsersRef.child(UserIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        holder.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (isUrgent == 0){
                                    Intent intent=new Intent(getActivity(), FourthActivity.class);
                                    intent.putExtra("GroupName",model.getGroupName());
                                    startActivity(intent);}
                                else{
                                    Intent intent=new Intent(getActivity(), FifthActivity.class);
                                    intent.putExtra("GroupName",model.getGroupName());
                                    startActivity(intent);}
                                }
                        });
                        holder.groupName.setText(model.getGroupName());
                        holder.groupType.setText(model.getGroupType());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item_layout,parent,false);
                GroupViewHolder viewHolder = new GroupViewHolder(view);
                return viewHolder;
            }
        };

        myGroupList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView groupName,groupType;
        View view;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupType = itemView.findViewById(R.id.txtGroupType);
            groupName = itemView.findViewById(R.id.GroupName);
            view = itemView;
        }
    }

}