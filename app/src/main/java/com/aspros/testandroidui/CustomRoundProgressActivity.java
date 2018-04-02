package com.aspros.testandroidui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aspros.testandroidui.widget.RoundProgressBar;

public class CustomRoundProgressActivity extends AppCompatActivity {
    private RoundProgressBar mRoundProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_progressbar);

        mRoundProgressBar = findViewById(R.id.round_progressbar);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRoundProgressBar.getProgress() == 100)
                    mRoundProgressBar.setProgress(2);

                new Thread(){
                    @Override
                    public void run() {
                        int curProgress = mRoundProgressBar.getProgress();
                        while (curProgress <= 100){
                           doNextProgress(curProgress);
                           curProgress += 1;
                        }
                    }
                }.start();
            }
        });
    }

    private void doNextProgress(int progress)
    {
        mRoundProgressBar.setProgress(progress);
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
