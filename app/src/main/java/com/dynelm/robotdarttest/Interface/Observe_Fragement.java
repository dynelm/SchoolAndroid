package com.dynelm.robotdarttest.Interface;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public interface Observe_Fragement {
    void update(List<Float> floatList);
    void update_xfROSwords(String string);
    void update_PollingState(boolean b);
    void update_Map(Bitmap bitmap);
}
