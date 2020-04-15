package com.lirctek.ics;

import android.app.Application;

import com.lirctek.ics.database.ObjectBox;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }
}
