package com.example.kallz2u.activites;

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
import com.example.kallz2u.bean.Groups;
import com.example.kallz2u.databinding.ActivityAddNewGroupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class AddNewGroupActivity extends AppCompatActivity {
    private EditText groupTitle;
    private ImageButton imageButton89, finishBtn;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private ActivityAddNewGroupBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setListeners();
        firebaseAuth = FirebaseAuth.getInstance();
        binding.imageButton103.setOnClickListener(done ->{
            String groupName = binding.CreateGroupName.getText().toString().trim();
            if (groupName.isEmpty()){
                binding.CreateGroupName.setError("Filed is required");
            }else{
                Bundle bundle = new Bundle();
                bundle.putString("GroupName",groupName);
                GroupMemberFragment memberFragment=new GroupMemberFragment();
            }
        });
    }

    public void createNewGroup(String groupId,String groupName,String adminId){
        Groups group = new Groups(groupName,adminId);
        mDatabase.child("Groups").child(groupId).setValue(group);
    }

    private void setListeners(){
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        imageButton89 = findViewById(R.id.imageButton89);
        imageButton89.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        groupTitle = findViewById(R.id.CreateGroupName);
        finishBtn = findViewById(R.id.imageButton103);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreatingGroup();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
    }

    private void startCreatingGroup() {
        String groupName = groupTitle.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)){
            Toast.makeText(this,"Please enter group name!",Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private void createGroup(String g_timestamp,String groupTitle){
        //setup group info
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("groupId",""+g_timestamp);
        hashMap.put("groupName",""+groupTitle);
        hashMap.put("createBy",""+ firebaseAuth.getUid());

        //create group
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Groups");
        databaseReference.child(g_timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddNewGroupActivity.this, "Group created successfully", Toast.LENGTH_SHORT).show();
                }
                 })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewGroupActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            getActionBar().setSubtitle(user.getEmail());
        }
    }
    */
}
