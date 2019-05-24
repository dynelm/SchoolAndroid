package com.dynelm.robotdarttest.Util;

import android.widget.TextView;

/**
 * Created by Administrator on 2017/12/30 0030.
 */

public class getPowerThread  {
    private BatteryView batteryView;
    private TextView power_text;

    public getPowerThread(BatteryView batteryView, TextView power_text) {
        this.batteryView = batteryView;
        this.power_text = power_text;
    }
}
