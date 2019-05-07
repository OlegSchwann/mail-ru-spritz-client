package com.github.olegschwann.spritzreader;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
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
import android.widget.Toast;

import com.github.olegschwann.spritzreader.ViewModels.LoginViewModel;
import com.github.olegschwann.spritzreader.repo.AuthRepo;
import com.github.olegschwann.spritzreader.repo.MsgsRepo;

import ru.mail.auth.sdk.AuthError;
import ru.mail.auth.sdk.AuthResult;
import ru.mail.auth.sdk.MailRuAuthSdk;
import ru.mail.auth.sdk.MailRuCallback;
import ru.mail.mailapp.service.oauth.OAuthAccountInfo;
import ru.mail.mailapp.service.oauth.OAuthInfo;


public class LoginFragment extends Fragment {
    private LoginViewModel mLoginViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        final Button logoutBtn = view.findViewById(R.id.log_out_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginViewModel.logout();
            }
        });

        final TextView emailText = (TextView)view.findViewById(R.id.user_email);

        mLoginViewModel.getLoginState().observe(this, new Observer<LoginViewModel.LoginState>() {
            @Override
            public void onChanged(@Nullable LoginViewModel.LoginState loginState) {
                switch(loginState) {
                    case NONE:
                        if (!isLoggedIn()) {
                            startLoginFlow();
                        }
                        Log.d("MY_APP", "NON LOGGED");
                        ((ServiceStarter)getActivity()).stopMsgsService();
                        break;
                    case LOGGED:
                        //MsgsRepo.getInstance(view.getContext()).getStatus();
                        Log.d("MY_APP", "LOGGED");
                        ((ServiceStarter)getActivity()).startMsgsService();
                        break;
                    default:
                        //TODO Error - msg
                        break;
                }
            }
        });

        mLoginViewModel.getUserEmail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                emailText.setText(s);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(!MailRuAuthSdk.getInstance().handleActivityResult(requestCode, resultCode, data, new MailRuCallback<AuthResult, AuthError>() {
            @Override
            public void onResult(AuthResult result) {
                saveResult(result);
            }

            @Override
            public void onError(AuthError error) {
                Toast.makeText(getActivity(), error.getErrorReason() + " " + error.name(), Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void startLoginFlow() {
        mLoginViewModel.startLoginFlow(this);
    }

    public boolean isLoggedIn() {
        return mLoginViewModel.isLoggedIn();
    }

    public void saveResult(AuthResult result) {
        mLoginViewModel.saveResult(result);
    }
}
