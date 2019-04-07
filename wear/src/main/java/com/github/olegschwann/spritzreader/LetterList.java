package com.github.olegschwann.spritzreader;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;



public class LetterList extends Fragment {
    public static final String TAG = "LetterListrFragment";

    // Список всех писем.
    private WearableRecyclerView lettersRecyclerView;
    // Синяя кнопка с логотипом mail.ru, открывает список писем в приложении на телефоне.
    private ImageButton toMobileApplication;

    // Ссылка на Activity, через которую фрагменты меняют экраы приложения.
    private OnFragmentInteractionListener mListener;

    public LetterList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_letters_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.lettersRecyclerView = (WearableRecyclerView) view.findViewById(R.id.lettersList);
        this.lettersRecyclerView.setHasFixedSize(true);
        this.lettersRecyclerView.setEdgeItemsCenteringEnabled(true);
        this.lettersRecyclerView.setLayoutManager(new WearableLinearLayoutManager(view.getContext()));
        this.lettersRecyclerView.setAdapter(new LetterAdapter(view.getContext(), Letter.testData));

        // this.toMobileApplication = (ImageButton) view.findViewById(R.id.toMobileApplication);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
