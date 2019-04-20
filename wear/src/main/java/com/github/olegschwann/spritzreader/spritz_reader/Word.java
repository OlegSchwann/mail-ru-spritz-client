package com.github.olegschwann.spritzreader.spritz_reader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

class Word {
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
}
