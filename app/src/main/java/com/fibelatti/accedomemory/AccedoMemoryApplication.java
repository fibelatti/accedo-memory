package com.fibelatti.accedomemory;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class AccedoMemoryApplication extends Application {
    private static final String TAG = AccedoMemoryApplication.class.getSimpleName();
    private static AccedoMemoryApplication app;

    public AccedoMemoryApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        Fabric.with(this, new Crashlytics());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
