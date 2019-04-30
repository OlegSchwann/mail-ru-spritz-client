package com.github.olegschwann.spritzreader;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.olegschwann.spritzreader.repo.AuthRepo;

import ru.mail.auth.sdk.AuthError;
import ru.mail.auth.sdk.AuthResult;
import ru.mail.auth.sdk.MailRuAuthSdk;
import ru.mail.auth.sdk.MailRuCallback;
import ru.mail.auth.sdk.ui.MailRuLoginButton;

public class MainActivity extends AppCompatActivity {

    private AuthRepo mAuthRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuthRepo = new AuthRepo(getApplicationContext());
        if (!mAuthRepo.isLoggedIn()) {
            mAuthRepo.startLoginFlow(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!MailRuAuthSdk.getInstance().handleActivityResult(requestCode, resultCode, data, new MailRuCallback<AuthResult, AuthError>() {
            @Override
            public void onResult(AuthResult result) {
                mAuthRepo.saveResult(result);
                Toast.makeText(MainActivity.this, "Success " + result.getAuthCode(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(AuthError error) {
                Toast.makeText(MainActivity.this, error.getErrorReason() + " " + error.name(), Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
