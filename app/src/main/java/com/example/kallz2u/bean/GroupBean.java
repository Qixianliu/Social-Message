package com.example.kallz2u.bean;

import java.util.List;

public class GroupBean {
    private String g_name;
    private List<UserBean> ulist;

    public GroupBean(String g_name, List<UserBean> ulist) {
        this.g_name = g_name;
        this.ulist = ulist;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public List<UserBean> getUlist() {
        return ulist;
    }

    public void setUlist(List<UserBean> ulist) {
        this.ulist = ulist;
    }
}
