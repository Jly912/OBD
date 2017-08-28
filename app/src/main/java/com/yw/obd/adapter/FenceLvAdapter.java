package com.yw.obd.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.entity.FenceListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apollo on 2017/8/24.
 */

public class FenceLvAdapter extends BaseAdapter {

    private Context context;
    private List<FenceListEntity.GeofencesBean> data;

    public FenceLvAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<FenceListEntity.GeofencesBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<FenceListEntity.GeofencesBean> getData() {
        return data;
    }

    public void delData(int position) {
        this.data.remove(position);
        Log.e("print","data---"+data);
        this.notifyDataSetChanged();
        Log.e("print","data11---"+data);
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
        FenceViewHolder fenceViewHolder = null;
        if (convertView != null) {
            fenceViewHolder = (FenceViewHolder) convertView.getTag();
        } else {
            fenceViewHolder = new FenceViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_fence, parent, false);
            fenceViewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_fence_name);
            fenceViewHolder.tvLat = (TextView) convertView.findViewById(R.id.tv_lat);
            fenceViewHolder.tvLng = (TextView) convertView.findViewById(R.id.tv_lng);
            fenceViewHolder.tvRadius = (TextView) convertView.findViewById(R.id.tv_radius);
            convertView.setTag(fenceViewHolder);
        }

        fenceViewHolder.tvName.setText(data.get(position).getFenceName());
        fenceViewHolder.tvLat.setText(context.getResources().getString(R.string.lat) + ":" + data.get(position).getLat());
        fenceViewHolder.tvLng.setText(context.getResources().getString(R.string.lng) + ":" + data.get(position).getLng());
        fenceViewHolder.tvRadius.setText(context.getResources().getString(R.string.radius) + ":" + data.get(position).getRadius());

        return convertView;
    }

    class FenceViewHolder {
        TextView tvName, tvLat, tvLng, tvRadius;
    }
}
