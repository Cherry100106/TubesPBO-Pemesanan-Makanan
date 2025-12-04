package com.tubes.util;

import java.util.ArrayList;
import java.util.List;

public class OrderEventBroker {
    private static final List<Runnable> listeners = new ArrayList<>();

    public static void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public static void fireOrderSaved() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}