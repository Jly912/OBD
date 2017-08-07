package com.yw.obd.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.wang.avi.AVLoadingIndicatorView;
import com.yw.obd.R;

/**
 * Created by apollo on 2017/8/7.
 */

public class LoadingDialog extends Dialog {
    private static LoadingDialog dialog;
    private AVLoadingIndicatorView avi;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        dialog = new LoadingDialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        avi= (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
        avi.show();
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

}
