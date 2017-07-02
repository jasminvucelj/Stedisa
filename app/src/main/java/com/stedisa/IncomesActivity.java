package com.stedisa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.stedisa.data.Category;
import com.stedisa.data.Database;

import java.util.ArrayList;
import java.util.List;

public class IncomesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);

        final Database db = Database.getInstance();
        db.createMockupData();

        BarChart incomesChart = (BarChart) findViewById(R.id.incomesChart);
        List<BarEntry> chartEntries = new ArrayList<>();
        int i = 0;
        final List<Category> codes = new ArrayList<>();
        for (Pair<Category, Float> pair : db.getIncomesSumsByCategories()) {
            chartEntries.add(new BarEntry(i, pair.second));
            codes.add(pair.first);
            i++;
        }
        BarDataSet set = new BarDataSet(chartEntries, "");
        set.setColors(colors(), this);
        BarData data = new BarData(set);
        //data.setValueTextColor(Color.WHITE);
        incomesChart.setData(data);
        incomesChart.getDescription().setEnabled(false);

        XAxis xAxis = incomesChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return codes.get((int) value).getName();
            }
        });
        //incomesChart.setDrawEntryLabels(false)
        incomesChart.getLegend().setEnabled(false);
        incomesChart.setFitBars(true);
        incomesChart.invalidate();


//        FloatingActionButton addIncomeButton = (FloatingActionButton) findViewById(R.id.addIncomeButton);
//        addIncomeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), AddIncomeActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });

        TextView totalIncomes = (TextView) findViewById(R.id.totalIncomes);
        totalIncomes.setText(String.format("Ukupno %.2f", db.getIncomesSum()));


        ListView incomesList = (ListView) findViewById(R.id.incomesList);
        PriceRowAdapter costListAdapter = new PriceRowAdapter(this, db.getAllIncomes());
        incomesList.setAdapter(costListAdapter);
        costListAdapter.notifyDataSetChanged();
//        incomesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(view.getContext(), IncomeDetailsActivity.class);
//                view.getContext().startActivity(intent);
//            }
//        });
    }

    private int[] colors() {
        int[] result = new int[10];
        result[0] = (R.color.graph_1);
        result[1] = (R.color.graph_2);
        result[2] = (R.color.graph_3);
        result[3] = (R.color.graph_4);
        result[4] = (R.color.graph_5);
        result[5] = (R.color.graph_6);
        result[6] = (R.color.graph_7);
        result[7] = (R.color.graph_8);
        result[8] = (R.color.graph_9);
        result[9] = (R.color.graph_10);
        return result;
    }
}
