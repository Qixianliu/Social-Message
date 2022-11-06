package com.example.kallz2u.activites;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kallz2u.Fragment.GroupFragment;
import com.example.kallz2u.R;
import com.example.kallz2u.bean.Message;
import com.example.kallz2u.bean.UserBean;
import com.example.kallz2u.utilities.PostJsonRequest2;
import com.example.kallz2u.utilities.PreferencesUtils;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MymessageActivity extends AppCompatActivity {
    private MAdapter2 adapter;
    private List<Message> mList;
    private RecyclerView re;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        re = findViewById(R.id.re);
        mList = LitePal.findAll(Message.class);
        adapter = new MAdapter2(mList);
        LinearLayoutManager l = new LinearLayoutManager(this);
        re.setLayoutManager(l);
        re.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                test("a##b##c##"+((Message)adapter.getData().get(position)).getContent().split("##")[3]+"##3",((Message)adapter.getData().get(position)).getContent().split("##")[3]);
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
                Toast.makeText(MymessageActivity.this,"send success",Toast.LENGTH_SHORT).show();
                Log.e("=============发送结果",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MymessageActivity.this,"send success",Toast.LENGTH_SHORT).show();
                Log.e("=============///",error.toString());
            }
        });
        request = mQueue.add(request2);
    }
    private class MAdapter2 extends BaseMultiItemQuickAdapter<Message,BaseViewHolder>{

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public MAdapter2(List<Message> data) {
            super(data);
            addItemType(0, R.layout.urgentfrom_item_layout);
            addItemType(1,R.layout.nonurgent_item_layout);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, Message item) {
            if (item.getItemType()==1){
                String[] arratStr = item.getContent().split("##");
                Log.d("=========",item.getContent());
                helper.setText(R.id.imageView45,item.getBy_email())
                        .setText(R.id.tv_tip,arratStr[0])
                        .setText(R.id.imageView46,arratStr[2])
                        .setText(R.id.imageView49,arratStr[1])
                        .addOnClickListener(R.id.btn_1);
            }else {
                String[] arratStr = item.getContent().split("##");
                helper.setText(R.id.imageView27,item.getBy_email())
                        .setText(R.id.tv_tip,arratStr[0])
                        .setText(R.id.imageView39,arratStr[2])
                        .setText(R.id.imageView43,arratStr[1])
                        .addOnClickListener(R.id.btn_1);
            }
        }
    }

}
