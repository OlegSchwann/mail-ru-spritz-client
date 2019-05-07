package com.github.olegschwann.spritzreader.ViewModels;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.olegschwann.spritzreader.MsgsFragment;
import com.github.olegschwann.spritzreader.MsgsService;

public class MsgsViewModel extends AndroidViewModel {
    private static MutableLiveData<Integer> mNewMsgsCount = new MutableLiveData<>();

    public MsgsViewModel(@NonNull Application application) {
        super(application);
        mNewMsgsCount.setValue(0);
    }

    public void startObserving(Activity activity) {
        Intent intent = new Intent(activity, MsgsService.class);
        activity.startService(intent);
    }

    public void stopObserving(Activity activity) {
        activity.stopService(new Intent(activity, MsgsService.class));
    }

    public static LiveData<Integer> getNewMsgsCount() {
        return mNewMsgsCount;
    }
}
