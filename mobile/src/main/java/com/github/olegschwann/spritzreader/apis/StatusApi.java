package com.github.olegschwann.spritzreader.apis;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface StatusApi {
    class StatusPlain {
        public int status;
        public String email;
        public int last_modified;

        public StatusBodyPlain body;

        @Override
        public String toString() {
            return "STATUS : " + status + " \n" +
                    "EMAIL : " + email + " \n" +
                    "LAST_MODIFIED : " + last_modified + "\n" +
                    "Body : " + body.toString() + "\n";

        }

    }

    class StatusBodyPlain {
        public List<MsgStatusPlain> messages =  null;

        @Override
        public String toString() {
            return "MESSAGES : " + messages.toString();
        }
    }

    class MsgStatusPlain {
        public String id;
        public String subject;
        public String snippet;

        @Override
        public String toString() {
            return "MESSAGE: \n\t ID : " + id + "\n" +
                    "\t subject : " + subject + "\n" +
                    "\t snippet : " + snippet + "\n";
        }
    }

    @GET("/api/v1/messages/status")
    Call<StatusPlain> getStatus(@Query("access_token") String token, @QueryMap Map<String, Boolean> filters);
}
