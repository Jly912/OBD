package com.yw.obd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.activity.MyCarActivity;
import com.yw.obd.activity.PersonalActivity;
import com.yw.obd.base.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apollo on 2017/7/25.
 */

public class MineFragment extends BaseFragment {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_icon)
    CircleImageView ivIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.iv_name_more)
    ImageButton ivNameMore;
    @Bind(R.id.iv_car_more)
    ImageButton ivCarMore;
    @Bind(R.id.ll_car)
    LinearLayout llCar;
    @Bind(R.id.iv_report_more)
    ImageButton ivReportMore;
    @Bind(R.id.ll_report)
    LinearLayout llReport;
    @Bind(R.id.iv_question_more)
    ImageButton ivQuestionMore;
    @Bind(R.id.ll_question)
    LinearLayout llQuestion;
    @Bind(R.id.iv_pwd_more)
    ImageButton ivPwdMore;
    @Bind(R.id.ll_pwd)
    LinearLayout llPwd;
    @Bind(R.id.iv_about_more)
    ImageButton ivAboutMore;
    @Bind(R.id.ll_about)
    LinearLayout llAbout;
    @Bind(R.id.iv_feedback_more)
    ImageButton ivFeedbackMore;
    @Bind(R.id.ll_feedback)
    LinearLayout llFeedback;

    public static MineFragment getInstance() {
        MineFragment mineFragment = new MineFragment();
        Bundle bundle = new Bundle();
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void initView(View view) {
        tvTitle.setText(getResources().getString(R.string.mine));
    }

    @OnClick({R.id.tv_title, R.id.iv_icon, R.id.iv_name_more, R.id.iv_car_more, R.id.ll_car, R.id.iv_report_more, R.id.ll_report, R.id.iv_question_more, R.id.ll_question, R.id.iv_pwd_more, R.id.ll_pwd, R.id.iv_about_more, R.id.ll_about, R.id.iv_feedback_more, R.id.ll_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            case R.id.iv_icon:
                break;
            case R.id.iv_name_more:
                getActivity().startActivity(new Intent(getActivity(), PersonalActivity.class));
                break;
            case R.id.iv_car_more:
            case R.id.ll_car:
                getActivity().startActivity(new Intent(getActivity(), MyCarActivity.class));
                break;
            case R.id.iv_report_more:
                break;
            case R.id.ll_report:
                break;
            case R.id.iv_question_more:
                break;
            case R.id.ll_question:
                break;
            case R.id.iv_pwd_more:
                break;
            case R.id.ll_pwd:
                break;
            case R.id.iv_about_more:
                break;
            case R.id.ll_about:
                break;
            case R.id.iv_feedback_more:
                break;
            case R.id.ll_feedback:
                break;
        }
    }
}
