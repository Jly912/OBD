package com.yw.obd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.entity.DeviceListInfo;

/**
 * Created by apollo on 2017/8/15.
 */

public class PopuLvAdapter extends BaseAdapter {

    private Context context;
    private DeviceListInfo deviceListInfo;
    private boolean isWhite = false;

    public PopuLvAdapter(Context context, DeviceListInfo deviceListInfo, boolean isWhite) {
        this.context = context;
        this.deviceListInfo = deviceListInfo;
        this.isWhite = isWhite;
    }

    @Override
    public int getCount() {
        return deviceListInfo.getArr().size();
    }

    @Override
    public Object getItem(int position) {
        return deviceListInfo.getArr().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_popu, parent, false);
            myViewHolder.tvCarName = (TextView) convertView.findViewById(R.id.tv_car);
            myViewHolder.tvCarNum = (TextView) convertView.findViewById(R.id.tv_car_number);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        if (isWhite) {
            myViewHolder.tvCarName.setTextColor(context.getResources().getColor(R.color.gray));
        }
        myViewHolder.tvCarName.setText(deviceListInfo.getArr().get(position).getName());
        myViewHolder.tvCarNum.setText(deviceListInfo.getArr().get(position).getCarNum());

        return convertView;
    }

    class MyViewHolder {
        TextView tvCarNum, tvCarName;
    }
}
