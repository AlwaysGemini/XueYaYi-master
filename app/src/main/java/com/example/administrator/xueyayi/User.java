package com.example.administrator.xueyayi;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/10/17.
 */

public class User extends BmobUser {

    private String Account;
    private String Password;

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    @Override
    public void setPassword(String password) {
        Password = password;
    }

}
