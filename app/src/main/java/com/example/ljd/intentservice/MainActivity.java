package com.example.ljd.intentservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int mTextViewTag;
    private Button mAddButton;
    private LinearLayout mContainerLinear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mTextViewTag = 0;
        mAddButton = (Button) findViewById(R.id.add_btn);
        mContainerLinear = (LinearLayout) findViewById(R.id.linear_container);
        mAddButton.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(Counter counter){
        TextView textView = (TextView)mContainerLinear.findViewWithTag(counter.tag);
        textView.setText(counter.progress + "ms");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                //生成2~5之间的随机数
                Random random = new Random();
                int num = random.nextInt(3)%(3) + 1;

                TextView textView = new TextView(this);
                textView.setTag(mTextViewTag);
                textView.setText(num * 1000 + "ms");
                mContainerLinear.addView(textView);
                MyIntentService.startDownload(this,num,mTextViewTag);
                mTextViewTag++;
                break;
            default:
                break;
        }
    }
}
