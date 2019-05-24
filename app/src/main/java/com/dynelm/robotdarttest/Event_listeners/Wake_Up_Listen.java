package com.dynelm.robotdarttest.Event_listeners;

import com.dynelm.robotdarttest.BasicActivity;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Wake_Up_Listen {
    public interface Wake_Up_Listerner{
         public void OnWake_Up_Listerner(boolean b);
    }
    private Wake_Up_Listerner wake_up_listerner;

    public Wake_Up_Listen() {
        this.wake_up_listerner = null;
    }

    public void setWake_up_listerner(Wake_Up_Listerner wake_up_listerner) {
        this.wake_up_listerner = wake_up_listerner;
    }
}
