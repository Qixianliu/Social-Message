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
import com.example.kallz2u.activites.MymessageActivity;
import com.example.kallz2u.bean.Group;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupFragment extends Fragment {

    private ImageView btnAddGroup;
    private View GroupsView;
    private RecyclerView myGroupList;
    DatabaseReference databaseReference,UsersRef;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    private String currentUserId;
    private ImageButton imageButton88;

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GroupsView = inflater.inflate(R.layout.fragment_group, container, false);
        myGroupList = GroupsView.findViewById(R.id.groupRecycleView);
        myGroupList.setLayoutManager(new LinearLayoutManager(GroupsView.getContext()));
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(firebaseAuth.getUid()).child("groups");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(firebaseAuth.getUid()).child("groups");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        imageButton88 = GroupsView.findViewById(R.id.imageButton88);
        imageButton88.setOnClickListener(view -> startActivity(new Intent(getActivity(), MymessageActivity.class)));
        btnAddGroup = GroupsView.findViewById(R.id.imageView66);

        initClick();
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

        FirebaseRecyclerAdapter<Group,GroupViewHolder> adapter
                =new FirebaseRecyclerAdapter<Group, GroupViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GroupViewHolder holder, int position, @NonNull Group model) {
                String UserIDs = getRef(position).getKey();
                UsersRef.child(UserIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.groupName.setText(model.getGroupName());
                        holder.groupType.setText(model.getGroupType());
                        holder.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle result = new Bundle();
                                result.putString("GroupDetail",model.getGroupName());
                                GroupFragment groupFragment = new GroupFragment();
                                GroupDetailFragment groupDetailFragment = new GroupDetailFragment();
                                groupDetailFragment.setArguments(result);
                                getFragmentManager().beginTransaction().replace(R.id.container,groupDetailFragment).commit();
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