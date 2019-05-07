package com.github.olegschwann.spritzreader.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatusApi {
    class StatusPlain {
        public int status;
        public String email;
        public int last_modified;

        public List<MsgStatusPlain> messages;

        @Override
        public String toString() {
            return "STATUS : " + status + " \n" +
                    "EMAIL : " + email + " \n" +
                    "LAST_MODIFIED : " + last_modified + "\n" +
                    "Messages : " + messages.toString() + "\n";

        }

    }

    class MsgStatusPlain {
        public int id;
        public String subject;
        public String snippet;

        @Override
        public String toString() {
            return "MESSAGE: \n\t ID : " + id + "\n" +
                    "\t subject : " + subject + "\n" +
                    "\t subject : " + snippet + "\n";
        }
    }

    @GET("/api/v1/messages/status")
    Call<StatusPlain> getStatus(@Query("access_token") String token);
}
