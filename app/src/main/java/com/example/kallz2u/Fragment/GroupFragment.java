package com.example.kallz2u.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kallz2u.R;
import com.example.kallz2u.activites.AddNewGroupActivity;
import com.example.kallz2u.activites.COVID19Activity;
import com.example.kallz2u.bean.Group;
import com.example.kallz2u.bean.UserBean;
import com.example.kallz2u.utilities.PostJsonRequest2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends Fragment {

    private ImageView btnAddGroup;
    private View GroupsView;
    private RecyclerView myGroupList;
    DatabaseReference databaseReference,UsersRef;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    private String currentUserId;

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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getUid()).child("groups");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getUid()).child("groups");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();


        btnAddGroup = GroupsView.findViewById(R.id.imageView66);

        initClick();
        return GroupsView;
    }

    private void initClick() {
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddNewGroupActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
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
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupType = itemView.findViewById(R.id.txtGroupType);
            groupName = itemView.findViewById(R.id.GroupName);
        }
    }
}