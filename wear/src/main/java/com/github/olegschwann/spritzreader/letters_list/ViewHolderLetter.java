package com.github.olegschwann.spritzreader.letters_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.olegschwann.spritzreader.R;
import com.github.olegschwann.spritzreader.database.LetterHeader;

public class ViewHolderLetter extends RecyclerView.ViewHolder {
    private TextView letter_from;
    private TextView letter_subject;
    private String letterId;

    public ViewHolderLetter(View itemView, final LetterClickListener listener) {
        super(itemView);
        this.letter_from = itemView.findViewById(R.id.letter_from);
        this.letter_subject = itemView.findViewById(R.id.letter_subject);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(letterId);
            }
        });
    }

    public void bind(LetterHeader letter) {
        this.letter_from.setText(letter.from);
        this.letter_subject.setText(letter.subject);
        this.letterId = letter.id;
    }
}
