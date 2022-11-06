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
import android.widget.TextView;

import com.example.kallz2u.R;
import com.example.kallz2u.bean.Group;
import com.example.kallz2u.bean.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GroupDetailFragment extends Fragment {

    private View GroupDetailView;
    private RecyclerView myMemberList;
    DatabaseReference databaseReference,UsersRef;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    TextView textView;
    private String currentUserId;

    public GroupDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GroupDetailView=inflater.inflate(R.layout.fragment_group_detail,container,false);


        Bundle bundle = this.getArguments();
        String groupDetail = bundle.getString("GroupDetail");
        textView = GroupDetailView.findViewById(R.id.txtTitle);
        textView.setText(groupDetail);
        myMemberList = GroupDetailView.findViewById(R.id.groupDetailRecycleView);
        myMemberList.setLayoutManager(new LinearLayoutManager(GroupDetailView.getContext()));
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("GroupMember").child(firebaseAuth.getUid()).child("groups").child(groupDetail).child("members");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("GroupMember").child(firebaseAuth.getUid()).child("groups").child(groupDetail).child("members");



        return GroupDetailView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<User> options=
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(databaseReference,User.class)
                        .build();
        FirebaseRecyclerAdapter<User, GroupDetailViewHolder> adapter
                =new FirebaseRecyclerAdapter<User, GroupDetailViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GroupDetailViewHolder holder, int position, @NonNull User model) {
                String UserIDs = getRef(position).getKey();
                UsersRef.child(UserIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.Member.setText(model.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public GroupDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item,parent,false);
                GroupDetailViewHolder viewHolder = new GroupDetailViewHolder(view);
                return viewHolder;
            }
        };

        myMemberList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class GroupDetailViewHolder extends RecyclerView.ViewHolder{
        TextView Member;
        public GroupDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            Member = itemView.findViewById(R.id.txtMember);
        }
    }
}