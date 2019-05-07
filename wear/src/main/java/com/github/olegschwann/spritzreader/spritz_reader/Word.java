package com.github.olegschwann.spritzreader.spritz_reader;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Word implements Parcelable{
    @Nullable
    @SerializedName("l")
    public String left;

    @NonNull
    @SerializedName("c")
    public String center = ".";

    @Nullable
    @SerializedName("r")
    public String right;

    @Nullable
    @SerializedName("d")
    public Integer delay;

    //region Parcelable
    private Word(Parcel in) {
        this.left = in.readString();

        this.center = in.readString();

        this.right = in.readString();

        @Nullable int delay = in.readInt();
        this.delay = (delay == 1 ? null : delay);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.left);

        dest.writeString(this.center);

        dest.writeString(this.right);

        // delay - множитель относительно установленного времени демонстрации.
        // 1 - нейтральный элемент.
        dest.writeInt(this.delay != null ? this.delay : 1);
    }

    public static final Parcelable.Creator<Word> CREATOR
            = new Parcelable.Creator<Word>() {

        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
    //endregion
}
