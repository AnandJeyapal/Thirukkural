package com.impiger.thirukkural;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Thirukkural;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by anand on 14/12/15.
 */
public final class Utils {
    static int[] primaryColors;
    static int[] primaryDarkColors;
    static ArrayList<Adhigaram> adhigarams;
    static ArrayList<Thirukkural> kurals;
    static DBHelper dbHelper;


    public static int getColorCode(Context context, int adhigaramIdx) {
        if (primaryColors == null) {
            primaryColors = context.getResources().getIntArray(R.array.primary_colors);
        }
        int colorIndex = adhigaramIdx % 9;
        return primaryColors[colorIndex];
    }

    public static Adhigaram getAdhigramAtIndex(Context context, int adhigaramIdx) {
        if (adhigarams == null) {
            dbHelper = new DBHelper(context);
            adhigarams = dbHelper.getAllAdhigarams();
        }
        return adhigarams.get(adhigaramIdx);
    }

    public static Thirukkural getKuralAtIndex(Context context, int adhigaramIdx) {
        if (kurals == null) {
            dbHelper = new DBHelper(context);
            kurals = dbHelper.getAllKurals();
        }
        return kurals.get(adhigaramIdx);
    }

    public static ArrayList<Adhigaram> getAdhigarams() {
        return adhigarams;
    }

    public static String getTimeString(int hourOfDay, int minutes) {
        String hoursStr = String.format("%02d", hourOfDay);
        String minutesStr = String.format("%02d", minutes);
        return hoursStr + ":" + minutesStr;
    }

    public static int getAdhigaramForKural(int kuralIndex) {
        return kuralIndex / 10;
    }

    public static String[] getRandomKural(Context context) {
        int kuralIndex;
        int adhigaramIndex;
        // Get random kural
        Random random = new Random();
        kuralIndex = random.nextInt(1329 - 0 + 1) + 0;
        adhigaramIndex = Utils.getAdhigaramForKural(kuralIndex);
        Adhigaram adhigaram = Utils.getAdhigramAtIndex(context, adhigaramIndex);
        Thirukkural kural = Utils.getKuralAtIndex(context, kuralIndex);
        String kuralDescription = kural.getKural();
        String adhigaramDescription = adhigaram.getAdhigaramName();
        String[] info = {kuralDescription, adhigaramDescription, String.valueOf(kuralIndex + 1)};
        return info;
    }

    public static void showNotification(Context context) {
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
        resultIntent.putExtra(Constants.EXTRA_CLEAR_NOTIFICATIONS, true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(KuralActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = "defaultId";
        CharSequence channelName = "Life";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotifyMgr.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_events)
                        .setContentTitle(adhigaramDescription).setStyle(new NotificationCompat
                        .BigTextStyle()
                        .bigText(kuralDescription))
                        .addAction(R.drawable.ic_search, "உரை", resultPendingIntent);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotifyMgr.notify(001, notification);
    }

    public static void clearNotifications(Context context) {
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.cancelAll();
    }

    public static void shareContent(Context context, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, getShareContent(context, title));
        context.startActivity(intent);
    }

    public static String getShareContent(Context context, String title) {
        StringBuilder builder = new StringBuilder();
        if(Constants.EXTRA_MORE_THIRUKKURAL.equals(title)) {
            builder.append(context.getString(R.string.thirukkural_more_title_one)).append("\n\n");
            builder.append(context.getString(R.string.thirukkural_more_content_one)).append("\n\n\n");
            builder.append(context.getString(R.string.thirukkural_more_title_two)).append("\n\n");
            builder.append(context.getString(R.string.thirukkural_more_content_two)).append("\n");
        }
        return builder.toString();
    }

}
