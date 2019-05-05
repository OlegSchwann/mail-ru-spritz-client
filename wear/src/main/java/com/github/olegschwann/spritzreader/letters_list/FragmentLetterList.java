package com.github.olegschwann.spritzreader.letters_list;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.olegschwann.spritzreader.host_activity.InteractionBus;
import com.github.olegschwann.spritzreader.R;
import com.github.olegschwann.spritzreader.OldTestData;


public class FragmentLetterList extends Fragment {
    public static final String TAG = "LetterListrFragment";

    // Список всех писем.
    private WearableRecyclerView lettersRecyclerView;
    // Синяя кнопка с логотипом mail.ru, открывает список писем в приложении на телефоне.
    private ImageButton toMobileApplication;

    // Ссылка на Activity, через которую фрагменты меняют экраы приложения.
    private InteractionBus mListener;

    public FragmentLetterList() {
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
        return inflater.inflate(R.layout.letters_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.lettersRecyclerView = (WearableRecyclerView) view.findViewById(R.id.lettersList);
        this.lettersRecyclerView.setHasFixedSize(false);
        this.lettersRecyclerView.setEdgeItemsCenteringEnabled(false);

        CustomScrollingLayoutCallback customScrollingLayoutCallback = new CustomScrollingLayoutCallback();
        WearableLinearLayoutManager layoutManager = new WearableLinearLayoutManager(view.getContext(), customScrollingLayoutCallback);
        this.lettersRecyclerView.setLayoutManager(layoutManager);
        this.lettersRecyclerView.setAdapter(new AdapterLetter(view.getContext(), OldTestData.FromSubject));

        // this.toMobileApplication = (ImageButton) view.findViewById(R.id.toMobileApplication);
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
