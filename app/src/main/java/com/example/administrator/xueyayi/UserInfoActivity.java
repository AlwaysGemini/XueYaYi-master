package com.example.administrator.xueyayi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        BmobUser user = BmobUser.getCurrentUser();
        TextView username = findViewById(R.id.username);
        username.setText(user.getUsername());
    }
}
