package com.yw.obd.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yw.obd.R;

/**
 * Created by apollo on 2017/7/4.
 */

public class BarView extends View {

    //两张素材图
    private Bitmap mBgBitmap; //背景
    private Bitmap mBarBitmap; //填充

    //素材图的宽高
    private int mWidth, mHeight;

    //百分比中的当前值和总值
    private int mValue, mTotal;

    public BarView(Context context) {
        this(context, null);
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarView);
        Drawable bgDra = typedArray.getDrawable(R.styleable.BarView_bg_source);
        Drawable barDra = typedArray.getDrawable(R.styleable.BarView_bar_source);

        if (bgDra != null) {
            mBgBitmap = ((BitmapDrawable) bgDra).getBitmap();
        } else {
            mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_progress);
        }
        if (bgDra != null) {
            mBarBitmap = ((BitmapDrawable) barDra).getBitmap();
        } else {
            mBarBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_progressend);
        }

        mWidth = mBgBitmap.getWidth();
        mHeight = mBgBitmap.getHeight();

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float hScale = 1.0f;
        float wScale = 1.0f;

        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mWidth) {
            wScale = (float) widthSize / (float) mWidth;
        }
        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mHeight) {
            hScale = (float) heightSize / (float) mHeight;
        }
        float scale = Math.min(hScale, wScale);
        setMeasuredDimension(resolveSizeAndState((int) (mWidth * scale), widthMeasureSpec, 0),
                resolveSizeAndState((int) (mHeight * scale), heightMeasureSpec, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        int width = getMeasuredWidth();
        int right = (int) (((float) mValue / (float) mTotal) * width);
        canvas.clipRect(0, 0, right, mBgBitmap.getHeight());
        canvas.drawBitmap(mBarBitmap, 0, 0, null);
    }

    /**
     * 动态设置长度的百分比
     *
     * @param value 当前值
     * @param total 总值
     */
    public void setVolume(int value, int total) {
        mValue = value;
        mTotal = total;
        postInvalidate();
    }

}
