package com.stedisa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AndroidThreeTen.init(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menu = new Intent(SplashActivity.this, MainMenuActivity.class);
                SplashActivity.this.startActivity(menu);
                SplashActivity.this.finish();
            }
        }, 1000);
    }
}
