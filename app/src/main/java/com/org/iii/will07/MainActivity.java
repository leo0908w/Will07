package com.org.iii.will07;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button left, right;
    private TextView clock;
    boolean isRunning;
    private int counter;
    private UIHandler uiHandler;
    private Timer timer;
    private ClockTask clockTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiHandler = new UIHandler();
        timer = new Timer();

        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);
        clock = (TextView)findViewById(R.id.clock);

    }

    @Override
    public void finish() {
        if (timer != null) {
            timer.purge();
            timer.cancel();
            super.finish();
        }
    }

    public void doLeft(View v){
        if (isRunning) {
            doLap();
        } else {
            doReset();
        }
    }
    public void doRight(View v){
        isRunning = !isRunning;
        right.setText(isRunning? "STOP" : "START");
        left.setText(isRunning? "LAP" : "RESET");

        if (isRunning) {
            doStart();
        } else {
            doStop();
        }
    }
    private void doStart() {
        clockTask = new ClockTask();
        timer.schedule(clockTask, 10, 10);
    }
    private void doStop() {
        clockTask.cancel();
    }
    private void doReset() {
        counter = 0;
        uiHandler.sendEmptyMessage(0);
    }
    private void doLap() {

    }
    private class ClockTask extends TimerTask {
        @Override
        public void run() {
            counter++;
            uiHandler.sendEmptyMessage(0);
        }
    }
    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clock.setText("" + counter);
        }
    }

}
