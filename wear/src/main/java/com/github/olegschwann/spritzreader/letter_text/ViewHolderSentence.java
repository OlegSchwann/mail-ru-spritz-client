package com.github.olegschwann.spritzreader.letter_text;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.olegschwann.spritzreader.R;

public class ViewHolderSentence extends RecyclerView.ViewHolder {
    private TextView sentence;
    // Номер предложения, который надо веруть при нажатии.
    private int position;

    public ViewHolderSentence(@NonNull View itemView, final SentenceClickListener listener) {
        super(itemView);
        this.sentence = itemView.findViewById(R.id.sentence);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    public void bind(String sentence, int position) {
        this.position = position;
        this.sentence.setText(sentence);
    }
}
