package com.yw.obd.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yw.obd.R;

import java.util.List;

/**
 * Created by apollo on 2017/8/11.
 */

public class CheckAdapter extends BaseAdapter {

    private Context context;
    private List<String> carCode;
    private List<String> carStr;
    private String carName;

    public CheckAdapter(Context context, List<String> carCode, List<String> carStr, String carName) {
        this.context = context;
        this.carStr = carStr;
        this.carCode = carCode;
        this.carName = carName;
    }


    @Override
    public int getCount() {
        return carCode.size();
    }

    @Override
    public Object getItem(int position) {
        return carCode.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView != null) {
            holder = (MyViewHolder) convertView.getTag();
        } else {
            holder = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_carcheck, null);
            holder.tvCarName = (TextView) convertView.findViewById(R.id.tvCarName);
            holder.tvCarCode = (TextView) convertView.findViewById(R.id.tv_carCode);
            holder.tvCarStr = (TextView) convertView.findViewById(R.id.tvStr);
            convertView.setTag(holder);
        }

        if (!TextUtils.isEmpty(carName)) {
            holder.tvCarName.setText(carName);
            holder.tvCarStr.setText(carStr.get(position));
            holder.tvCarCode.setText(carCode.get(position));
        }
        return convertView;
    }

    class MyViewHolder {
        TextView tvCarName, tvCarCode, tvCarStr;
    }
}
