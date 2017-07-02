package com.stedisa;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

import com.stedisa.data.Database;

public class AddIncomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        Database db = Database.getInstance();

        ListView categoryList = (ListView) findViewById(R.id.newIncomeCategory);
        categoryList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        CategoryRowAdapter adapter = new CategoryRowAdapter(this, db.getCategories());
        categoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
