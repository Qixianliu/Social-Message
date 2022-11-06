package com.example.kallz2u.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.litepal.crud.LitePalSupport;

public class Message extends LitePalSupport implements MultiItemEntity {
    private long id;
    private String content;
    private String add_time;
    private String by_email;
    private int type; //0 urgent 1non urgent

    public Message(String content, String add_time, String by_email) {
        this.content = content;
        this.add_time = add_time;
        this.by_email = by_email;
    }

    public Message(String content, String by_email, int type) {
        this.content = content;
        this.by_email = by_email;
        this.type = type;
    }

    public Message(String content, String add_time, String by_email, int type) {
        this.content = content;
        this.add_time = add_time;
        this.by_email = by_email;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }


    public String getBy_email() {
        return by_email;
    }

    public void setBy_email(String by_email) {
        this.by_email = by_email;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
