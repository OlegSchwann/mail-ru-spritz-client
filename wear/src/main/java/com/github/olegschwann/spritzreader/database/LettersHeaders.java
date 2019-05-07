package com.github.olegschwann.spritzreader.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LettersHeaders extends ArrayList<LetterHeader> implements Parcelable {
    public static final String TAG = "letters_headers";

    public LettersHeaders(@NonNull List<LetterHeader> lettersHeaders) {
        super(lettersHeaders);
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        for (LetterHeader letterHeader : this) {
            letterHeader.writeToParcel(dest, flags);
        }
    }

    public static final Creator<LettersHeaders> CREATOR
            = new Creator<LettersHeaders>() {

        @Override
        public LettersHeaders createFromParcel(Parcel in) {
            return (LettersHeaders) in.readArrayList(null);
        }

        @Override
        public LettersHeaders[] newArray(int size) {
            return new LettersHeaders[size];
        }
    };
    //endregion
}
