package com.example.kallz2u.activites;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kallz2u.R;
import com.example.kallz2u.bean.UserBean;
import com.example.kallz2u.utilities.PostJsonRequest2;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
   private RecyclerView re;
   private MAdapter adapter;
   private List<UserBean> mList;
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_userlist);
      re = findViewById(R.id.re);
      mList = new ArrayList<>();
      adapter = new MAdapter(R.layout.adapter_user);
      LinearLayoutManager l = new LinearLayoutManager(this);
      re.setLayoutManager(l);
      re.setAdapter(adapter);
      getData();

      adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
         @Override
         public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            test(((UserBean)adapter.getData().get(position)).getToken());
         }
      });

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

   private void test(String token) {
      Log.d("============",token);
      RequestQueue mQueue = Volley.newRequestQueue(this);
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

   private class MAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder>{


      public MAdapter(int layoutResId) {
         super(layoutResId);
      }

      @Override
      protected void convert(@NonNull BaseViewHolder helper, UserBean item) {
         helper.setText(R.id.tv_1,String.format("user:%s",item.getEmail()));
      }
   }
}
