package com.example.kallz2u.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.kallz2u.R;
import com.example.kallz2u.bean.Message;
import com.example.kallz2u.bean.UserBean;
import com.example.kallz2u.utilities.PostJsonRequest2;
import com.example.kallz2u.utilities.PreferencesUtils;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UrgentEventActivity extends AppCompatActivity {
    private List<UserBean> mList;
    private List<String> sList;
    private Spinner sp;
    private ImageButton imageButton62;
    private EditText editTextTextPersonName5;
    private int position=0;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_event);
        textView=findViewById(R.id.txtWhat);

        String what=getIntent().getStringExtra("What");

        textView.setText(what);
        initView();
    }
    private void initView() {
        sp = findViewById(R.id.spinner8);
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5);
        imageButton62 = findViewById(R.id.imageButton62);
        sList = new ArrayList<>();
        mList = new ArrayList<>();
        getData();
        imageButton62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("========","click");
                test(getIntent().getStringExtra("What")+"##"+new Date().toLocaleString()+"##"+editTextTextPersonName5.getText().toString()+"##"+mList.get(position).getToken()+"##1",mList.get(position).getToken());
//                Message m = new Message(getIntent().getStringExtra("What")+"##"+new Date().toLocaleString()+"##"+editTextTextPersonName5.getText().toString()+"##"+mList.get(position).getToken()+"##1",mList.get(position).getEmail(),0);
//                boolean re = m.save();
//                if (re){
//                    Toast.makeText(UrgentEventActivity.this,"send success",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(UrgentEventActivity.this,"send error",Toast.LENGTH_SHORT).show();
//                }
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void test(String msg,String token) {
        Log.d("============",token);
        RequestQueue mQueue = Volley.newRequestQueue(this);
        Request<JSONObject> request = null;
        String data = "{\n" +
                "    \"to\": \"" +token+"\",\n"+
                "    \"notification\": {\n" +
                "        \"body\": \"" + PreferencesUtils.getString(this,"name") +"\",\n"+
                "        \"title\": \"" +msg+"\",\n"+
                "        \"image\": \"https://img0.baidu.com/it/u=2371305810,3587591415&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281\"\n" +
                "    },\n" +
                "    \"data\": {\n" +
                "        \"push-link\": \"xflag://notification/detail?id=93\"\n" +
                "    }\n" +
                "}";
        Log.d("=============",data);
        PostJsonRequest2 request2 = new PostJsonRequest2("https://fcm.googleapis.com/fcm/send",
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UrgentEventActivity.this,"send success",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("=============发送结果",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UrgentEventActivity.this,"send error",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("=============///",error.toString());
            }
        });
        request = mQueue.add(request2);
    }

    private void getData(){
        CollectionReference loansRef = FirebaseFirestore.getInstance().collection("user");
        loansRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        sList.add(queryDocumentSnapshots.getDocuments().get(i).getData().get("email").toString());
                        mList.add(new UserBean(queryDocumentSnapshots.getDocuments().get(i).getData().get("email").toString(),
                                queryDocumentSnapshots.getDocuments().get(i).getData().get("pwd").toString(),
                                queryDocumentSnapshots.getDocuments().get(i).getData().get("token").toString()));
                    }
                    sp.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sList));

                })
                .addOnFailureListener(e -> {
                    Log.e("==========",e.getMessage());
                });
    }
}