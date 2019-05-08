package com.github.olegschwann.spritzreader.letters_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.olegschwann.spritzreader.R;
import com.github.olegschwann.spritzreader.database.LettersHeaders;

public class AdapterLetter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LettersHeaders letters;

    private LetterClickListener listener;

    AdapterLetter() {
        // Required empty public constructor
    }

    AdapterLetter(LettersHeaders data, LetterClickListener listener) {
        this.letters = data;
        this.listener = listener;
    }

    // Позволяет поддерживать 3 типа элементов в списке.
    // https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return LIST_TYPE.HEADER;
        }
        // letters:            [0, 1, 2, 3, 4]
        // real_items: [header, 0, 1, 2, 3, 4, button]
        if (position == 1 + this.letters.size()) {
            return LIST_TYPE.APPLICATION_BUTTON;
        }
        if (position > 0 && position < 1 + this.letters.size()) {
            return LIST_TYPE.LETTER;
        }
        Log.wtf("getItemViewType", "illegal getItemViewType(" + position + ")");
        throw new RuntimeException();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case LIST_TYPE.HEADER: {
                View view = layoutInflater.inflate(R.layout.letters_list_header, parent, false);
                return new ViewHolderHeader(view);
            }
            case LIST_TYPE.LETTER: {
                View view = layoutInflater.inflate(R.layout.letters_list_one_letter, parent, false);
                return new ViewHolderLetter(view, this.listener);
            }
            case LIST_TYPE.APPLICATION_BUTTON: {
                View view = layoutInflater.inflate(R.layout.letters_list_mail_ru_button, parent, false);
                return new ViewHolderApplicationButton(view);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LIST_TYPE.HEADER: {
                ViewHolderHeader holderLetter = (ViewHolderHeader) holder;

            }
            break;
            case LIST_TYPE.LETTER: {
                ViewHolderLetter holderLetter = (ViewHolderLetter) holder;
                holderLetter.bind(this.letters.get(position - 1));
            }
            break;
            case LIST_TYPE.APPLICATION_BUTTON: {
                ViewHolderApplicationButton holderLetter = (ViewHolderApplicationButton) holder;

            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        return this.letters.size() + 2;
    }
}
