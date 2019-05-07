package com.github.olegschwann.spritzreader.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.github.olegschwann.spritzreader.apis.MsgsApi;
import com.github.olegschwann.spritzreader.apis.StatusApi;
import com.google.android.gms.common.api.Api;
import com.squareup.moshi.JsonDataException;

import java.util.HashMap;
import java.util.Map;

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

    public void refresh(final MutableLiveData<Integer> msgsCount) {
        Map<String, Boolean> filters = new HashMap<>();
        filters.put("unread", true);
        Call<StatusApi.StatusPlain> call = mStatusApi.getStatus(AuthRepo.getInstance(mContext).getAccessToken(), filters);
        call.enqueue(new Callback<StatusApi.StatusPlain>() {
            @Override
            public void onResponse(Call<StatusApi.StatusPlain> call, Response<StatusApi.StatusPlain> response) {
                if (response.isSuccessful()) {
                    try {
                        StatusApi.StatusPlain status = response.body();
                        if (status.status == 200) {
                            for (int i = 0; i < status.body.messages.size(); i++) {
                                Log.d("MY_APP", "IN LOOP");
                                //getMsgById(status.body.messages.get(i).id);
                                msgsCount.postValue(status.body.messages.size());
                            }
                        }
                    } catch (JsonDataException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusApi.StatusPlain> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMsgById(String id) {
        Call<MsgsApi.MessagePlain> call = mMsgsApi.getById(AuthRepo.getInstance(mContext).getAccessToken(),
                AuthRepo.getInstance(mContext).getEmail(), id);
        call.enqueue(new Callback<MsgsApi.MessagePlain>() {
            @Override
            public void onResponse(Call<MsgsApi.MessagePlain> call, Response<MsgsApi.MessagePlain> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("MY_APP", response.body().toString());
                }
                Log.d("MY_APP", "IN RESPONSE MSG");
                Log.d("MY_APP", call.request().toString());
            }

            @Override
            public void onFailure(Call<MsgsApi.MessagePlain> call, Throwable t) {
                Log.d("MY_APP", "IN RESPONSE MSG FAIL");
                t.printStackTrace();
            }
        });
    }

    /*public void getStatus() {
        Call<StatusApi.StatusPlain> call = mStatusApi.getStatus(AuthRepo.getInstance(mContext).getAccessToken(), new StatusApi.Filters(true));
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
    }*/

    public void getLogin(Callback<StatusApi.StatusPlain> callback) {
        Call<StatusApi.StatusPlain> call = mStatusApi.getStatus(AuthRepo.getInstance(mContext).getAccessToken(), new HashMap<String, Boolean>());
        call.enqueue(callback);
    }
}
