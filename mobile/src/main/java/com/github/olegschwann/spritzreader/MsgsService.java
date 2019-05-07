package com.github.olegschwann.spritzreader;

import android.app.IntentService;
import android.app.Service;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStore;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.olegschwann.spritzreader.ViewModels.MsgsViewModel;
import com.github.olegschwann.spritzreader.repo.ApiRepo;
import com.github.olegschwann.spritzreader.repo.MsgsRepo;

import java.util.concurrent.TimeUnit;

public class MsgsService extends Service {

    private final int mRefreshDelay = 5;
    private Thread mWorkingThread;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MY_SERVICE", "ON START");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MutableLiveData<Integer> data = (MutableLiveData<Integer>)MsgsViewModel.getNewMsgsCount();
        startObserving(data);
        return super.onStartCommand(intent, flags, startId);
    }

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

    public void startObserving(final MutableLiveData<Integer> msgsCount) {
        mWorkingThread = new Thread(new Runnable() {
            public void run() {
                while (!mWorkingThread.isInterrupted()) {
                    MsgsRepo.getInstance(getApplicationContext()).refresh(msgsCount);
                    try {
                        TimeUnit.SECONDS.sleep(mRefreshDelay);
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
