package com.example.kallz2u.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kallz2u.R;
import com.example.kallz2u.bean.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewGroupFragment extends Fragment {

    private EditText groupTitle;
    private View addGroupView;
    private String GroupType;
    private ImageButton imageButton89, finishBtn,imageButton97,imageButton98,imageButton99,imageButton100,imageButton101,imageButton102;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Group group;

    public AddNewGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addGroupView = inflater.inflate(R.layout.fragment_add_new_group,container,false);
        groupTitle = addGroupView.findViewById(R.id.CreateGroupName);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");
        group =new Group();
        finishBtn = addGroupView.findViewById(R.id.imageButton103);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = groupTitle.getText().toString();
                String groupType = GroupType;
                if (TextUtils.isEmpty(groupName)){
                    Toast.makeText(getActivity(),"Group Name is required!",Toast.LENGTH_SHORT).show();
                }else if(GroupType.isEmpty()){
                    Toast.makeText(getActivity(),"Group Type is required!",Toast.LENGTH_SHORT).show();
                }
                else{
                    addDataToFirebase(groupName,groupType);

                    Bundle bundle = new Bundle();
                    bundle.putString("GroupName",groupName);
                    getParentFragmentManager().setFragmentResult("GN",bundle);
                    AddNewGroupFragment addNewGroupFragment = new AddNewGroupFragment();
                    GroupMemberFragment groupMemberFragment = new GroupMemberFragment();
                    addNewGroupFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container,groupMemberFragment).commit();

                }
            }
        });

        imageButton97 = addGroupView.findViewById(R.id.imageButton97);
        imageButton98 = addGroupView.findViewById(R.id.imageButton98);
        imageButton99 = addGroupView.findViewById(R.id.imageButton99);
        imageButton100 = addGroupView.findViewById(R.id.imageButton100);
        imageButton101 = addGroupView.findViewById(R.id.imageButton101);
        imageButton102 = addGroupView.findViewById(R.id.imageButton102);
        imageButton97.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupType = "Personal network";
            }
        });
        imageButton98.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupType = "ORGs";
            }
        });
        imageButton99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupType = "Service group";
            }
        });
        imageButton100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupType = "Support group";
            }
        });
        imageButton101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupType = "Professional group";
            }
        });
        imageButton102.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupType = "Customize";
            }
        });



        imageButton89 = addGroupView.findViewById(R.id.imageButton89);//back button
        imageButton89.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupFragment groupFragment = new GroupFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,groupFragment).commit();
            }
        });

        return addGroupView;
    }

    private void addDataToFirebase(String groupName,String groupType){
        group.setGroupType(groupType);
        group.setGroupName(groupName);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(firebaseAuth.getUid()).child("groups").child(groupName).setValue(group);
                //Toast.makeText(getActivity(),"New Group Created",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(getActivity(),"Failed to create",Toast.LENGTH_SHORT).show();
            }
        });
    }
}