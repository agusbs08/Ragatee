package com.hackaton.bri.ragatee.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hackaton.bri.ragatee.app.AppPreference;

public class AppStateService extends Service {
    private AppPreference mAppPreference;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppPreference = new AppPreference(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("Service", "App Closed");
        mAppPreference.setUserLoginState(false);
        mAppPreference.setUserLogin(null);
        stopSelf();
    }
}
