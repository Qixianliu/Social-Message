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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kallz2u.bean.User;
import com.example.kallz2u.bean.UserBean;
import com.example.kallz2u.databinding.ActivitySigninBinding;
import com.example.kallz2u.firebase.MessagingService;
import com.example.kallz2u.utilities.PostJsonRequest2;
import com.example.kallz2u.utilities.PreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    private ActivitySigninBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private int signIn = 0;
    boolean ck = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        startService(new Intent(SignInActivity.this, MessagingService.class));
//        test();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void test() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        Request<JSONObject> request = null;
        String data = "{\n" +
                "    \"to\": \"dio4-KmAThqRNMCTIt9Kh4:APA91bHQaGwgtiA2caZkdHzRfkr2-ElCKZu2a0MMpUr_hkoyuFMbZKPPnRUn9-aM9KK3zShl1IxutDLjO_4ReMsVKqBTwzI3Slz8a9uEizjYfv6gctluXsgO_avHdm1987zq3YLYpw4q\",\n" +
                "    \"notification\": {\n" +
                "        \"body\": \"我是测试message\",\n" +
                "        \"title\": \"test\",\n" +
                "        \"image\": \"https://timgsa.baidu.com/timg?image&qua..\"\n" +
                "    },\n" +
                "    \"data\": {\n" +
                "        \"push-link\": \"xflag://notification/detail?id=93\"\n" +
                "    }\n" +
                "}";
        PostJsonRequest2 request2 = new PostJsonRequest2("https://fcm.googleapis.com/fcm/send",
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("=============",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("=============///",error.toString());
            }
        });
        request = mQueue.add(request2);
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
                        Log.d("=========", "signIn:onComplete:" + new Gson().toJson(task.getResult().getUser().getIdToken(false)));
                        binding.progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                        @Override
                                        public void onComplete(@NonNull Task<String> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w("===========", "Fetching FCM registration token failed", task.getException());
                                                return;
                                            }

                                            // Get new FCM registration token
                                            String token = task.getResult();
                                            checkUser(binding.fieldEmail.getText().toString().trim(),binding.fieldPassword.getText().toString().trim(),
                                                    token);
                                            Log.e("============",token);
                                        }
                                    });
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void checkUser(String email,String pwd,String token){
        CollectionReference loansRef = FirebaseFirestore.getInstance().collection("user");
        loansRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        if (queryDocumentSnapshots.getDocuments().get(i).getData().get("email").toString().equals(email)){
                            ck = true;
                        }
                    }
                    Log.e("==========ckresult",ck+"");
                    if (!ck){
                        sendData(email,pwd,token);
                    }
                    PreferencesUtils.putString(SignInActivity.this,"name",binding.fieldEmail.getText().toString().trim());
                    PreferencesUtils.putString(SignInActivity.this,"pwd",binding.fieldPassword.getText().toString().trim());
//                            onAuthSuccess(task.getResult().getUser());
                    startActivity(new Intent(SignInActivity.this,UserListActivity.class));

                })
                .addOnFailureListener(e -> {
                    Log.e("==========",e.getMessage());
                });
    }
    private void sendData(String name,String pwd,String token){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        final Map<String, Object> postMap = new HashMap<>();
        postMap.put("email", name);
        postMap.put("pwd", pwd);
        postMap.put("token", token);
        database.collection("user")
                .add(postMap)
                .addOnSuccessListener(documentReference -> {
                    Log.d("========","send success");
                })
                .addOnFailureListener(exception -> {
                    Log.d("ERROR", exception.getMessage());
                    Log.d("========", exception.getMessage());
                });
    }


    private String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}


