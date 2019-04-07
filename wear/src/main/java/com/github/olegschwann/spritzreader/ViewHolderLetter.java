package com.github.olegschwann.spritzreader;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolderLetter extends RecyclerView.ViewHolder {
    private TextView letter_from;
    private TextView letter_subject;

    public ViewHolderLetter(View itemView) {
        super(itemView);
        this.letter_from = itemView.findViewById(R.id.letter_from);
        this.letter_subject = itemView.findViewById(R.id.letter_subject);
    }

    public void bind(Letter letter) {
        this.letter_from.setText(letter.from);
        this.letter_subject.setText(letter.subject);
    }
}