package com.github.olegschwann.spritzreader.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MsgDBStored {

    @PrimaryKey
    @NonNull
    private String mId;
    private String mOwnerEmail;
    private String mBody;

    public MsgDBStored(String id, String ownerEmail, String body) {
        mId = id;
        mOwnerEmail = ownerEmail;
        mBody = body;
    }

    //public MsgDBStored() {}

    public String getId() {
        return mId;
    }

    public String getOwnerEmail() {
        return mOwnerEmail;
    }

    public String getBody() {
        return mBody;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setOwnerEmail(String email) {
        mOwnerEmail = email;
    }

    public void setmBody(String body) {
        mBody = body;
    }
}
