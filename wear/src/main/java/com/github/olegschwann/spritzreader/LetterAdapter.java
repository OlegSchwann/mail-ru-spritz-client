package com.github.olegschwann.spritzreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type

public class LetterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // TODO: хранить письма в файловой системе
    private Letter[] letters;
    private Context context;

    LetterAdapter() {
        // Required empty public constructor
    }

    LetterAdapter(Context context, Letter[] data) {
        this.context = context;
        this.letters = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return LETTER_LIST.HEADER;
        }
        // letters:            [0, 1, 2, 3, 4]
        // real_items: [header, 0, 1, 2, 3, 4, button]
        if (position == 1 + this.letters.length) {
            return LETTER_LIST.APPLICATION_BUTTON;
        }
        if (position > 0 && position < 1 + this.letters.length ) {
            return LETTER_LIST.LETTER;
        }
        Log.wtf("getItemViewType", "illegal getItemViewType(" + position +")");
        throw new RuntimeException();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case LETTER_LIST.HEADER: {
                View view = layoutInflater.inflate(R.layout.letters_list_header, parent, false);
                return new ViewHolderHeader(view);
            }
            case LETTER_LIST.LETTER: {
                View view = layoutInflater.inflate(R.layout.letters_list_one_letter, parent, false);
                return new ViewHolderLetter(view);
            }
            case LETTER_LIST.APPLICATION_BUTTON: {
                View view = layoutInflater.inflate(R.layout.letters_list_mail_ru_button, parent, false);
                return new ViewHolderApplicationButton(view);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LETTER_LIST.HEADER: {
                ViewHolderHeader holderLetter = (ViewHolderHeader) holder;

            } break;
            case LETTER_LIST.LETTER: {
                ViewHolderLetter holderLetter = (ViewHolderLetter) holder;
                holderLetter.bind(letters[position - 1]);
            } break;
            case LETTER_LIST.APPLICATION_BUTTON: {
                ViewHolderApplicationButton holderLetter = (ViewHolderApplicationButton) holder;

            } break;
        }
    }

    @Override
    public int getItemCount() {
        return this.letters.length + 2;
    }
}
