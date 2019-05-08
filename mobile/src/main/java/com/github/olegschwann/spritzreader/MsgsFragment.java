package com.github.olegschwann.spritzreader;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.olegschwann.spritzreader.ViewModels.LoginViewModel;
import com.github.olegschwann.spritzreader.ViewModels.MsgsViewModel;

import org.w3c.dom.Text;


public class MsgsFragment extends Fragment {
    private MsgsViewModel mMsgsViewModel;

    public MsgsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_msgs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMsgsViewModel = ViewModelProviders.of(this).get(MsgsViewModel.class);

        final TextView msgsCount = (TextView)view.findViewById(R.id.msgs_count);

        mMsgsViewModel.getNewMsgsCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                msgsCount.setText(integer.toString());
            }
        });
    }

    public void startObesrving() {
        mMsgsViewModel.startObserving(getActivity());
    }

    public void stopObserving() {
        mMsgsViewModel.stopObserving(getActivity());
    }
}
