package com.example.lpwd.bean;

public class Values {
    /**
     * account:账号
     * pwd:密码
     */
    private int id;
    private  String account;
    private  String pwd;

    public Values(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "账号："+account+"密码："+pwd;
    }
}
