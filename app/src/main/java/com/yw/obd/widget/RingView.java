package com.yw.obd.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RingView extends View {

    private final Paint paint;
    private final Context context;
    public double radius;

    public RingView(Context context) {
        // TODO Auto-generated constructor stub
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.STROKE);//绘制空心圆
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        int center = getWidth() / 2;
        int innerCircle = (int) radius;//设置内圆半径

        //绘制圆环
        this.paint.setARGB(64, 66, 97, 180);
        this.paint.setStrokeWidth((int) radius);
        canvas.drawCircle(center, center, (int) radius / 2, this.paint);

        //绘制外圆
        this.paint.setARGB(255, 66, 97, 180);
        this.paint.setStrokeWidth(2);
        canvas.drawCircle(center, center, innerCircle, this.paint);

        super.onDraw(canvas);
    }
}
