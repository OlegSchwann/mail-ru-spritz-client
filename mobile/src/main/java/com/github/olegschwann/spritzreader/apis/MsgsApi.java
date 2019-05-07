package com.github.olegschwann.spritzreader.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MsgsApi {

    class MessagePlain {
        //public MessageBodyPlain body;
        public String body;
        public String email;

        public String toString() {
            return body + "\n" + email;
            //return "";
        }
    }

    class MessageBodyPlain {
        public InnerBodyPlain body;
    }

    class InnerBodyPlain {
        String text;
    }

    @GET("/api/v1/messages/message")
    Call<MessagePlain> getById(@Query("acces_token") String token, @Query(value = "email", encoded = true) String email, @Query("id") String id);
}
