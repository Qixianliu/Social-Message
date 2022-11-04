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
    private MAdapter adapter;
    private List<UserBean> mList;

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GroupsView = inflater.inflate(R.layout.fragment_group, container, false);
        myGroupList = GroupsView.findViewById(R.id.groupRecycleView);
        recyclerView = GroupsView.findViewById(R.id.groupRecycleView);
        myGroupList.setLayoutManager(new LinearLayoutManager(GroupsView.getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Groups").child("email");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Groups");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();


        btnAddGroup = GroupsView.findViewById(R.id.imageView66);
        initClick();
        mList = new ArrayList<>();
        adapter = new MAdapter(R.layout.contact_item_layout);
        LinearLayoutManager l = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);

        getData();
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                test(((UserBean)adapter.getData().get(position)).getToken());
//            }
//        });
        /*GroupsView.findViewById(R.id.imageButton133).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), COVID19Activity.class));
            }
        });*/
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
    private void test(String token) {
        Log.d("============",token);
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        Request<JSONObject> request = null;
        String data = "{\n" +
                "    \"to\": \"" +token+"\",\n"+
                "    \"notification\": {\n" +
                "        \"body\": \"我是测试message\",\n" +
                "        \"title\": \"test\",\n" +
                "        \"image\": \"https://timgsa.baidu.com/timg?image&qua..\"\n" +
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
                Log.e("=============发送结果",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                        mList.add(new UserBean(queryDocumentSnapshots.getDocuments().get(i).getData().get("email").toString(),
                                queryDocumentSnapshots.getDocuments().get(i).getData().get("pwd").toString(),
                                queryDocumentSnapshots.getDocuments().get(i).getData().get("token").toString()));
                    }
                    adapter.setNewData(mList);

                })
                .addOnFailureListener(e -> {
                    Log.e("==========",e.getMessage());
                });
    }
    private class MAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {


        public MAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, UserBean item) {
            helper.setText(R.id.txtContactName,item.getPwd())
                    .setText(R.id.txtContactEmail,item.getEmail());
        }
    }
}