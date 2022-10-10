package com.example.kallz2u.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.widget.Toast;

import com.example.kallz2u.R;
import com.example.kallz2u.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners(){
        binding.buttonToSignIn.setOnClickListener(v -> onBackPressed());
        binding.buttonSignUp.setOnClickListener(v -> {
            if(isValidSignUpDetails()){
                signUp();
            }
        });

    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void signUp(){

    }

    private Boolean isValidSignUpDetails(){
        if(encodedImage == null){
            showToast("Select profile image");
            return false;
        }else if(binding.inputName.getText().toString().trim().isEmpty()){
            showToast("Enter name");
            return false;
        }
        else if(binding.inputEmail.getText().toString().trim().isEmpty()){
            showToast("Enter Email");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            showToast("Enter valid email");
            return false;
        }
        else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return false;
        }
        else {return true;
        }
    }
}