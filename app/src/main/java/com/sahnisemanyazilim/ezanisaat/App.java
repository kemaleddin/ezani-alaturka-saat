package com.sahnisemanyazilim.ezanisaat;

import com.kemalettinsargin.mylib.BaseApp;
import com.sahnisemanyazilim.ezanisaat.services.DelayedWidgetWorker;

/**
 * Written by "كمال الدّين صارغين"  on 1.09.2018.
 * و من الله توفیق
 */
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        DelayedWidgetWorker.Companion.cancel(this);
        DelayedWidgetWorker.Companion.schedule(this);

    }
}
