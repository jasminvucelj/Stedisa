package com.stedisa;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.stedisa.adapter.CategoryRowAdapter;
import com.stedisa.data.Category;
import com.stedisa.data.Database;

public class AddCostActivity extends Activity {

    private Category selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);

        final Database db = Database.getInstance();

        final ListView categoryList = (ListView) findViewById(R.id.newCostCategory);
        categoryList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final CategoryRowAdapter adapter = new CategoryRowAdapter(this, db.getCostCategories());
        categoryList.setAdapter(adapter);
        selected = adapter.getItem(0);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = adapter.getItem(position);
            }
        });
        adapter.notifyDataSetChanged();
        categoryList.setSelection(0);

        Button cancel = (Button) findViewById(R.id.newCostButtonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText title = (EditText) findViewById(R.id.newCostTitle);
        final EditText quantity = (EditText) findViewById(R.id.newCostQuantity);
        final EditText description = (EditText) findViewById(R.id.newCostDescription);

        Button add = (Button) findViewById(R.id.newCostButtonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addCost(
                        Float.parseFloat(quantity.getText().toString()),
                        title.getText().toString(),
                        description.getText().toString(),
                        selected
                );
                finish();
            }
        });
    }

}
