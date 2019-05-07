package com.github.olegschwann.spritzreader.ViewModels;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.github.olegschwann.spritzreader.MsgsService;

public class MsgsViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> mNewMsgsCount = new MutableLiveData<>();

    public MsgsViewModel(@NonNull Application application) {
        super(application);
        mNewMsgsCount.setValue(0);
    }

    public void startObserving(Activity activity) {
        activity.startService(new Intent(activity, MsgsService.class));
    }

    public void stopObserving(Activity activity) {
        activity.stopService(new Intent(activity, MsgsService.class));
    }

    public LiveData<Integer> getNewMsgsCount() {
        return mNewMsgsCount;
    }
}
