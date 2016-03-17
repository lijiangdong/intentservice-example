package com.example.ljd.intentservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mTextView = (TextView)findViewById(R.id.text_1);
        MyIntentService.startDownload(this,10);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(Progress  progress){
        mTextView.setText(String.valueOf(progress.progress));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
