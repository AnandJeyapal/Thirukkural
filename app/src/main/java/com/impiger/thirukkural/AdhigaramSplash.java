package com.impiger.thirukkural;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.impiger.thirukkural.model.Constants;

public class AdhigaramSplash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private TextView adhigaramText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adhigaram_splash);
        int[] primaryColors = getResources().getIntArray(R.array.primary_colors);
        View container = findViewById(R.id.container);
        adhigaramText = (TextView) findViewById(R.id.adhigaram_name);
        String adhigaram = getIntent().getExtras().getString(Constants.EXTRA_TITLE);
        int adhigaramId = getIntent().getExtras().getInt(Constants.EXTRA_ADHIGARAM_INDEX, 0);
        if (!TextUtils.isEmpty(adhigaram)) {
            adhigaramText.setText(adhigaram);
        }
        int colorIndex = adhigaramId;
        colorIndex %= 9;
        container.setBackgroundColor(primaryColors[colorIndex]);
        new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(AdhigaramSplash.this, AdhigaramDetailActivity.class);
                        i.putExtras(getIntent().getExtras());
                        startActivity(i);
                        finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
