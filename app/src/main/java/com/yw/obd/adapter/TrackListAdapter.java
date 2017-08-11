package com.yw.obd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.entity.TrackListInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apollo on 2017/8/8.
 */

public class TrackListAdapter extends BaseAdapter {
    private Context context;
    private List<TrackListInfo.ArrBean> data;

    private static final int END = 0;
    private static final int NORMAL = 1;

    public TrackListAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<TrackListInfo.ArrBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<TrackListInfo.ArrBean> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size() + 1;
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size() || data.size() == 0 || data == null) {
            return END;
        }
        return NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NormalViewHolder normalViewHolder;
        EndViewHolder endViewHolder;
        switch (getItemViewType(position)) {
            case NORMAL:
                if (convertView == null) {
                    normalViewHolder = new NormalViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_normal, parent, false);
                    normalViewHolder.tvStartTime = (TextView) convertView.findViewById(R.id.tv_start_time);
                    normalViewHolder.tvEndTime = (TextView) convertView.findViewById(R.id.tv_end_time);
                    normalViewHolder.tvStartAdd = (TextView) convertView.findViewById(R.id.tvStartAdd);
                    normalViewHolder.tvEndAdd = (TextView) convertView.findViewById(R.id.tvEndAdd);
                    normalViewHolder.tvKm = (TextView) convertView.findViewById(R.id.tv_km);
                    normalViewHolder.tvStopTime = (TextView) convertView.findViewById(R.id.tv_time);
                    convertView.setTag(normalViewHolder);
                } else {
                    normalViewHolder = (NormalViewHolder) convertView.getTag();
                }
                TrackListInfo.ArrBean track = data.get(position);
                String startTime = track.getStartTime();
                String endTime = track.getEndTime();
                String[] start = startTime.split(" ");
                String[] end = endTime.split(" ");
                normalViewHolder.tvStartTime.setText(start[1] + context.getResources().getString(R.string.start_point));
                normalViewHolder.tvEndTime.setText(end[1] + context.getResources().getString(R.string.end_point));
                normalViewHolder.tvStartAdd.setText(track.getStartAddress());
                normalViewHolder.tvEndAdd.setText(track.getEndAddress());
                String distance = track.getDistance();
                float distant = ((float) Integer.parseInt(distance)) / 1000;
                if (Integer.parseInt(distance) < 1000) {
                    normalViewHolder.tvKm.setText(Integer.parseInt(distance) + "m");
                } else {
                    DecimalFormat decimalFormat=new DecimalFormat(".00");
                    normalViewHolder.tvKm.setText(decimalFormat.format(distant) + "Km");
                }

                String stopMinutes = track.getStopMinutes();

                int day = Integer.parseInt(stopMinutes)
                        / (24 * 60);
                int hour = (Integer.parseInt(stopMinutes) - day * 24 * 60) / 60;
                int minute = Integer.parseInt(stopMinutes) - day
                        * 24 * 60 - hour * 60;

                String park = (day > 0 ? day
                        + context.getResources().getString(R.string.day)
                        : "")
                        + ((hour > 0 || day > 0) ? hour
                        + context.getResources().getString(R.string.hour)
                        : "") + minute
                        + context.getResources().getString(R.string.minute);
                normalViewHolder.tvStopTime.setText(context.getResources().getString(R.string.stop) + park);
                break;
            case END:

                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_iitem_end, parent, false);
                    endViewHolder = new EndViewHolder();
                    convertView.setTag(endViewHolder);
                } else {
                    endViewHolder = (EndViewHolder) convertView.getTag();
                }

                break;
        }
        return convertView;
    }

    class NormalViewHolder {
        TextView tvStartTime, tvEndTime, tvStartAdd, tvEndAdd, tvKm, tvStopTime;
    }

    class EndViewHolder {
        TextView tv;
    }

}
