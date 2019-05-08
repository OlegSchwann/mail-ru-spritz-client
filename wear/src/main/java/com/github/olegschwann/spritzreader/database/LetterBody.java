package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import com.google.gson.Gson;

import com.github.olegschwann.spritzreader.spritz_reader.Word;

public class LetterBody implements Parcelable {
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "json_body")
    public String jsonBody; // тело письма, уже размеченное в JSON Spritz формат.

    // Выдаёт
    public ArrayList<String> getSentenceBody() {
        ArrayList<String> result = new ArrayList<String>();
        for (Word[] i : (new Gson()).fromJson(this.jsonBody, Word[][].class)) {
            ArrayList<String> sentence = new ArrayList<String>();
            for (Word j : i) {
                sentence.add(
                    (j.left != null ? j.left : "") +
                    j.center +
                    (j.right != null ? j.right : "")
                );
            }
            result.add(String.join(" ", sentence));
        }
        return result;
    }

    public LetterBody(String id, String jsonBody) {
        this.id = id;
        this.jsonBody = jsonBody;
    }

    //region Parcelable
    private LetterBody(Parcel in) {
        this.id = in.readString();
        this.jsonBody = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.jsonBody);
    }

    public static final Creator<LetterBody> CREATOR
            = new Creator<LetterBody>() {

        @Override
        public LetterBody createFromParcel(Parcel in) {
            return new LetterBody(in);
        }

        @Override
        public LetterBody[] newArray(int size) {
            return new LetterBody[size];
        }
    };
    //endregion
}
