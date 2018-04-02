package com.aspros.testandroidui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.aspros.testandroidui.R;

public class RoundProgressBar extends View {
    private Context mContext;
    private float mWidth, mHeight;
    private Paint mPaint;
    private String textContent;

    private static final int START_ANGLE = -90;
    private static final String CENTER_COLOR = "#eeff06";
    private static final String RING_COLOR = "#FF7281E1";
    private static final String PROGRESS_COLOR = "#FFDA0F0F";
    private static final String TEXT_COLOR = "#FF000000";
    private static final int TEXT_SIZE = 30;
    private static final int CIRCLE_RADIUS = 20;
    private static final int RING_WIDTH = 5;

    // 圆弧起始角度, 参考canvas.drawArc
    private int startAngle;

    // 圆环内半径
    private int radius;

    // 进度条的宽度
    private int ringWidth;

    // 默认进度
    private int mProgress = 2;

    // 圆心内部填充色
    private int centerColor;

    // 进度条背景色
    private int ringColor;

    // 进度条的颜色
    private int progressColor;

    // 文字大小
    private int textSize;

    // 文字颜色
    private int textColor;

    // 文字是否需要显示
    private boolean isTextDisplay;

    public RoundProgressBar(Context context) {
        this(context, null);
        init(context);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

        // 获得属性值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        for (int i = 0; i < a.length(); i ++) {
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.RoundProgressBar_startAngle:
                    startAngle = a.getInteger(index, START_ANGLE);
                    break;
                case R.styleable.RoundProgressBar_centerColor:
                    centerColor = a.getColor(index, Color.parseColor(CENTER_COLOR));
                    break;
                case R.styleable.RoundProgressBar_progressColor:
                    progressColor = a.getColor(index, Color.parseColor(PROGRESS_COLOR));
                    break;
                case R.styleable.RoundProgressBar_ringColor:
                    ringColor = a.getColor(index, Color.parseColor(RING_COLOR));
                    break;
                case R.styleable.RoundProgressBar_textColor:
                    textColor = a.getColor(index, Color.parseColor(TEXT_COLOR));
                    break;
                case R.styleable.RoundProgressBar_textSize:
                    textSize = (int) a.getDimension(index, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE,
                            getResources().getDisplayMetrics()));
                    break;
                case R.styleable.RoundProgressBar_isTextDisplay:
                    isTextDisplay = a.getBoolean(index, true);
                    break;
                case R.styleable.RoundProgressBar_radius:
                    radius = (int) a.getDimension(index, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, CIRCLE_RADIUS,
                            getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.RoundProgressBar_ringWidth:
                    ringWidth = (int) a.getDimension(index, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, RING_WIDTH,
                            getResources().getDisplayMetrics()
                    ));
                    break;
                default:
                    break;
            }
        }
        a.recycle();

        // init paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    private void init(Context context){
        this.mContext = context;

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        mWidth = (float) dm.widthPixels; // px为单位
        mHeight = (float) dm.heightPixels;

        Log.e("Custom", "w=" + mWidth + ",h=" + mHeight);

    }

    private void drawCircle(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3.0f);

        canvas.translate(mWidth/2, mHeight/2);
        canvas.drawCircle(0, 0, 280, paint);
    }

    private void drawLine(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3.0f);

        canvas.drawLine(0, 0, 0, 250, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取圆心坐标
        int cx = getWidth() / 2;
        int cy = cx;

        // 画圆心颜色
        if (centerColor != 0) {
            drawCenterCircle(canvas, cx, cy);
        }

        // 画外层大圆
        drawOuterCircle(canvas, cx, cy);

        // 画进度圆弧
        drawProgress(canvas, cx, cy);

        // 画进度百分比
        drawProgressText(canvas, cx, cy);

        // drawCircle(canvas);
        // drawLine(canvas);
    }

    private void drawCenterCircle(Canvas canvas, int cx, int cy) {
        mPaint.setColor(centerColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    private void drawOuterCircle(Canvas canvas, int cx, int cy) {
        mPaint.setColor(ringColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ringWidth);
        canvas.drawCircle(cx, cy, radius + (ringWidth/2), mPaint);
    }

    private void drawProgress(Canvas canvas, int cx, int cy) {
        mPaint.setColor(progressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ringWidth);

        float sweepAngle = (float) (mProgress * 360.0 / 100);
        RectF rect = new RectF(cx - radius - ringWidth/2, cy - radius - ringWidth/2,
                cx + radius + ringWidth/2, cy + radius + ringWidth/2);

        canvas.drawArc(rect, startAngle, sweepAngle, false, mPaint);

    }

    private void drawProgressText(Canvas canvas, int cx, int cy) {
        if (!isTextDisplay) {
            return;
        }

        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setStrokeWidth(0.0f);
        textContent = getProgress() + "%";

        float textWidth = mPaint.measureText(textContent);

        canvas.drawText(textContent, cx -textWidth / 2, cy + textSize / 2, mPaint);
    }

    public synchronized int getProgress() {
        return mProgress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }

        mProgress = progress;
        // 进度改变时，需要通过invalidate方法进行重绘
        postInvalidate();
    }
}
