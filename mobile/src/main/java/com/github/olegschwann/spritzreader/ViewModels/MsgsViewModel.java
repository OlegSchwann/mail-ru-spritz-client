package com.github.olegschwann.spritzreader.ViewModels;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import android.support.annotation.NonNull;

import com.github.olegschwann.spritzreader.repo.MsgsRepo;

public class MsgsViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> mNewMsgsCount = new MutableLiveData<>();

    public MsgsViewModel(@NonNull Application application) {
        super(application);
        mNewMsgsCount.setValue(0);
    }

    public void startObserving() {
        MsgsRepo.getInstance(getApplication()).startObserving(mNewMsgsCount);
    }

    public void stopObserving() {
        MsgsRepo.getInstance(getApplication()).stopObserving();
    }

    public LiveData<Integer> getNewMsgsCount() {
        return mNewMsgsCount;
    }
}
