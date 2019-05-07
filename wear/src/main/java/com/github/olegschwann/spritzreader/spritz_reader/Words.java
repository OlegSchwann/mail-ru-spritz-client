package com.github.olegschwann.spritzreader.spritz_reader;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Words extends ArrayList<ArrayList<Word>> implements Parcelable {
    public static final String TAG = "Words";

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }

    public static final Parcelable.Creator<Words> CREATOR
            = new Parcelable.Creator<Words>() {

        @Override
        public Words createFromParcel(Parcel in) {
            return (Words) in.readArrayList(null);
        }

        @Override
        public Words[] newArray(int size) {
            return new Words[size];
        }
    };
    //endregion
}
