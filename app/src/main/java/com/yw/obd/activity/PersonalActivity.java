package com.yw.obd.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.UserInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by apollo on 2017/8/1.
 * 个人资料
 */

public class PersonalActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_ad)
    TextView tvAd;
    @Bind(R.id.tv_birth)
    TextView tvBir;

    private UserInfo info;
    private Calendar startCalendar = Calendar.getInstance();
    SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
    private Dialog loadingDia;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void init() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(inflate)
                .create();
        loadingDia.show();

        tvTitle.setText(getResources().getString(R.string.personal_profile));
        ivBack.setVisibility(View.VISIBLE);

        //获得用户信息
        Http.getUserInfo(this, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            info = new Gson().fromJson(res, UserInfo.class);
                            tvName.setText(info.getName());
                            String gender = info.getGender();
                            tvSex.setText(Integer.parseInt(gender) == 1 ? getResources().getString(R.string.man) : getResources().getString(R.string.woman));
                            tvTel.setText(info.getPhone());
                            tvAd.setText(info.getAddress());
                            tvBir.setText(info.getBriDate());
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @OnClick({R.id.iv_back, R.id.iv_icon, R.id.iv_name_more, R.id.ll_name, R.id.iv_sex_more, R.id.ll_sex,
            R.id.iv_tel_more, R.id.ll_tel, R.id.iv_add_more, R.id.ll_add, R.id.ll_birth, R.id.iv_bir_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                String sex = tvSex.getText().toString();
                String name = tvName.getText().toString();
                String birth = tvBir.getText().toString();
                String tel = tvTel.getText().toString();
                String add = tvAd.getText().toString();
                Log.d("print", "----" + birth.equals(info.getBriDate()));
                int gender = sex.equals(getResources().getString(R.string.man)) ? 1 : 0;
                if (!name.equals(info.getName()) ||
                        gender != Integer.parseInt(info.getGender()) ||
                        !birth.equals(info.getBriDate()) || !tel.equals(info.getPhone()) ||
                        !add.equals(info.getAddress())) {
                    //数据改变 进行用户信息更新
                    Http.updateUserInfo(this, name, gender, birth, tel, add, new Http.OnListener() {
                        @Override
                        public void onSucc(Object object) {
                            String res = (String) object;
                            try {
                                int state = Integer.parseInt(new JSONObject(res).getString("state"));
                                switch (state) {
                                    case 2005:
                                        AppData.showToast(PersonalActivity.this, R.string.update_succ);
                                        finish();
                                        break;
                                    case 2004:
                                        AppData.showToast(PersonalActivity.this, R.string.update_failed);
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    if (loadingDia != null) {
                        loadingDia.dismiss();
                        loadingDia = null;
                    }
                    finish();
                }

                break;
            case R.id.iv_icon:
                break;
            case R.id.iv_name_more:
            case R.id.ll_name:
                final EditText etName = new EditText(this);
                etName.setText(tvName.getText().toString());
                etName.setSelection(tvName.getText().toString().length());
                new AlertDialog.Builder(this)
                        .setTitle(R.string.input_name)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(etName)
                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = etName.getText().toString();
                                if (!TextUtils.isEmpty(name)) {
                                    tvName.setText(name);
                                    dialog.dismiss();
                                } else {
                                    AppData.showToast(PersonalActivity.this, R.string.input_name);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.iv_sex_more:
            case R.id.ll_sex:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.select_sex)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new String[]{getResources().getString(R.string.man), getResources().getString(R.string.woman)}, 0,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                tvSex.setText(R.string.man);
                                                break;
                                            case 1:
                                                tvSex.setText(R.string.woman);
                                                break;

                                        }
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.iv_bir_more:
            case R.id.ll_birth:
                DatePickerDialog dialog2 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                startCalendar.set(Calendar.YEAR, year);
                                startCalendar.set(Calendar.MONTH, monthOfYear);
                                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // TODO Auto-generated method stub
                                tvBir.setText(sdfdate.format(startCalendar.getTime()));
                            }

                        },
                        startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH));
                dialog2.show();
                break;
            case R.id.iv_tel_more:
            case R.id.ll_tel:
                final EditText etTel = new EditText(this);
                etTel.setText(tvTel.getText().toString());
                etTel.setSelection(tvTel.getText().toString().length());
                new AlertDialog.Builder(this)
                        .setTitle(R.string.input_tel)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(etTel)
                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = etTel.getText().toString();
                                if (!TextUtils.isEmpty(name)) {
                                    tvTel.setText(name);
                                    dialog.dismiss();
                                } else {
                                    AppData.showToast(PersonalActivity.this, R.string.input_tel);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.iv_add_more:
            case R.id.ll_add:
                final EditText etAdd = new EditText(this);
                etAdd.setText(tvAd.getText().toString());
                etAdd.setSelection(tvAd.getText().toString().length());
                new AlertDialog.Builder(this)
                        .setTitle(R.string.input_add)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(etAdd)
                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = etAdd.getText().toString();
                                if (!TextUtils.isEmpty(name)) {
                                    tvAd.setText(name);
                                    dialog.dismiss();
                                } else {
                                    AppData.showToast(PersonalActivity.this, R.string.input_add);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (loadingDia != null) {
            loadingDia.dismiss();
            loadingDia = null;
        }
        super.onDestroy();
    }
}
