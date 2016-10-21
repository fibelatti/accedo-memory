package com.fibelatti.accedomemory;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.fibelatti.accedomemory.db.Database;

import io.fabric.sdk.android.Fabric;

public class AccedoMemoryApplication extends Application {
    private static final String TAG = AccedoMemoryApplication.class.getSimpleName();
    private static AccedoMemoryApplication app;
    public static Database db;

    public AccedoMemoryApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        db = new Database(this);
        db.open();

        Fabric.with(this, new Crashlytics());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        db.close();
    }
}
