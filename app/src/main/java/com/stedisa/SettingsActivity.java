package com.stedisa;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.stedisa.data.Database;

import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.TemporalField;

import java.util.Arrays;

public class SettingsActivity extends Activity {

    SharedPreferences sharedPrefs;

    String language, currency, period;
    int paycheckDate;
    boolean autoAddSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinnerLanguage = (Spinner) findViewById(R.id.spinnerLanguage);
        Spinner spinnerCurrency = (Spinner) findViewById(R.id.spinnerCurrency);
        Spinner spinnerDate = (Spinner) findViewById(R.id.spinnerDate);
        Spinner spinnerPeriod = (Spinner) findViewById(R.id.spinnerPeriod);

        CheckBox cbAutoAddSalary = (CheckBox) findViewById(R.id.cbAutoAddSalary);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String[] tempArray;

        language = sharedPrefs.getString("language", "Hrvatski");
        tempArray = getResources().getStringArray(R.array.language_array);
        spinnerLanguage.setSelection(Arrays.asList(tempArray).indexOf(language));
        // set language

        currency = sharedPrefs.getString("currency", "HRK");
        tempArray = getResources().getStringArray(R.array.currency_array);
        spinnerCurrency.setSelection(Arrays.asList(tempArray).indexOf(currency));

        paycheckDate = sharedPrefs.getInt("paycheckDate", 15);
        spinnerDate.setSelection(paycheckDate-1);

        period = sharedPrefs.getString("period", "Mjesec");
        tempArray = getResources().getStringArray(R.array.period_array);
        spinnerPeriod.setSelection(Arrays.asList(tempArray).indexOf(period));

        autoAddSalary = sharedPrefs.getBoolean("autoAddSalary", false);
        cbAutoAddSalary.setChecked(autoAddSalary);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                language = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paycheckDate = Integer.parseInt((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                period = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbAutoAddSalary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoAddSalary = isChecked;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("language", language);
        editor.putString("currency", currency);
        editor.putInt("paycheckDate", paycheckDate);
        editor.putString("period", period);
        editor.putBoolean("autoAddSalary", autoAddSalary);
        editor.apply();

        Database db = Database.getInstance();
        db.setCurrency(currency);
        TemporalField temporalField = ChronoField.MONTH_OF_YEAR;
        if (period.equals("Mjesec")) {
            temporalField = ChronoField.MONTH_OF_YEAR;
        } else if (period.equals("Tjedan")) {
            temporalField = ChronoField.ALIGNED_WEEK_OF_YEAR;
        } else if (period.equals("Dan")) {
            temporalField = ChronoField.DAY_OF_MONTH;
        } else if (period.equals("Godina")) {
            temporalField = ChronoField.YEAR;
        }
        db.setTemporalField(temporalField);
    }
}
