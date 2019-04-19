package com.github.olegschwann.spritzreader.letter_text;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewHolderSentence extends RecyclerView.ViewHolder {
    private String sentence;

    public ViewHolderSentence(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(String sentence) {
        this.sentence = sentence;
    }
}
