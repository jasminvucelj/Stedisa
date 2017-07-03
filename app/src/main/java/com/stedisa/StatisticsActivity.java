package com.stedisa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.stedisa.data.Category;
import com.stedisa.data.Database;
import com.stedisa.data.DatabaseChangeListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends Activity implements DatabaseChangeListener {

    private LineChart chart;
    private TextView totalIncomes;
    private TextView totalCosts;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        db = Database.getInstance();
        db.addListener(this);

        chart = (LineChart) findViewById(R.id.statsChart);
        chart.getAxisRight().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        refreshChart();

        totalIncomes = (TextView) findViewById(R.id.statsTotalIncomes);
        totalCosts = (TextView) findViewById(R.id.statsTotalCosts);
        refreshText();

        Button details = (Button) findViewById(R.id.statsDetailsButton);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StatisticsDetailsActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onChange() {
        refreshChart();
        refreshText();
    }

    private void refreshChart() {
        List<Entry> chartEntries = new ArrayList<>();
        final List<Category> codes = new ArrayList<>();
        Pair<List<String>, Pair<List<Float>, List<Float>>> data = db.getValuesByPeriods();
        final List<String> labels = data.first;
        List<Float> incomesData = data.second.first;
        List<Float> costsData = data.second.second;

        List<Entry> incomes = new ArrayList<>();
        for (int i = 0; i < incomesData.size(); i++) {
            incomes.add(new Entry(i, incomesData.get(i)));
        }

        List<Entry> costs = new ArrayList<>();
        for (int i = 0; i < costsData.size(); i++) {
            costs.add(new Entry(i, costsData.get(i)));
        }

        LineDataSet incomeDataSet = new LineDataSet(incomes, "Prihodi");
        incomeDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        incomeDataSet.setColor(Color.BLUE);
        LineDataSet costDataSet = new LineDataSet(costs, "Troškovi");
        costDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        costDataSet.setColor(Color.RED);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(incomeDataSet);
        dataSets.add(costDataSet);

        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                try {
                    return labels.get((int) value);
                } catch(IndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        chart.invalidate();
    }

    private void refreshText() {
        totalIncomes.setText(String.format("Ukupno %.2f", db.getIncomesSum(false)));
        totalCosts.setText(String.format("Ukupno %.2f", db.getCostsSum(false)));
    }
}
