package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.CarBrandDetailInfo;
import com.yw.obd.entity.CarBrandInfo;
import com.yw.obd.entity.CarYearModelInfo;
import com.yw.obd.http.WebService;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/2.
 * 添加车辆
 */

public class AddCarActivity extends BaseActivity implements WebService.WebServiceListener {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_car_num)
    EditText etCarNum;
    @Bind(R.id.tv_car_brand)
    TextView tvCarBrand;
    @Bind(R.id.iv_car_brand_more)
    ImageButton ivCarBrandMore;
    @Bind(R.id.ll_car_brand)
    LinearLayout llCarBrand;
    @Bind(R.id.tv_car_type)
    TextView tvCarType;
    @Bind(R.id.iv_car_type_more)
    ImageButton ivCarTypeMore;
    @Bind(R.id.ll_car_type)
    LinearLayout llCarType;
    @Bind(R.id.tv_model_year)
    TextView tvModelYear;
    @Bind(R.id.iv_model_year_more)
    ImageButton ivModelYearMore;
    @Bind(R.id.ll_model_year)
    LinearLayout llModelYear;
    @Bind(R.id.tv_gear_case)
    TextView tvGearCase;
    @Bind(R.id.tv_displacement)
    TextView tvDisplacement;
    @Bind(R.id.et_car_km)
    EditText etKm;
    @Bind(R.id.et_carriage)
    EditText etCarriage;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.et_device)
    EditText etDevice;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    private static final int ADD_CAR = 1;
    private static final int GET_CAR_BRAND = 2;
    private static final int GET_CAR_BRAND_DETAIL = 3;
    private static final int GET_CAR_YEAR_MODEL = 4;
    private static final int GET_CAR_DETAIL = 5;
    private static final String KEY = "20170801CHLOBDYW028M";
    private String selectedCarBrand = "0";
    private String carBrand = "";
    private String carModel = "";
    private String selectCarID = "";
    private String typeYear = "";
    private String gearbox = "";
    private String displacement = "";
    private String type = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected void init() {
        tvTitle.setText(getResources().getString(R.string.add_car));
        ivBack.setVisibility(View.VISIBLE);

        type = getIntent().getStringExtra("type");
        Log.d("print", "----" + type);

    }

    @OnClick({R.id.iv_car_brand_more, R.id.ll_car_brand, R.id.iv_car_type_more, R.id.ll_car_type, R.id.iv_model_year_more, R.id.ll_model_year, R.id.btn_submit, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_car_brand_more:
            case R.id.ll_car_brand:
                getCarBrandInfo(1, GET_CAR_BRAND, "0");
                break;
            case R.id.iv_car_type_more:
            case R.id.ll_car_type:
                getCarBrandInfo(2, GET_CAR_BRAND_DETAIL, selectedCarBrand);
                break;
            case R.id.iv_model_year_more:
            case R.id.ll_model_year:
                getCarBrandInfo(3, GET_CAR_YEAR_MODEL, selectCarID);
                break;
            case R.id.btn_submit:
                String carNum = etCarNum.getText().toString().trim();
                String carKm = etKm.getText().toString().trim();
                String sn = etDevice.getText().toString().trim();
                String carVIN = etCarriage.getText().toString().trim();
                if (TextUtils.isEmpty(sn) || TextUtils.isEmpty(carNum) || TextUtils.isEmpty(carBrand) || TextUtils.isEmpty(typeYear) || TextUtils.isEmpty(displacement)
                        || TextUtils.isEmpty(gearbox) || TextUtils.isEmpty(carKm) || TextUtils.isEmpty(carVIN) || TextUtils.isEmpty(carModel)) {
                    AppData.showToast(this, R.string.complete_info);
                    return;
                }

                addCar(sn, carNum, carBrand, carModel, typeYear, gearbox, displacement, carKm, carVIN);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }


    private void addCar(String sn, String carNum, String carBrand, String carModel,
                        String carYear, String carGBox, String carOutput, String carDis, String carVIN) {
        WebService web = new WebService(this, ADD_CAR, false, "AddDevice");
        HashMap<String, Object> property = new HashMap<>();
        property.put("loginName", AppData.GetInstance(this).getUserName());
        property.put("password", AppData.GetInstance(this).getUserPass());
        property.put("key", KEY);
        property.put("sn", sn);
        property.put("carNum", carNum);
        property.put("carBrand", carBrand);
        property.put("carModel", carModel);
        property.put("carYear", carYear);
        property.put("carGBox", carGBox);
        property.put("carOutput", carOutput);
        property.put("carDis", carDis);
        property.put("carVIN", carVIN);
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }

    private void getCarBrandInfo(int typeID, int method, String carId) {
        WebService web = new WebService(this, method, false, "GetCarBrandInfo");
        HashMap<String, Object> property = new HashMap<>();
        property.put("typeID", typeID);
        property.put("key", KEY);
        property.put("id", carId);
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }

    @Override
    public void onWebServiceReceive(String method, int id, String result) {
        try {
            JSONObject jo = new JSONObject(result);
            if (id == GET_CAR_BRAND) { //获得车辆品牌信息
                int status = Integer.parseInt(jo.getString("status"));
                if (status != 0) {
                    String msg = jo.getString("msg");
                    AppData.GetInstance(this).showToast(this, msg);
                    return;
                }

                final CarBrandInfo carBrandInfo = new Gson().fromJson(result, CarBrandInfo.class);
                final List<CarBrandInfo.ResultBean> carList = carBrandInfo.getResult();
                final List<String> name = new ArrayList<>();
                for (CarBrandInfo.ResultBean car : carList) {
                    name.add(car.getName());
                }

                new AlertDialog.Builder(this)
                        .setTitle(R.string.select_car_brand)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, name), 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedCarBrand = carList.get(which).getId();
                                carBrand = name.get(which);
                                tvCarBrand.setText(carBrand);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            } else if (id == GET_CAR_BRAND_DETAIL) {   //获得品牌详情
                int status = Integer.parseInt(jo.getString("status"));
                if (status != 0) {
                    String msg = jo.getString("msg");
                    AppData.GetInstance(this).showToast(this, msg);
                    return;
                }

                CarBrandDetailInfo detailInfo = new Gson().fromJson(result, CarBrandDetailInfo.class);
                final List<CarBrandDetailInfo.ResultBean> cars = detailInfo.getResult();
                final List<String> name = new ArrayList<>();
                for (CarBrandDetailInfo.ResultBean car : cars) {
                    String carName = car.getName();
                    name.add(carName);
                }
                final List<String> logos = new ArrayList<>();
                new AlertDialog.Builder(this)
                        .setTitle(R.string.select_car_brand)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, name), 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final List<CarBrandDetailInfo.ResultBean.ListBean> list = cars.get(which).getList();
                                final List<String> carlistName = new ArrayList<>();
                                for (CarBrandDetailInfo.ResultBean.ListBean car : list) {
                                    carlistName.add(car.getName());
                                    String logo = car.getLogo();
                                    String[] split = logo.split("/");
                                    String ss = "";
                                    for (int i = 0; i < split.length; i++) {
                                        if (i == split.length - 1) {
                                            ss += split[i];
                                        } else {
                                            ss += split[i] + "/";
                                        }
                                    }
                                    logos.add(ss);
                                }

                                Log.d("print", "logos" + logos);
                                new AlertDialog.Builder(AddCarActivity.this)
                                        .setTitle(R.string.select_car_model)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setSingleChoiceItems(new ArrayAdapter<>(AddCarActivity.this, android.R.layout.simple_expandable_list_item_1, carlistName), 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String name = carlistName.get(which);
                                                carModel = name;
                                                tvCarType.setText(carModel);
                                                selectCarID = list.get(which).getId();
                                                dialog.dismiss();
                                            }
                                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else if (id == GET_CAR_YEAR_MODEL) { //获得年款
                int status = Integer.parseInt(jo.getString("status"));
                if (status != 0) {
                    String msg = jo.getString("msg");
                    AppData.GetInstance(this).showToast(this, msg);
                    return;
                }

                CarYearModelInfo yearModelInfo = new Gson().fromJson(result, CarYearModelInfo.class);
                final List<CarYearModelInfo.ResultBean.ListBean> car = yearModelInfo.getResult().getList();
                final List<String> yearType = new ArrayList<>();
                for (CarYearModelInfo.ResultBean.ListBean item : car) {
                    yearType.add(item.getName());
                }
                new AlertDialog.Builder(this)
                        .setTitle(R.string.select_car_year)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, yearType), 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                typeYear = yearType.get(which);
                                tvModelYear.setText(typeYear);
                                getCarBrandInfo(4, GET_CAR_DETAIL, car.get(which).getId());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            } else if (id == GET_CAR_DETAIL) {
                int status = Integer.parseInt(jo.getString("status"));
                if (status != 0) {
                    String msg = jo.getString("msg");
                    AppData.GetInstance(this).showToast(this, msg);
                    return;
                }
                JSONObject object = jo.getJSONObject("result");
//                typeYear = object.getString("yeartype");
                JSONObject jsonObject = object.getJSONObject("basic");
                displacement = jsonObject.getString("displacement");
                gearbox = jsonObject.getString("gearbox");
                tvGearCase.setText(gearbox);
                tvDisplacement.setText(displacement + "L/T");

            } else if (id == ADD_CAR) {//添加车辆
                switch (Integer.parseInt(jo.getString("state"))) {
                    case 1006:
                        AppData.showToast(this, R.string.sn_exist);
                        break;
                    case 1010:
                        AppData.showToast(this, R.string.sn_not_exist);
                        break;
                    case 1011:
                        AppData.showToast(this, R.string.sn_has_add);
                        break;
                    case 2003:
                        AppData.showToast(this, R.string.car_exist);
                        break;
                    case 2006:
                        AppData.showToast(this, R.string.add_failed);
                        break;
                    case 2007:
                        AppData.showToast(this, R.string.add_succ);
                        if (type == null) {
                            finish();
                        } else if (type.equals("login")) {
                            startActivity(new Intent(this, MainActivity.class));
                            Log.d("print", "type" + type);
                            finish();
                        }
                        break;
                    case 2010:
                        AppData.showToast(this, R.string.add_same);
                        break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
