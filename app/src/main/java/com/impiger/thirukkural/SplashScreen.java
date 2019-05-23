package com.impiger.thirukkural;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by anand on 09/12/15.
 */
public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private TextView title;
    private TextView subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.sub_title);
        // Font path
        String fontPath = "fonts/tamil.ttf";

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        title.setTypeface(tf);
        subtitle.setTypeface(tf);
        //generateAdhigarams();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public String loadPartsFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("detail.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void generateAdhigarams() {
        String jsonStr = loadPartsFromAsset();
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONObject partGroupObj = jsonObj.getJSONObject("section");
                // Getting JSON Array node
                JSONArray partsArray = partGroupObj.getJSONArray("detail");

                // looping through All parts
                for (int i = 0; i < partsArray.length(); i++) {

                    JSONObject partObject = partsArray.getJSONObject(i);
                    String partName = partObject.getString("name");

                    JSONObject partGroupObject = partObject.getJSONObject("chapterGroup");
                    JSONArray iyalArray = partGroupObject.getJSONArray("detail");

                    for (int j = 0; j < iyalArray.length(); j++) {
                        JSONObject iyalObject = iyalArray.getJSONObject(j);
                        String iyalName = iyalObject.getString("name");

                        JSONObject adhigaramGroupObject = iyalObject.getJSONObject("chapters");
                        JSONArray adhigaramArray = adhigaramGroupObject.getJSONArray("detail");

                        for (int k = 0; k < adhigaramArray.length(); k++) {
                            JSONObject adhigaramObject = adhigaramArray.getJSONObject(k);
                            String adhigaramName = adhigaramObject.getString("name");
                            int adhigaramNumber = adhigaramObject.getInt("number");
                            int adhigaramStart = adhigaramObject.getInt("start");
                            int adhigaramEnd = adhigaramObject.getInt("end");
                            Log.d("HHH", partName+","+iyalName +","+adhigaramName +","+adhigaramNumber +","+adhigaramStart +","+adhigaramEnd);
                        }
                    }
                }
            } catch (final JSONException e) {

            }
        }
    }
}