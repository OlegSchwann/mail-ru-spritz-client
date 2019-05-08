package com.github.olegschwann.spritzreader.letter_text;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import com.github.olegschwann.spritzreader.R;

public class AdapterSentence extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> sentences;
    private SentenceClickListener listener;

    AdapterSentence(ArrayList<String> data, SentenceClickListener listener) {
        this.sentences = data;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return LIST_TYPE.ALIGNMENT;
        }
        // sentences:             [0, 1, 2, 3, 4]
        // real_items: [alignment, 0, 1, 2, 3, 4, button]
        if (position == 1 + this.sentences.size()) {
            return LIST_TYPE.APPLICATION_BUTTON;
        }
        if (position > 0 && position < 1 + this.sentences.size() ) {
            return LIST_TYPE.SENTENCE;
        }
        throw new RuntimeException();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case LIST_TYPE.ALIGNMENT: {
                View view = layoutInflater.inflate(R.layout.letter_text_alignment, parent, false);
                return new ViewHolderAlignment(view);
            }
            case LIST_TYPE.SENTENCE: {
                View view = layoutInflater.inflate(R.layout.letter_text_sentence, parent, false);
                return new ViewHolderSentence(view, listener);
            }
            case LIST_TYPE.APPLICATION_BUTTON: {
                View view = layoutInflater.inflate(R.layout.letter_text_mai_ru_button, parent, false);
                return new ViewHolderApplicationButton(view);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LIST_TYPE.ALIGNMENT: {
                ViewHolderAlignment holderLetter = (ViewHolderAlignment) holder;

            } break;
            case LIST_TYPE.SENTENCE: {
                ViewHolderSentence holderLetter = (ViewHolderSentence) holder;
                int sentencePosition = position - 1;
                holderLetter.bind(this.sentences.get(sentencePosition), sentencePosition);
            } break;
            case LIST_TYPE.APPLICATION_BUTTON: {
                ViewHolderApplicationButton holderLetter = (ViewHolderApplicationButton) holder;

            } break;
        }
    }

    @Override
    public int getItemCount() {
        return this.sentences.size() + 2;
    }
}
