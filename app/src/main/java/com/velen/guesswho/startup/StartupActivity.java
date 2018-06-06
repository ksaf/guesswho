package com.velen.guesswho.startup;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.TextView;

import com.velen.guesswho.R;
import com.velen.guesswho.playScreen.PlayScreenActivity;

/**
 * This activity builds the first screen you see when you open the app, and a set amount of
 * time later navigates you to the main menu.
 */
public class StartupActivity extends AppCompatActivity {

    private static final int LOGO_ID = R.id.logoTV;
    private static final String LOGO_TYPEFACE = "typefaces/caricature2.ttf";
    private static final int LOGO_TXT_SIZE = 100;
    private static final String LOGO_COLOR = "#FF7F50";
    private static final int LOADING_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        TextView logoTV = setupLogo(LOGO_ID, LOGO_TYPEFACE, LOGO_TXT_SIZE, LOGO_COLOR);
        startNextActivityIn(LOADING_TIME);
    }

    private TextView setupLogo(int logoRPath, String typeFacePath, int size, String color) {
        TextView logoTV = (TextView) findViewById(logoRPath);
        logoTV.setTypeface(Typeface.createFromAsset(getAssets(), typeFacePath));
        logoTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        logoTV.setTextColor(Color.parseColor(color));
        return logoTV;
    }

    private void startNextActivityIn(int timeMs) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startNextActivity();
            }
        }, timeMs);
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, PlayScreenActivity.class);
        startActivity(intent);
    }
}
