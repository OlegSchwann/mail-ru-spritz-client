package com.github.olegschwann.spritzreader.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MsgsApi {

    class MessagePlain {
        public MessageBodyPlain body;

        public String toString() {
            return "BODY: \n" + body.toString();
        }
    }

    class MessageBodyPlain {
        public InnerBodyPlain body;

        public String toString() {
            return "BODY:\n" + body.toString();
        }
    }

    class InnerBodyPlain {
        String text;
        String subject;

        public String toString() {
            return  "SUBJECT : " + subject + "\n"
                +   "TEXT : " + text + "\n";
        }
    }

    @GET("/api/v1/messages/message")
    Call<MessagePlain> getById(@Query("access_token") String token, @Query(value = "email", encoded = true) String email, @Query("id") String id);
}
