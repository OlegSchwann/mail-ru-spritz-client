package com.github.olegschwann.spritzreader.repo;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;

import com.github.olegschwann.spritzreader.MainActivity;

import ru.mail.auth.sdk.AuthResult;
import ru.mail.auth.sdk.MailRuAuthSdk;

public class AuthRepo {
    public static final String IS_LOGGED_IN  = "isLoggedIn";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String AUTH_CODE = "authCode";

    private final Context mContext;

    public AuthRepo(Context context) {
        mContext = context;
    }

    public boolean isLoggedIn() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(IS_LOGGED_IN, false);
    }

    public void startLoginFlow(Activity activity) {
        MailRuAuthSdk.initialize(mContext);
        MailRuAuthSdk.getInstance().startLogin(activity);
    }

    public void saveResult(AuthResult result) {
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
}
