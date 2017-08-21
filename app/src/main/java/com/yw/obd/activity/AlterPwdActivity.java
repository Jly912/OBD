package com.yw.obd.activity;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/15.
 */

public class AlterPwdActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_original_pwd)
    EditText etOriginalPwd;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_pwd2)
    EditText etPwd2;
    @Bind(R.id.btn_alter)
    Button btnAlter;

    private AlertDialog loadingDia;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String oldPwd = etOriginalPwd.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();
            String pwd2 = etPwd2.getText().toString().trim();

            if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd2)) {
                btnAlter.setEnabled(false);
                btnAlter.setPressed(false);
                btnAlter.setBackgroundColor(getResources().getColor(R.color.board_gray));
            } else {
                btnAlter.setEnabled(true);
                btnAlter.setPressed(true);
                btnAlter.setBackgroundColor(getResources().getColor(R.color.btn_blue));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_pwd;
    }

    @Override
    protected void init() {

        View inflate = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(inflate)
                .create();
        btnAlter.setEnabled(false);
        btnAlter.setPressed(false);
        btnAlter.setBackgroundColor(getResources().getColor(R.color.board_gray));

        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.alter_pwd);
        etOriginalPwd.addTextChangedListener(textWatcher);
        etPwd.addTextChangedListener(textWatcher);
        etPwd2.addTextChangedListener(textWatcher);
    }


    @OnClick({R.id.iv_back, R.id.btn_alter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (loadingDia != null) {
                    loadingDia.dismiss();
                    loadingDia = null;
                }
                finish();
                break;
            case R.id.btn_alter:
                String pwd = etOriginalPwd.getText().toString().trim();
                String newp = etPwd.getText().toString().trim();
                final String newP2 = etPwd2.getText().toString().trim();
                if (!newp.equals(newP2)) {
                    AppData.showToast(this, R.string.pwd_not_same);
                    return;
                }

                loadingDia.show();
                Http.updatePwd(this, pwd, newP2, new Http.OnListener() {
                    @Override
                    public void onSucc(Object object) {
                        if (loadingDia != null && loadingDia.isShowing()) {
                            loadingDia.dismiss();
                        }
                        String res = (String) object;
                        try {
                            int state = new JSONObject(res).getInt("state");
                            switch (state) {
                                case 2005:
                                    AppData.showToast(AlterPwdActivity.this, R.string.update_succ);
                                    AppData.GetInstance(AlterPwdActivity.this).setUserPass(newP2);
                                    finish();
                                    break;
                                case 2004:
                                    AppData.showToast(AlterPwdActivity.this, R.string.update_failed);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
