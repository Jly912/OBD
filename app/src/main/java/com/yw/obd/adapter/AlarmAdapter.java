package com.yw.obd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.entity.WarnListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apollo on 2017/8/15.
 */

public class AlarmAdapter extends BaseAdapter {

    private Context context;
    private List<WarnListInfo.ArrBean> data;

    public AlarmAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<WarnListInfo.ArrBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void addData(List<WarnListInfo.ArrBean> data) {
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    public void addAData(WarnListInfo.ArrBean data) {
        this.data.add(data);
        this.notifyDataSetChanged();
    }

    public void clearData() {
        this.data.clear();
        this.notifyDataSetChanged();
    }

    public List<WarnListInfo.ArrBean> getData() {
        return this.data;
    }

    public void removeData(int position) {
        this.data.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmViewHolder holder = null;
        if (convertView == null) {
            holder = new AlarmViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_alarm, null);
            holder.tvAlarmType = (TextView) convertView.findViewById(R.id.tvAlarmType);
            holder.tvCarNum = (TextView) convertView.findViewById(R.id.tv_car_num);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(holder);
        } else {
            holder = (AlarmViewHolder) convertView.getTag();
        }

        holder.tvAlarmType.setText(data.get(position).getWarn());
        holder.tvTime.setText(data.get(position).getCreateDate());
        holder.tvCarNum.setText(data.get(position).getName());

        return convertView;
    }

    class AlarmViewHolder {
        TextView tvCarNum, tvTime, tvAlarmType;
    }
}
