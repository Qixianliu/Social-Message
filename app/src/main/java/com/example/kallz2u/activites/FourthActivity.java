package com.example.kallz2u.activites;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kallz2u.R;
import com.example.kallz2u.bean.GroupBean;
import com.example.kallz2u.bean.UserBean;
import com.example.kallz2u.databinding.ActivityFourthBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class FourthActivity extends AppCompatActivity {
    private ActivityFourthBinding binding;
    private List<UserBean> mList = new ArrayList<>();
    String nonWhat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFourthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();
        binding.imageButton.setOnClickListener(view -> onBackPressed());
        binding.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Need a chat";
                chooseGroup(nonWhat);
//                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
//                intent.putExtra("nonWhat",""+nonWhat);
//                startActivity(intent);
            }
        });
        binding.imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Child Minding";
                chooseGroup(nonWhat);
//                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
//                intent.putExtra("nonWhat",""+nonWhat);
//                startActivity(intent);
            }
        });
        binding.imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Gardening";
                chooseGroup(nonWhat);
//                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
//                intent.putExtra("nonWhat",""+nonWhat);
//                startActivity(intent);
            }
        });
        binding.imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Go to Doctor";
                chooseGroup(nonWhat);
//                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
//                intent.putExtra("nonWhat",""+nonWhat);
//                startActivity(intent);
            }
        });
        binding.imageButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Walk the Dog";
                chooseGroup(nonWhat);
//                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
//                intent.putExtra("nonWhat",""+nonWhat);
//                startActivity(intent);
            }
        });
        binding.imageButton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Car Breakdown";
                chooseGroup(nonWhat);
//                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
//                intent.putExtra("nonWhat",""+nonWhat);
//                startActivity(intent);
            }
        });
    }

    private void chooseGroup(String nonWhat){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        RecyclerView re = new RecyclerView(this);
        Dialog d = b.create();
        GroupAdapter adapter = new GroupAdapter(R.layout.group_item_layoutnew);
        LinearLayoutManager l = new LinearLayoutManager(this);
        re.setLayoutManager(l);
        re.setAdapter(adapter);
        List<GroupBean> gList = new ArrayList<>();
        gList.add(new GroupBean("user",mList));
        adapter.setNewData(gList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
                intent.putExtra("nonWhat",""+nonWhat);
                startActivity(intent);
                d.dismiss();
            }
        });
        b.setView(re);
        b.show();
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
                })
                .addOnFailureListener(e -> {
                    Log.e("==========",e.getMessage());
                });
    }
    private class GroupAdapter extends BaseQuickAdapter<GroupBean, BaseViewHolder> {

        public GroupAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, GroupBean item) {
            StringBuffer sb = new StringBuffer();
            for (UserBean u:item.getUlist()){
                sb.append(u.getEmail()+",");
            }
            helper.setText(R.id.imageView73,item.getG_name())
                    .setText(R.id.imageView75,sb.toString());
        }
    }

}
