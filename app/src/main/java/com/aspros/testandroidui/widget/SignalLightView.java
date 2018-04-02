package com.aspros.testandroidui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SignalLightView extends View {
    private int count = 0;
    private Paint mPaint;

    public SignalLightView(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);

        mPaint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (count < 100){
            count ++;
        }else{
            count = 0;
        }

        int cx = getWidth() / 2;
        int cy = cx;

        mPaint.setStrokeWidth(3.0f);
        switch(count%4){
            case 0:
                mPaint.setColor(Color.GREEN);
                break;
            case 1:
                mPaint.setColor(Color.RED);
                break;
            case 2:
                mPaint.setColor(Color.BLUE);
                break;
            case 3:
                mPaint.setColor(Color.YELLOW);
                break;
            default:
                mPaint.setColor(Color.WHITE);
                break;
        }

        canvas.drawCircle(cx, cy, 100, mPaint);
    }
}
