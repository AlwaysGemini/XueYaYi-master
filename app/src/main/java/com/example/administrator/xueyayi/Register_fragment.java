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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class Register_fragment extends Fragment {

    private Button register;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        register = view.findViewById(R.id.register);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    EditText useraccount = getActivity().findViewById(R.id.user_account);
                    EditText userpassword = getActivity().findViewById(R.id.user_password);
                    String UserAccount = useraccount.getText().toString();
                    String UserPassword = userpassword.getText().toString();

                    BmobUser user = new BmobUser();
                    user.setUsername(UserAccount);
                    user.setPassword(UserPassword);
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User s, BmobException e) {
                            if (e == null) {
                                Log.i("smile", "用户注册失败");
                            } else {
                                Log.i("smile", "用户注册成功");
                            }
                        }
                    });
                }
            }
        });
    }


}