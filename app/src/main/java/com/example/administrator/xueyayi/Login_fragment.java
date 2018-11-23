package com.example.administrator.xueyayi;

/**
 * Created by Administrator on 2018/10/17.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class Login_fragment extends Fragment implements View.OnClickListener {

    private Button login;
    private TextView register;
    private Register_fragment register_fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        login = view.findViewById(R.id.login);
        register = view.findViewById(R.id.register);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击登录按钮，则尝试登录
            case R.id.login:
                //获取到用户输入的账号密码
                if (getActivity() != null) {
                    EditText useraccount = getActivity().findViewById(R.id.user_account);
                    EditText userpassword = getActivity().findViewById(R.id.user_password);
                    String UserAccount = useraccount.getText().toString();
                    String UserPassword = userpassword.getText().toString();
                    //将用户账号密码存入User对象中
                    BmobUser user = new BmobUser();;
                    user.setUsername(UserAccount);
                    user.setPassword(UserPassword);
                    user.login(new SaveListener<BmobUser>() {

                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if(e==null){
                                Log.i("smile","用户登录成功");
                                getActivity().finish();
                                //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                                //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                            }else{
                                Log.i("smile","用户登录失败");
                            }
                        }
                    });

                }
                break;
            //点击注册按钮，则跳转到注册界面
            case R.id.register:
                if (register_fragment == null) {
                    register_fragment = new Register_fragment();
                }
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.login_container, register_fragment).addToBackStack(null).commitAllowingStateLoss();
                }
                break;
        }
    }
}