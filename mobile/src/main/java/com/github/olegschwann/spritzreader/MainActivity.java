package com.github.olegschwann.spritzreader;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.olegschwann.spritzreader.repo.AuthRepo;
import com.github.olegschwann.spritzreader.conversion.ConvertionTask;

import ru.mail.auth.sdk.AuthError;
import ru.mail.auth.sdk.AuthResult;
import ru.mail.auth.sdk.MailRuAuthSdk;
import ru.mail.auth.sdk.MailRuCallback;
import ru.mail.auth.sdk.ui.MailRuLoginButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Log.d("MY_APP","NEW FRAGMENTS");
            openLoginFragment();
        }
    }

    public void openLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_container, new LoginFragment())
                .commit();
    }
}
