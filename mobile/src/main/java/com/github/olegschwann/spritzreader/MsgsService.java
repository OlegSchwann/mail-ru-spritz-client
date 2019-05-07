package com.github.olegschwann.spritzreader;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.olegschwann.spritzreader.repo.ApiRepo;
import com.github.olegschwann.spritzreader.repo.MsgsRepo;

import java.util.concurrent.TimeUnit;

public class MsgsService extends Service {

    private Thread mWorkingThread;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MY_SERVICE", "ON START");
        super.startForeground(0, null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startObserving();
        return super.onStartCommand(intent, flags, startId);
    }

    /*@Override
    public void onHandleIntent(Intent intent) {
        while (true) {
            try {
                Log.d("MY_SERVICE", "IN HANDLE");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWorkingThread.interrupt();
        if (mWorkingThread.isInterrupted()) {
            Log.d("MY_SERVICE", "IS INTERRUPTED");
        }
        Log.d("MY_SERVICE", "ON_DESTROY");
    }

    public void startObserving() {
        mWorkingThread = new Thread(new Runnable() {
            public void run() {
                while (!mWorkingThread.isInterrupted()) {
                    MsgsRepo.getInstance(getApplicationContext()).getStatus();
                    Log.d("MY_SERVICE", "i = IN THREAD");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                stopSelf();
            }
        });
        mWorkingThread.start();
    }
}
