package com.example.kallz2u.activites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.kallz2u.Fragment.GroupFragment;
import com.example.kallz2u.R;
import com.example.kallz2u.bean.Message;
import com.example.kallz2u.bean.UserBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : kezhijie
 * @Email : 827112947@qq.com
 * @Date : on 2022-11-03 09:46.
 * @Description :描述
 */
public class MymessageActivity extends AppCompatActivity {
    private MAdapter adapter;
    private List<Message> mList;
    private RecyclerView re;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        re = findViewById(R.id.re);
        mList = LitePal.findAll(Message.class);
        adapter = new MAdapter(R.layout.contact_item_layout);
        LinearLayoutManager l = new LinearLayoutManager(this);
        re.setLayoutManager(l);
        re.setAdapter(adapter);
        adapter.setNewData(mList);
    }
    private class MAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {


        public MAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, Message item) {
            helper.setText(R.id.txtContactName,item.getContent())
                    .setText(R.id.txtContactEmail,item.getBy_email());
        }
    }
}
