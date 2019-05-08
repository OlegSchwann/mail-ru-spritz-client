package com.github.olegschwann.spritzreader.letter_text;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import com.github.olegschwann.spritzreader.Types.NullBundleException;
import com.github.olegschwann.spritzreader.host_activity.InteractionBus;
import com.github.olegschwann.spritzreader.R;

public class FragmentLetterText extends Fragment implements SentenceClickListener{
    public static final String TAG = "FullLetterTextFragment";
    private RecyclerView sentenceRecyclerView;
    private ArrayList<String> data;
    private String letterId;

    public FragmentLetterText() {
        // Required empty public constructor
    }

    // Внешний интерфейс фрагмента, используется для параметризации
    // _до_ отрисовки экрана. Принимает список предложений.
    // Что бы их можно было запихать в android.os.Bundle,
    // все структуры из базы данных реализуют Parcelable.
    public void setSentences(ArrayList<String> sentences, String letterId) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("sentences", sentences);
        bundle.putString("letterId", letterId);
        setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments(); // TODO: может быть надо использовать savedInstanceState?
        if (bundle == null) {
            throw new NullBundleException("Necessary to parameterize FragmentLetterText with setSentences()");
        }
        this.data = bundle.getStringArrayList("sentences");
        this.letterId = bundle.getString("letterId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.letter_text_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.sentenceRecyclerView = view.findViewById(R.id.letter_text_list);
        this.sentenceRecyclerView.setHasFixedSize(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        this.sentenceRecyclerView.setLayoutManager(layoutManager);

        // this.onClick(sentenceNumber) будет вызвано при нажатии на предложение в списке.
        AdapterSentence adapterSentence = new AdapterSentence(this.data, this);
        this.sentenceRecyclerView.setAdapter(adapterSentence);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // region SentenceClickListener
    @Override
    public void onClick(int sentenceIndex) {
        ((InteractionBus)getActivity())
            .transitionToSpritz(this.letterId, sentenceIndex);
    }
    // endregion
}
