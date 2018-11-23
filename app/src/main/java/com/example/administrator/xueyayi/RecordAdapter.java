package com.example.administrator.xueyayi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/11/20.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<Reservation_Record> mRecordList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView reservation_machine_number;
        TextView record_date;

        public ViewHolder(View itemView) {
            super(itemView);
            reservation_machine_number = itemView.findViewById(R.id.reservation_machine_number);
            record_date = itemView.findViewById(R.id.reservation_date);
        }
    }

    public RecordAdapter(List<Reservation_Record> RecordList){
        mRecordList = RecordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reservation_Record reservation_record = mRecordList.get(position);
        holder.reservation_machine_number.setText(String.valueOf(reservation_record.getMachine_Number()));
        holder.record_date.setText(reservation_record.getTime_Start().getDate());
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }
}
