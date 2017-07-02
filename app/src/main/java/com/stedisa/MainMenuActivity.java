package com.stedisa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ImageButton ibCosts = (ImageButton) findViewById(R.id.ibCosts);
        ImageButton ibIncomes = (ImageButton) findViewById(R.id.ibIncomes);
        ImageButton ibStats = (ImageButton) findViewById(R.id.ibStats);
        ImageButton ibCurrencies = (ImageButton) findViewById(R.id.ibCurrencies);
        ImageButton ibCategories = (ImageButton) findViewById(R.id.ibCategories);
        ImageButton ibSettings = (ImageButton) findViewById(R.id.ibSettings);
        ImageButton ibAbout = (ImageButton) findViewById(R.id.ibAbout);

        ibCosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v.getContext(), CostsActivity.class);
            }
        });

        ibIncomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startNewActivity(v.getContext(), IncomesActivity.class);
            }
        });

        ibStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startNewActivity(v.getContext(), StatsActivity.class);
            }
        });

        ibCurrencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startNewActivity(v.getContext(), CurrenciesActivity.class);
            }
        });

        ibCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startNewActivity(v.getContext(), CategoriesActivity.class);
            }
        });

        ibSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startNewActivity(v.getContext(), SettingsActivity.class);
            }
        });

        ibAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v.getContext(), CreditsActivity.class);
            }
        });
    }

    static void startNewActivity(Context thisActivityContext, Class nextActivityClass){
        Intent intent = new Intent(thisActivityContext, nextActivityClass);
        thisActivityContext.startActivity(intent);
    }
}
