package com.hackaton.bri.ragatee.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hackaton.bri.ragatee.model.User;

public class AppPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "ragateee";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String TOKEN_AUTH = "TokenAuth";
    private static final String USER_LOGIN_STATE = "UserLoginState";
    private static final String USER_LOGIN = "UserLogin";

    public AppPreference(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor = pref.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserLoginState(boolean loginState) {
        editor = pref.edit();
        editor.putBoolean(USER_LOGIN_STATE, loginState);
        editor.apply();
    }

    public boolean getUserLoginState() {
        return pref.getBoolean(USER_LOGIN_STATE, false);
    }

    public void setTokenAuth(String tokenAuth) {
        editor = pref.edit();
        editor.putString(TOKEN_AUTH, tokenAuth);
        editor.apply();
    }

    public String getTokenAuth() {
        return pref.getString(TOKEN_AUTH, "");
    }

    public void setUserLogin(User user) {
        editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(USER_LOGIN, json);
        editor.apply();
    }

    public User getUserLogin() {
        Gson gson= new Gson();
        String json = pref.getString(USER_LOGIN, "");
        return gson.fromJson(json, User.class);
    }

}
