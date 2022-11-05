package com.example.kallz2u.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.Fragment.GroupMemberFragment;
import com.example.kallz2u.R;
import com.example.kallz2u.bean.Group;
import com.example.kallz2u.databinding.ActivityAddNewGroupBinding;
import com.example.kallz2u.utilities.PreferencesUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewGroupActivity extends AppCompatActivity {
    private EditText groupTitle;
    private String GroupType;
    private ImageButton imageButton89, finishBtn,imageButton97,imageButton98,imageButton99,imageButton100,imageButton101,imageButton102;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        groupTitle = findViewById(R.id.CreateGroupName);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");
        group =new Group();
        finishBtn = findViewById(R.id.imageButton103);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = groupTitle.getText().toString();
                String groupType = GroupType;
                if (TextUtils.isEmpty(groupName)){
                    Toast.makeText(AddNewGroupActivity.this,"Group Name is required!",Toast.LENGTH_SHORT).show();
                }else if(GroupType.isEmpty()){
                    Toast.makeText(AddNewGroupActivity.this,"Group Type is required!",Toast.LENGTH_SHORT).show();
                }
                else{
                    addDataToFirebase(groupName,groupType);
                    String groupN = groupName;
                    Bundle bundle = new Bundle();
                    bundle.putString("GroupName",groupName);
                    GroupMemberFragment memberFragment = new GroupMemberFragment();
                    memberFragment.setArguments(bundle);
                    //startActivity(new Intent(AddNewGroupActivity.this,GroupMemberFragment.class));
                }
            }
        });

        //group type buttons
        imageButton97 = findViewById(R.id.imageButton97);
        imageButton98 = findViewById(R.id.imageButton98);
        imageButton99 = findViewById(R.id.imageButton99);
        imageButton100 = findViewById(R.id.imageButton100);
        imageButton101 = findViewById(R.id.imageButton101);
        imageButton102 = findViewById(R.id.imageButton102);
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



        imageButton89 = findViewById(R.id.imageButton89);//back button
        imageButton89.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addDataToFirebase(String groupName,String groupType){
        group.setGroupType(groupType);
        group.setGroupName(groupName);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(firebaseAuth.getUid()).child("groups").child(groupName).setValue(group);
                Toast.makeText(AddNewGroupActivity.this,"New Group Created",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddNewGroupActivity.this,"Failed to create",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
