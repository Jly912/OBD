package com.yw.obd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.activity.MyCarActivity;
import com.yw.obd.activity.OilActivity;
import com.yw.obd.activity.PersonalActivity;
import com.yw.obd.base.BaseFragment;
import com.yw.obd.entity.UserInfo;
import com.yw.obd.http.Http;

import org.json.JSONException;
import org.json.JSONObject;

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
    @Bind(R.id.ll_oil)
    LinearLayout llOil;
    @Bind(R.id.iv_oil_more)
    ImageButton ivOilMore;
    @Bind(R.id.rl_user)
    RelativeLayout rlUser;

    private UserInfo userInfo;


    public static MineFragment getInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Http.getUserInfo(getActivity(), new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    if (state == 0) {
                        userInfo = new Gson().fromJson(res, UserInfo.class);
                        tvName.setText(userInfo.getName());
                        tvPhone.setText(getResources().getString(R.string.phone_number) + userInfo.getPhone());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initView(View view) {
        tvTitle.setText(getResources().getString(R.string.mine));
    }

    @OnClick({R.id.rl_user, R.id.iv_icon, R.id.iv_name_more, R.id.iv_car_more, R.id.ll_car, R.id.iv_report_more, R.id.ll_report, R.id.iv_question_more, R.id.ll_question, R.id.iv_pwd_more, R.id.ll_pwd, R.id.iv_about_more,
            R.id.ll_about, R.id.iv_feedback_more, R.id.ll_feedback, R.id.iv_oil_more, R.id.ll_oil})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user:
            case R.id.iv_icon:
            case R.id.iv_name_more:
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                if (userInfo != null) {
                    intent.putExtra("user", userInfo);
                }
                getActivity().startActivity(intent);
                break;
            case R.id.iv_car_more:
            case R.id.ll_car:
                getActivity().startActivity(new Intent(getActivity(), MyCarActivity.class));
                break;
            case R.id.iv_oil_more:
            case R.id.ll_oil:
                getActivity().startActivity(new Intent(getActivity(), OilActivity.class));
                break;
            case R.id.iv_report_more:
            case R.id.ll_report:
                break;
            case R.id.iv_question_more:
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
