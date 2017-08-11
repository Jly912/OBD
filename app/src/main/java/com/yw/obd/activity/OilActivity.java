package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
 * Created by apollo on 2017/8/8.
 */

public class OilActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.actionbar)
    LinearLayout actionbar;
    @Bind(R.id.et_oil)
    EditText etOil;
    @Bind(R.id.ll_oil)
    LinearLayout llOil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oil;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.current_oil_price);

        etOil.setCursorVisible(false);
        etOil.setFocusable(false);
        etOil.setFocusableInTouchMode(false);

        Http.getOilPrice(this, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(res);
                    int state = Integer.parseInt(jsonObject.getString("state"));
                    switch (state) {
                        case 0:
                            String oilPrice = jsonObject.getString("oilPrice");
                            etOil.setText(oilPrice);
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.et_oil, R.id.ll_oil})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_oil:
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText et = new EditText(this);
                et.setText(etOil.getText());
                et.setHintTextColor(getResources().getColor(R.color.bg_gray));
                et.setFocusable(true);
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                linearLayout.addView(et);
                new AlertDialog.Builder(this)
                        .setTitle(R.string.input_oil)
                        .setView(linearLayout)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String trim = et.getText().toString().trim();
                                if (TextUtils.isEmpty(trim)) {
                                    AppData.showToast(OilActivity.this, R.string.input_oil);
                                    return;
                                } else {
                                    etOil.setText(trim);
                                    Http.updateOil(OilActivity.this, trim, new Http.OnListener() {
                                        @Override
                                        public void onSucc(Object object) {
                                            String res = (String) object;
                                            Log.e("print", "oil====" + res);
                                            try {
                                                int state = Integer.parseInt(new JSONObject(res).getString("state"));
                                                switch (state) {
                                                    case 2005://修改成功
                                                        AppData.showToast(OilActivity.this, R.string.update_succ);
                                                        break;
                                                    case 2004://修改失败
                                                        AppData.showToast(OilActivity.this, R.string.update_failed);
                                                        break;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    dialog.dismiss();
                                }

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false).show();
                break;
            case R.id.ll_oil:
                break;
        }
    }

}
