package com.fibelatti.accedomemory.helpers;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusHelper {
    private static final Object syncLock = new Object();
    private static BusHelper instance;
    private final Bus bus = new Bus(ThreadEnforcer.MAIN);

    private BusHelper() {
    }

    public static BusHelper getInstance() {
        if (instance == null) {
            synchronized (syncLock) {
                if (instance == null)
                    instance = new BusHelper();
            }
        }
        return instance;
    }

    public Bus getBus() {
        return bus;
    }
}
