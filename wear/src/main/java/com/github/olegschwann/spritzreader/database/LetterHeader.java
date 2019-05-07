package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class LetterHeader implements Parcelable {
    @ColumnInfo(name = "id")
    @NonNull
    public String id;

    @ColumnInfo(name = "from")
    @NonNull
    public String from; // имя отправителя

    @ColumnInfo(name = "subject")
    @NonNull
    public String subject; // тема письма

    public LetterHeader(String id, String from, String subject){
        this.id = id;
        this.from = from;
        this.subject = subject;
    }

    //region Parcelable
    // Объяснение Parcelable
    // https://guides.codepath.com/android/using-parcelable
    private LetterHeader(Parcel in){
        this.id = in.readString();
        this.from = in.readString();
        this.subject = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.from);
        dest.writeString(this.subject);
    }

    public static final Creator<LetterHeader> CREATOR
            = new Creator<LetterHeader>() {

        @Override
        public LetterHeader createFromParcel(Parcel in) {
            return new LetterHeader(in);
        }

        @Override
        public LetterHeader[] newArray(int size) {
            return new LetterHeader[size];
        }
    };
    //endregion
}
