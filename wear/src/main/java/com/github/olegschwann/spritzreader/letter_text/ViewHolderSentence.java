package com.github.olegschwann.spritzreader.letter_text;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.olegschwann.spritzreader.R;

public class ViewHolderSentence extends RecyclerView.ViewHolder {
    private TextView sentence;

    public ViewHolderSentence(@NonNull View itemView) {
        super(itemView);
        this.sentence = itemView.findViewById(R.id.sentence);
    }

    public void bind(String sentence) {
        this.sentence.setText(sentence);
    }
}
