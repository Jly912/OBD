package com.yw.obd.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yw.obd.R;

import butterknife.ButterKnife;

/**
 * Created by apollo on 2017/7/25.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        //查找actionbar控件设置paddingtop
        BaseActivity activity = (BaseActivity) getActivity();
        //判断Activity是否开启沉浸式通知栏
        if (activity.isOpenStatus()) {
            View actionBar = view.findViewById(R.id.actionbar);
            if (actionBar != null) {
                int statusHeight = getStatusHeight(getActivity());
                actionBar.setPadding(0, statusHeight, 0, 0);
            }
        }

        initView(view);
    }

    protected abstract int getLayoutId();

    protected void initView(View view) {
    }

    /**
     * 获得通知栏的高度
     *
     * @param context
     * @return
     */
    private int getStatusHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelOffset(resId);
        }

        return -1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
