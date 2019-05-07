package com.github.olegschwann.spritzreader.repo;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.github.olegschwann.spritzreader.MainActivity;

import ru.mail.auth.sdk.AuthResult;
import ru.mail.auth.sdk.MailRuAuthSdk;

public class AuthRepo {
    public static final String IS_LOGGED_IN  = "isLoggedIn";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String AUTH_CODE = "authCode";
    public static final String USER_EMAIL = "email";

    private final Context mContext;
    private static AuthRepo mRepo;

    public static AuthRepo getInstance(Context context) {
        if (mRepo == null) {
            Log.d("MY_APP", "New repo created");
            mRepo = new AuthRepo(context);
        }
        return mRepo;
    }

    private AuthRepo(Context context) {
        mContext = context;
    }

    public boolean isLoggedIn() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(IS_LOGGED_IN, false);
    }

    public void logout() {
        PreferenceManager.getDefaultSharedPreferences(mContext).edit()
                .putBoolean(IS_LOGGED_IN, false).apply();
    }

    public void startLoginFlow(Fragment fragment) {
        MailRuAuthSdk.initialize(mContext);
        MailRuAuthSdk.getInstance().startLogin(fragment);
    }

    public void saveResult(AuthResult result) {
        Log.d("MY_APP", result.getAccess());
        PreferenceManager.getDefaultSharedPreferences(mContext).edit()
                .putString(ACCESS_TOKEN, result.getAccess())
                .putString(REFRESH_TOKEN, result.getRefresh())
                .putString(AUTH_CODE, result.getAuthCode())
                .putBoolean(IS_LOGGED_IN, true)
                .apply();
    }

    public static class AuthParams {
        private String mAccess;
        private String mRefresh;
        private String mAuthCode;

        public AuthParams(String access, String refresh, String authCode) {
            mAccess = access;
            mRefresh = refresh;
            mAuthCode = authCode;
        }

        public String getAccess() {
            return mAccess;
        }

        public String getRefresh() {
            return mRefresh;
        }

        public String getAuthCode() {
            return mAuthCode;
        }

    }

    public String getAccessToken() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(ACCESS_TOKEN, "");
    }
    public String getEmail() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(USER_EMAIL, "");

    }

    public void saveEmail(String email) {
        PreferenceManager.getDefaultSharedPreferences(mContext).edit()
                .putString(USER_EMAIL, email)
                .apply();
    }
}
