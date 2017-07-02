package com.stedisa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.stedisa.data.Category;
import com.stedisa.data.Database;

import java.util.ArrayList;
import java.util.List;

public class CostsActivity extends Activity {

    private PieChart costsChart;
    private List<PieEntry> chartEntries;

    private FloatingActionButton addCostButton;
    private TextView totalCosts;
    private ListView costList;
    private PriceRowAdapter costListAdapter;

    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        db = Database.getInstance();
        db.createMockupData();

        costsChart = (PieChart) findViewById(R.id.costsChart);
        chartEntries = new ArrayList<>();
        int i = 0;
        for (Pair<Category, Float> pair : db.getCostsSumsByCategories()) {
            chartEntries.add(new PieEntry(pair.second, pair.first.getName()));
        }
        PieDataSet set = new PieDataSet(chartEntries, "");
        set.setColors(colors(), this);
        PieData data = new PieData(set);
        data.setValueTextColor(Color.WHITE);
        costsChart.setData(data);
        costsChart.getDescription().setEnabled(false);
        costsChart.setDrawEntryLabels(false);
        costsChart.invalidate();


        addCostButton = (FloatingActionButton) findViewById(R.id.addCostButton);
        addCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCostActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        totalCosts = (TextView) findViewById(R.id.totalCosts);
        totalCosts.setText(String.format("Ukupno %.2f", db.getCostsSum()));


        costList = (ListView) findViewById(R.id.costList);
        costListAdapter = new PriceRowAdapter(this, db.getAllCosts());
        costList.setAdapter(costListAdapter);
        costListAdapter.notifyDataSetChanged();
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