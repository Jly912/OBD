package com.yw.obd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.entity.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apollo on 2017/8/30.
 */

public class RyAdapter extends RecyclerView.Adapter<RyAdapter.MyViewHolder> {

    private Context context;
    private List<ErrorCode> data;

    public RyAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<ErrorCode> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void addData(List<ErrorCode> data) {
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_error, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(data.get(position).getCodename());
        holder.tvNum.setText(data.get(position).getCodeNum());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum, tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
