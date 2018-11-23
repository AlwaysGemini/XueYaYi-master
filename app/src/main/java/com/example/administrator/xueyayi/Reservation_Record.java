package com.example.administrator.xueyayi;

import android.support.annotation.NonNull;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Administrator on 2018/11/20.
 */

public class Reservation_Record extends BmobObject {
    private String UserId;
    private BmobDate Time_Start;
    private BmobDate Time_End;
    private Integer Machine_Number;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }



    public int getMachine_Number() {
        return Machine_Number;
    }

    public void setMachine_Number(int machine_Number) {
        Machine_Number = machine_Number;
    }

    public BmobDate getTime_Start() {
        return Time_Start;
    }

    public void setTime_Start(BmobDate time_Start) {
        Time_Start = time_Start;
    }

    public BmobDate getTime_End() {
        return Time_End;
    }

    public void setTime_End(BmobDate time_End) {
        Time_End = time_End;
    }
}
