package com.impiger.thirukkural;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.impiger.thirukkural.alarm.AlarmReceiver;
import com.impiger.thirukkural.listeners.AlarmTimeSetListener;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.view.ShakingBell;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener, AlarmTimeSetListener {

    private TextView hourView;
    private TextView minuteView;
    private SwitchCompat setAlarmSwitch;
    private SharedPreferences sharedPref;
    private ShakingBell alarmBell;
    private CompoundButton.OnCheckedChangeListener listener;
    private boolean isAlarmSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alarm);
        hourView = (TextView) findViewById(R.id.time_view);
        minuteView = (TextView) findViewById(R.id.minute_view);
        setAlarmSwitch = (SwitchCompat) findViewById(R.id.set_alarm_switch);
        alarmBell = (ShakingBell) findViewById(R.id.alarm_bell);
        hourView.setOnClickListener(this);
        sharedPref = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPref.edit();
                if (buttonView.getId() == R.id.set_alarm_switch) {
                    if (isChecked) {
                        int minutes = Integer.valueOf(getMinutes());
                        setAlarm(getHour(), minutes);
                        alarmBell.shake();
                    } else {
                        alarmBell.setAlarmOff();
                    }
                    editor.putBoolean(Constants.ALARM_SET, isChecked);
                    editor.commit();
                }
            }
        };
        setKuralProverb();
        isAlarmSet = sharedPref.getBoolean(Constants.ALARM_SET, false);
        setAlarmSwitch.setOnCheckedChangeListener(null);
        setTime();
        setAlarmSwitch.setChecked(isAlarmSet);
        setAlarmSwitch.setOnCheckedChangeListener(listener);
        if (isAlarmSet) {
            alarmBell.shake();
        } else {
            alarmBell.setAlarmOff();
        }
        alarmBell.setOnClickListener(this);
    }

    private void setKuralProverb() {
        String[] array = getResources().getStringArray(R.array.kural_info);
        String randomInfo = array[new Random().nextInt(array.length)];
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), Constants.FONT_PATH);
        // Applying font
        TextView kuralView = (TextView) findViewById(R.id.kural_proverb);
        kuralView.setText(randomInfo);
        kuralView.setTypeface(tf);
    }

    @Override
    public void onClick(View v) {
        v.setSelected(!v.isSelected());
        if (v.getId() == R.id.time_view) {
            showTimePickerDialog(hourView);
        } else if (v.getId() == R.id.alarm_bell && !setAlarmSwitch.isChecked()) {
            setAlarmSwitch.setChecked(true);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getFragmentManager(), "");
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        setTime(hourOfDay, minute, true);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private AlarmTimeSetListener listener;

        public TimePickerFragment(AlarmTimeSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            listener.onTimeSet(hourOfDay, minute);
        }
    }

    /**
     * Get saved time and set to the time picker
     */
    public void setTime() {
        int hourOfDay = sharedPref.getInt(Constants.SAVED_HOUR, 7);
        int minutes = sharedPref.getInt(Constants.SAVED_MINUTES, 0);
        setTime(hourOfDay, minutes, false);
    }

    private void setTime(final int hourOfDay, int minute, boolean alarm) {
        if (hourOfDay != 0 && minute != 0) {
            int twelveHourOfDay = hourOfDay;
            if (twelveHourOfDay > 12) {
                twelveHourOfDay = hourOfDay - 12;
                minuteView.setText("PM");
            } else {
                minuteView.setText("AM");
            }
            hourView.setText(Utils.getTimeString(twelveHourOfDay, minute));
            if (alarm) {
                setAlarm(hourOfDay, minute);
                setAlarmSwitch.setChecked(true);
            }
        }
    }

    public void setAlarm(int hourOfDay, int minutes) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Clear existing alarm if any.
        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minutes);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        storeTime(hourOfDay, minutes);
    }

    private int getHour() {
        String[] s = hourView.getText().toString().split(":");
        int hour = Integer.valueOf(s[0]);
        hour = isPM() ? hour + 12 : hour;
        return hour;
    }

    private boolean isPM() {
        return "PM".equals(minuteView.getText().toString());
    }

    private String getMinutes() {
        String[] s = hourView.getText().toString().split(":");
        return s[1];
    }

    private void storeTime(int hourOfDay, int minute) {
        SharedPreferences sharedPref = getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Constants.SAVED_HOUR, hourOfDay);
        editor.putInt(Constants.SAVED_MINUTES, minute);
        editor.commit();
    }
}
