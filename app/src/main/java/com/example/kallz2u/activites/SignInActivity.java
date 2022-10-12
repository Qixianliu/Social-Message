package com.example.kallz2u.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.bean.User;
import com.example.kallz2u.databinding.ActivitySigninBinding;
import com.example.kallz2u.utilities.PreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class SignInActivity extends AppCompatActivity {
    private ActivitySigninBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private int signIn = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());
        // Go to MainFragment
        Log.e("=======","success");
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }



    private void setListeners() {
        if (PreferencesUtils.getBoolean(SignInActivity.this,"ck")){
            if (!TextUtils.isEmpty(PreferencesUtils.getString(SignInActivity.this,"name"))){
                binding.fieldEmail.setText(PreferencesUtils.getString(SignInActivity.this,"name"));
                binding.fieldPassword.setText(PreferencesUtils.getString(SignInActivity.this,"pwd"));
            }
        }
        //check box
        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    PreferencesUtils.putBoolean(SignInActivity.this,"ck",true);
                }else {
                    PreferencesUtils.putBoolean(SignInActivity.this,"ck",false);
                }
            }
        });
        binding.buttonSignIn.setOnClickListener(view -> {
            if (signIn==0){
                signIn();
            }else {
                signUp();
            }
        });
        binding.buttonSignUp.setOnClickListener(view -> {
            if (signIn==0){
                signIn=1;
                binding.buttonSignIn.setText("Sign Up");
                binding.checkBox.setVisibility(View.GONE);
                binding.buttonSignUp.setText("To Sign In");
            }else {
                signIn=0;
                binding.buttonSignIn.setText("Sign In");
                binding.checkBox.setVisibility(View.VISIBLE);
                binding.buttonSignUp.setText("To Sign Up");
            }
        });
    }

    private void signUp() {
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(binding.fieldEmail.getText().toString().trim(),
                        binding.fieldPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("=========", "createUser:onComplete:" + new Gson().toJson(task));
                        Log.d("=======", "createUser:onComplete:" + task.isSuccessful());
                        binding.progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            String username = usernameFromEmail(task.getResult().getUser().getEmail());
                            // Write new user
                            writeNewUser(task.getResult().getUser().getUid(), username, task.getResult().getUser().getEmail());
                            Toast.makeText(SignInActivity.this, "Sign Up Success",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(binding.fieldEmail.getText().toString().trim(),
                        binding.fieldPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("=========", "signIn:onComplete:" + new Gson().toJson(task));
                        binding.progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            PreferencesUtils.putString(SignInActivity.this,"name",binding.fieldEmail.getText().toString().trim());
                            PreferencesUtils.putString(SignInActivity.this,"pwd",binding.fieldPassword.getText().toString().trim());
//                            onAuthSuccess(task.getResult().getUser());
                            startActivity(new Intent(SignInActivity.this,DashBoardActivity.class));
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}


