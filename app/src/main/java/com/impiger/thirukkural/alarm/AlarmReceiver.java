package com.impiger.thirukkural.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.impiger.thirukkural.Utils;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.showNotification(context);
    }

}
