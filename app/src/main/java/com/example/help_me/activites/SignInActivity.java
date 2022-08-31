package com.example.help_me.activites;

import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignInActivity {




    private void setListeners() {
     //   binding.textCreateNewAccount.setOnClickListener(v ->
     //           startActivity(new Intent(getApplicationContext(), SignInActivity.class)));
    }



    private void addDataToFirestore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> data = new HashMap<>();
        data.put("first_name", "Oris");
        data.put("last_name","Liu");
        database.collection("user")
                .add(data)
                .addOnSuccessListener(documentReference -> {
         //           Toast.makeText(getApplicationContext(),"Data Inserted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception ->  {
         //           Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}


