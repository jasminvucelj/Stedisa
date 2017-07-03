package com.stedisa;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.stedisa.adapter.CategoryRowAdapter;
import com.stedisa.data.Database;
import com.stedisa.data.DatabaseChangeListener;

public class CategoriesActivity extends Activity implements DatabaseChangeListener {

    private Database db;
    private ListView costCategories;
    private ListView incomeCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        db = Database.getInstance();
        db.addListener(this);

        costCategories = (ListView) findViewById(R.id.categoriesCosts);
        incomeCategories = (ListView) findViewById(R.id.categoriesIncomes);

        refresh();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addCategoryButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCategoryActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onChange() {
        refresh();
    }

    private void refresh() {
        final CategoryRowAdapter costAdapter = new CategoryRowAdapter(this, db.getCostCategories());
        costCategories.setAdapter(costAdapter);
        costAdapter.notifyDataSetChanged();

        final CategoryRowAdapter incomeAdapter = new CategoryRowAdapter(this, db.getIncomeCategories());
        incomeCategories.setAdapter(incomeAdapter);
        incomeAdapter.notifyDataSetChanged();
    }
}
