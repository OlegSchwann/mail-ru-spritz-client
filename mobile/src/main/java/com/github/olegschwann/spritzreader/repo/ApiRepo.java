package com.github.olegschwann.spritzreader.repo;

import android.content.Context;

import com.github.olegschwann.spritzreader.apis.MsgsApi;
import com.github.olegschwann.spritzreader.apis.StatusApi;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiRepo {
    private final MsgsApi mMsgsApi;
    private final StatusApi mStatusApi;
    private final OkHttpClient mOkHttpClient;

    private static ApiRepo mRepo;

    private ApiRepo() {
        mOkHttpClient = new OkHttpClient().newBuilder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .baseUrl(new HttpUrl.Builder().scheme("https")
                        .host("aj-https.mail.ru")
                        .build())
                .client(mOkHttpClient)
                .build();

        mMsgsApi = retrofit.create(MsgsApi.class);
        mStatusApi = retrofit.create(StatusApi.class);
    }

    public static ApiRepo getInstance() {
        if (mRepo == null) {
            mRepo = new ApiRepo();
        }
        return mRepo;
    }

    public MsgsApi getMsgsApi() {
        return mMsgsApi;
    }

    public StatusApi getStatusApi() {
        return mStatusApi;
    }
}
