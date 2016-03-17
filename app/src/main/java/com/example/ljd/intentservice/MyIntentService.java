package com.example.ljd.intentservice;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


public class MyIntentService extends IntentService {

    private static final String ACTION_DOWNLOAD = "com.example.ljd.intentservice.action.DOWNLOAD";

    private static final String EXTRA_SEC = "com.example.ljd.intentservice.extra.PARAM1";

    private static final int SLEEP_TIME = 100;

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }


    public static void startDownload(Context context, int second) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_SEC,second);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final int param1 = intent.getIntExtra(EXTRA_SEC, 10);
                handleActionFoo(param1);
            }
        }
    }

    private void handleActionFoo(int sec) {

        Progress progress = new Progress(0);
        int millis = sec * 1000;
        for (int i = 0;i <= millis; i+=SLEEP_TIME){
            progress.progress = i;
            EventBus.getDefault().post(progress);
            Log.d(TAG,String.valueOf(i));
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
