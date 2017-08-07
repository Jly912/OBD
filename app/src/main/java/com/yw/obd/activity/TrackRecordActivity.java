package com.yw.obd.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/28.
 * 轨迹记录
 */

public class TrackRecordActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_car)
    TextView tvCar;
    @Bind(R.id.tv_car_number)
    TextView tvCarNumber;
    @Bind(R.id.btn_more)
    ImageButton btnMore;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.tv_reset)
    TextView tvReset;
    @Bind(R.id.btn_left)
    ImageButton btnLeft;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.btn_right)
    ImageButton btnRight;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private List<String> data;
    private MyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_track_record;
    }

    @Override
    protected void init() {
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("第" + i + "条Item");
        }

        adapter=new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TrackRecordActivity.this,LocusActivity.class);
                startActivity(intent);
            }
        });

        tvTitle.setText(getResources().getString(R.string.track_record));
        ivBack.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.btn_blue));
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.btn_more, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_reset, R.id.btn_left, R.id.tv_date, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_more:
                break;
            case R.id.tv_start_time:
                break;
            case R.id.tv_end_time:
                break;
            case R.id.tv_reset:
                break;
            case R.id.btn_left:
                break;
            case R.id.tv_date:
                break;
            case R.id.btn_right:
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        static final int END = 0;
        static final int NORMAL = 1;

        @Override
        public int getCount() {
            return data.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == data.size()) {
                return END;
            }
            return NORMAL;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position==data.size()){
                refreshLayout.setRefreshing(false);
            }
            NormalViewHolder normalViewHolder;
            EndViewHolder endViewHolder;
            switch (getItemViewType(position)) {
                case NORMAL:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(TrackRecordActivity.this).inflate(R.layout.list_item_normal, parent, false);
                        normalViewHolder = new NormalViewHolder();
                        convertView.setTag(normalViewHolder);
                    }else {
                        normalViewHolder= (NormalViewHolder) convertView.getTag();
                    }

//                    normalViewHolder.tv.setText(data.get(position));
                    break;
                case END:

                    if (convertView==null){
                        convertView = LayoutInflater.from(TrackRecordActivity.this).inflate(R.layout.list_iitem_end,parent,false);
                        endViewHolder=new EndViewHolder();
                        convertView.setTag(endViewHolder);
                    }else {
                        endViewHolder= (EndViewHolder) convertView.getTag();
                    }

                    break;
            }
            return convertView;
        }

        class NormalViewHolder {
            TextView tv;
        }
        class EndViewHolder {
            TextView tv;
        }


    }
}
