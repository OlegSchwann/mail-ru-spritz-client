package com.github.olegschwann.spritzreader.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MsgsApi {

    class MessagePlain {

    }

    @GET("message")
    Call<MessagePlain> getById(@Query("acces_token") String token, @Query("id") int id);
}
