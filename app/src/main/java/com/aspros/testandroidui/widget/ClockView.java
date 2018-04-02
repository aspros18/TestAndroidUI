package com.aspros.testandroidui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class ClockView extends View {

    private Context context;
    private Paint paintOutSide, paintDegree;
    private float outWidth, outHeight;

    public ClockView(Context context) {
        this(context, null);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initOutSize();
    }

    private void initOutSize() {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//获取WM对象
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        outHeight = (float) dm.heightPixels;//获取真实屏幕的高度以px为单位
        outWidth = (float) dm.widthPixels;
    }

    /**
     * 画外圈圆
     *
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas) {
        paintOutSide = new Paint();
        paintOutSide.setColor(Color.LTGRAY);
        paintOutSide.setStyle(Paint.Style.STROKE);
        paintOutSide.setAntiAlias(true);
        paintOutSide.setDither(true);
        paintOutSide.setStrokeWidth(6f);
        canvas.drawCircle(outWidth / 2.0f, outHeight / 2.0f, outWidth / 2.0f, paintOutSide);
    }

    /**
     * 画刻度
     */
    private void drawDegree(Canvas canvas) {
        paintDegree = new Paint();
        paintDegree.setColor(Color.BLACK);
        paintDegree.setStyle(Paint.Style.STROKE);
        paintDegree.setAntiAlias(true);
        paintDegree.setDither(true);
        paintDegree.setStrokeWidth(3f);
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                paintDegree.setStrokeWidth(2f);
                paintDegree.setTextSize(40);

                canvas.drawLine(outWidth / 2.0f, (outHeight / 2.0f - outWidth / 2.0f), outWidth / 2.0f,
                        (outHeight / 2.0f - outWidth / 2.0f + 60), paintDegree);

                String degreeTxt = String.valueOf(i);
                canvas.drawText(degreeTxt, (outWidth / 2 - paintDegree.measureText(degreeTxt) / 2),
                        (outHeight / 2 - outWidth / 2 + 90), paintDegree);
            } else {
                paintDegree.setStrokeWidth(1f);
                paintDegree.setTextSize(30);
                canvas.drawLine(outWidth / 2.0f, (outHeight / 2.0f - outWidth / 2.0f), outWidth / 2.0f, (outHeight / 2.0f - outWidth / 2.0f + 40), paintDegree);
                String degreeTxt = String.valueOf(i);
                canvas.drawText(degreeTxt, (outWidth / 2 - paintDegree.measureText(degreeTxt) / 2) + 20, (outHeight / 2 - outWidth / 2 + 40), paintDegree);
            }
            canvas.rotate(15, outWidth / 2, outHeight / 2);
        }
    }

    private void drawPointor(Canvas canvas) {
        Paint paintHour = new Paint();
        paintHour.setColor(Color.BLACK);
        paintHour.setStyle(Paint.Style.STROKE);
        paintHour.setAntiAlias(true);
        paintHour.setDither(true);
        paintHour.setStrokeWidth(14f);

        Paint paintMin = new Paint();
        paintMin.setColor(Color.BLACK);
        paintMin.setStyle(Paint.Style.STROKE);
        paintMin.setAntiAlias(true);
        paintMin.setDither(true);
        paintMin.setStrokeWidth(10f);

        canvas.save();
        canvas.translate(outWidth / 2, outHeight / 2);
        canvas.drawLine(0, 0, 150, 200, paintHour);
        canvas.drawLine(0, 0, 150, 250, paintMin);
        canvas.restore();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOutCircle(canvas);
        drawDegree(canvas);
        drawPointor(canvas);
    }


}
