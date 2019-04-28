package com.github.olegschwann.spritzreader.letter_text;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.olegschwann.spritzreader.HostActivity.InteractionBus;
import com.github.olegschwann.spritzreader.R;
import com.github.olegschwann.spritzreader.TestData;


public class FragmentLetterText extends Fragment {
    public static final String TAG = "FullLetterTextFragment";
    private RecyclerView sentenceRecyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InteractionBus mListener;

    public FragmentLetterText() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLetterText.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLetterText newInstance(String param1, String param2) {
        FragmentLetterText fragment = new FragmentLetterText();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.letter_text_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.sentenceRecyclerView = (RecyclerView) view.findViewById(R.id.letter_text_list);
        this.sentenceRecyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        this.sentenceRecyclerView.setLayoutManager(layoutManager);
        this.sentenceRecyclerView.setAdapter(new AdapterSentence(view.getContext(), TestData.Sentenses));


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionBus) {
            mListener = (InteractionBus) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionBus");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
