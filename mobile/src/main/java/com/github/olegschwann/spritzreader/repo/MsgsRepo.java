package com.github.olegschwann.spritzreader.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.github.olegschwann.spritzreader.apis.MsgsApi;
import com.github.olegschwann.spritzreader.apis.StatusApi;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MsgsRepo {
    // private final MsgsDao mMsgsDao;
    private final Context mContext;
    private final MsgsApi mMsgsApi;
    private final StatusApi mStatusApi;

    private static MsgsRepo mRepo;

    private MsgsRepo(Context context) {
        mContext = context;
        //mMsgsDao = AppDatabase.getInstance(context).getMsgsDao();
        mMsgsApi = ApiRepo.getInstance().getMsgsApi();
        mStatusApi = ApiRepo.getInstance().getStatusApi();
    }

    public static MsgsRepo getInstance(Context context) {
        if (mRepo == null) {
            mRepo = new MsgsRepo(context);
        }
        return mRepo;
    }

    public void getStatus() {
        Call<StatusApi.StatusPlain> call = mStatusApi.getStatus(AuthRepo.getInstance(mContext).getAccessToken());
        call.enqueue(new Callback<StatusApi.StatusPlain>() {
            @Override
            public void onResponse(Call<StatusApi.StatusPlain> call, Response<StatusApi.StatusPlain> response) {
                if (response.isSuccessful()) {
                    StatusApi.StatusPlain status = response.body();
                    Log.d("MY_APP", "Hello " + response.message());
                    if (status.status == 200) {
                        //Log.d("MY_APP", response.body().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusApi.StatusPlain> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getLogin(final MutableLiveData<String> data) {
        Call<StatusApi.StatusPlain> call = mStatusApi.getStatus(AuthRepo.getInstance(mContext).getAccessToken());
        call.enqueue(new Callback<StatusApi.StatusPlain>() {
            @Override
            public void onResponse(Call<StatusApi.StatusPlain> call, Response<StatusApi.StatusPlain> response) {
                if (response.isSuccessful()) {
                    StatusApi.StatusPlain status = response.body();
                    if (status.status == 200) {
                        data.postValue(status.email);
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusApi.StatusPlain> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
