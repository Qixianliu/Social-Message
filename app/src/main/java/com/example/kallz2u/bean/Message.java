package com.example.kallz2u.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @Author : kezhijie
 * @Email : 827112947@qq.com
 * @Date : on 2022-11-03 08:56.
 * @Description :描述
 */
public class Message extends LitePalSupport {
    private long id;
    private String content;
    private String add_time;
    private String by_email;

    public Message(String content, String add_time, String by_email) {
        this.content = content;
        this.add_time = add_time;
        this.by_email = by_email;
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
}
