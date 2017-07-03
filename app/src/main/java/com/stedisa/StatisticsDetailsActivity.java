package com.stedisa;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.stedisa.data.Database;
import com.stedisa.data.DatabaseChangeListener;

public class StatisticsDetailsActivity extends Activity implements DatabaseChangeListener {

    private TextView incomeSum;
    private TextView incomeCount;
    private TextView incomeAvg;

    private TextView costSum;
    private TextView costCount;
    private TextView costAvg;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_details);

        db = Database.getInstance();
        db.addListener(this);

        incomeSum = (TextView) findViewById(R.id.statDetailsIncomeSum);
        incomeCount = (TextView) findViewById(R.id.statDetailsIncomeCount);
        incomeAvg = (TextView) findViewById(R.id.statDetailsIncomeAvg);

        costSum = (TextView) findViewById(R.id.statDetailsCostSum);
        costCount = (TextView) findViewById(R.id.statDetailsCostCount);
        costAvg = (TextView) findViewById(R.id.statDetailsCostAvg);

        refresh();
    }

    @Override
    public void onChange() {
        refresh();
    }

    private void refresh() {
        incomeSum.setText(String.format("%.2f", db.getIncomesSum(false)));
        incomeCount.setText(Integer.toString(db.getIncomeCount()));
        incomeAvg.setText(String.format("%.2f", db.getIncomeAverage()));

        costSum.setText(String.format("%.2f", db.getCostsSum(false)));
        costCount.setText(Integer.toString(db.getCostCount()));
        costAvg.setText(String.format("%.2f", db.getCostAverage()));
    }
}
