package com.example.administrator.xueyayi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Bespeak_Activity extends AppCompatActivity implements View.OnClickListener{

    Button select_date = null;
    private Spinner spinner ;
    int mYear;
    int mMonth;
    int mDay;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bespeak_);

        spinner = findViewById(R.id.spinner);

        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        findViewById(R.id.navigation_bespeak).setOnClickListener(this);

        select_date = findViewById(R.id.select_date);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 调用时间选择器
                datePickerDialog = new DatePickerDialog(Bespeak_Activity.this, onDateSetListener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    Date time_start,time_end;

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }

            TextView date=findViewById(R.id.date);
            date.setText(days);
        }
    };

    BmobUser bmobUser = BmobUser.getCurrentUser();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigation_bespeak:
                String hour = (String)spinner.getSelectedItem();
                String start_hour = null;
                String end_hour = null;
                if (hour.equals("8:00-10:00")){
                    start_hour = "8";
                    end_hour = "10";
                }else if(hour.equals("10:00-12:00")){
                    start_hour = "10";
                    end_hour = "12";
                }else if(hour.equals("12:00-14:00")){
                    start_hour = "12";
                    end_hour = "14";
                }else if(hour.equals("14:00-16:00")){
                    start_hour = "14";
                    end_hour = "16";
                }else if(hour.equals("16:00-18:00")){
                    start_hour = "16";
                    end_hour = "18";
                }
                time_start = new Date(mYear-1900,mMonth,mDay,Integer.parseInt(start_hour),0,0);
                time_end = new Date(mYear-1900,mMonth,mDay,Integer.parseInt(end_hour),0,0);
                Toast.makeText(this,sdf.format(time_start),Toast.LENGTH_LONG).show();
                Reservation_Record reservation_record = new Reservation_Record();
                reservation_record.setUserId(bmobUser.getUsername());
                reservation_record.setTime_Start(new BmobDate(time_start));
                reservation_record.setTime_End(new BmobDate(time_end));
                reservation_record.setMachine_Number(2);
                reservation_record.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e == null){
                                    Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"上传失败",Toast.LENGTH_LONG).show();
                                }
                    }
                });
                finish();

                break;
        }
    }
}
