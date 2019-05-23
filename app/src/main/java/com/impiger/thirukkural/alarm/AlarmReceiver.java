package com.impiger.thirukkural.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.impiger.thirukkural.KuralActivity;
import com.impiger.thirukkural.R;
import com.impiger.thirukkural.Utils;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Thirukkural;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent resultIntent = new Intent(context, KuralActivity.class);
        // Get random kural
        Random random = new Random();
        int kuralIndex = random.nextInt(1330);
        int adhigaramIndex = Utils.getAdhigaramForKural(kuralIndex);
        Adhigaram adhigaram = Utils.getAdhigramAtIndex(context, adhigaramIndex);
        Thirukkural kural = Utils.getKuralAtIndex(context, kuralIndex);
        String kuralDescription = kural.getKural();
        String adhigaramDescription = adhigaram.getAdhigaramName();
        resultIntent.putExtra(Constants.EXTRA_START_ID, kuralIndex);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(KuralActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_events)
                        .setContentTitle(adhigaramDescription).setStyle(new NotificationCompat
                        .BigTextStyle()
                        .bigText(kuralDescription))
                        .addAction(R.drawable.ic_search, "உரை", resultPendingIntent);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void showNotification(Context context) {
        Intent resultIntent = new Intent(context, KuralActivity.class);
        // Get random kural
        Random random = new Random();
        int kuralIndex = random.nextInt(1330);
        int adhigaramIndex = Utils.getAdhigaramForKural(kuralIndex);
        Adhigaram adhigaram = Utils.getAdhigramAtIndex(context, adhigaramIndex);
        Thirukkural kural = Utils.getKuralAtIndex(context, kuralIndex);
        String kuralDescription = kural.getKural();
        String adhigaramDescription = adhigaram.getAdhigaramName();
        resultIntent.putExtra(Constants.EXTRA_START_ID, kuralIndex);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(KuralActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_events)
                        .setContentTitle(adhigaramDescription).setStyle(new NotificationCompat
                        .BigTextStyle()
                        .bigText(kuralDescription))
                        .addAction(R.drawable.ic_search, "உரை", resultPendingIntent);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
