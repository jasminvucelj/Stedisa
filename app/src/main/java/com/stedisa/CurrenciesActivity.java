package com.stedisa;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.stedisa.data.Database;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CurrenciesActivity extends Activity {

    private Database db;

    private TextView unit;
    private TextView buyed;
    private TextView middle;
    private TextView selled;

    private RadioButton aud;
    private RadioButton cad;
    private RadioButton czk;
    private RadioButton dkk;
    private RadioButton huf;
    private RadioButton jpy;
    private RadioButton nok;
    private RadioButton sek;
    private RadioButton chf;
    private RadioButton usd;
    private RadioButton gbp;
    private RadioButton eur;

    private static HashMap<String, List<String>> table;
    static {
        table = new HashMap<>();
        table.put("AUD", Arrays.asList("1", "4.865181", "4.989929", "5.114677"));
        table.put("CAD", Arrays.asList("1", "4.874704", "4.874704", "5.124688"));
        table.put("CZK", Arrays.asList("1", "0.275337", "0.282397", "0.288045"));
        table.put("DKK", Arrays.asList("1", "0.976198", "0.996120", "1.016042"));
        table.put("HUF", Arrays.asList("100", "2.342356", "2.397498", "2.445448"));
        table.put("JPY", Arrays.asList("100", "5.655724", "5.800743", "5.945762"));
        table.put("NOK", Arrays.asList("1", "0.758986", "0.774476", "0.789966"));
        table.put("SEK", Arrays.asList("1", "0.751771", "0.767113", "0.782455"));
        table.put("CHF", Arrays.asList("1", "6.600450", "6.776643", "6.959612"));
        table.put("USD", Arrays.asList("1", "6.332071", "6.494432", "6.656793"));
        table.put("GBP", Arrays.asList("1", "8.224986", "8.435883", "8.646780"));
        table.put("EUR", Arrays.asList("1", "7.355000", "7.407549", "7.455000"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curencies);

        db = Database.getInstance();

        unit = (TextView) findViewById(R.id.currenciesUnit);
        buyed = (TextView) findViewById(R.id.currenciesBuyed);
        middle = (TextView) findViewById(R.id.currenciesMiddle);
        selled = (TextView) findViewById(R.id.currenciesSelled);

        aud = (RadioButton) findViewById(R.id.aud); cad = (RadioButton) findViewById(R.id.cad);
        czk = (RadioButton) findViewById(R.id.czk); dkk = (RadioButton) findViewById(R.id.dkk);
        huf = (RadioButton) findViewById(R.id.huf); jpy = (RadioButton) findViewById(R.id.jpy);
        nok = (RadioButton) findViewById(R.id.nok); sek = (RadioButton) findViewById(R.id.sek);
        chf = (RadioButton) findViewById(R.id.chf); usd = (RadioButton) findViewById(R.id.usd);
        gbp = (RadioButton) findViewById(R.id.gbp); eur = (RadioButton) findViewById(R.id.eur);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clearOthers(buttonView.getText().toString());
                    onCurrencyChange(buttonView.getText().toString());
                }
            }
        };
        aud.setOnCheckedChangeListener(listener); cad.setOnCheckedChangeListener(listener);
        czk.setOnCheckedChangeListener(listener); dkk.setOnCheckedChangeListener(listener);
        huf.setOnCheckedChangeListener(listener); jpy.setOnCheckedChangeListener(listener);
        nok.setOnCheckedChangeListener(listener); sek.setOnCheckedChangeListener(listener);
        chf.setOnCheckedChangeListener(listener); usd.setOnCheckedChangeListener(listener);
        gbp.setOnCheckedChangeListener(listener); eur.setOnCheckedChangeListener(listener);

    }

    private void onCurrencyChange(String currency) {
        List<String> data = table.get(currency);
        unit.setText(data.get(0));
        buyed.setText(data.get(1));
        middle.setText(data.get(2));
        selled.setText(data.get(3));
    }

    private void clearOthers(String me) {
        if (!me.equals("AUD")) aud.setChecked(false); if (!me.equals("CAD")) cad.setChecked(false);
        if (!me.equals("CZK")) czk.setChecked(false); if (!me.equals("DKK")) dkk.setChecked(false);
        if (!me.equals("HUF")) huf.setChecked(false); if (!me.equals("JPY")) jpy.setChecked(false);
        if (!me.equals("NOK")) nok.setChecked(false); if (!me.equals("SEK")) sek.setChecked(false);
        if (!me.equals("CHF")) chf.setChecked(false); if (!me.equals("USD")) usd.setChecked(false);
        if (!me.equals("GBP")) gbp.setChecked(false); if (!me.equals("EUR")) eur.setChecked(false);
    }
}
