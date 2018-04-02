package com.aspros.testandroidui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.aspros.testandroidui.widget.SignalLightView;

public class CustomSignalLightActivity extends AppCompatActivity {

    private SignalLightView mSignalLightView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_singlelight);

        mSignalLightView = new SignalLightView(this);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.main_layout);
        layout.addView(mSignalLightView);

        new Thread(new SignalLightThread()).start();
    }

    class SignalLightThread implements Runnable{

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mSignalLightView.postInvalidate(); // 使得View重绘调用onDraw方法
            }
        }
    }
}

