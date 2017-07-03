package com.stedisa;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.stedisa.data.Category;
import com.stedisa.data.Database;
import com.stedisa.data.Transaction;

import java.util.List;

public class EditCostActivity extends Activity {

    public static final String EXTRA_COST = "EXTRA_COST";

    private Category selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cost);

        final Database db = Database.getInstance();

        final Transaction t = (Transaction) getIntent().getSerializableExtra(EXTRA_COST);

        final ListView categoryList = (ListView) findViewById(R.id.editCostCategory);
        categoryList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        List<Category> categories = db.getCostCategories();
        final CategoryRowAdapter adapter = new CategoryRowAdapter(this, db.getCostCategories());
        categoryList.setAdapter(adapter);
        selected = adapter.getItem(categories.indexOf(t.getCategory()));
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = adapter.getItem(position);
            }
        });
        adapter.notifyDataSetChanged();
        categoryList.setSelection(categories.indexOf(t.getCategory()));

        Button cancel = (Button) findViewById(R.id.editCostButtonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText title = (EditText) findViewById(R.id.editCostTitle);
        title.setText(t.getName());
        final EditText quantity = (EditText) findViewById(R.id.editCostQuantity);
        quantity.setText(t.getValueString());
        final EditText description = (EditText) findViewById(R.id.editCostDescription);
        description.setText(t.getDescription());

        Button add = (Button) findViewById(R.id.editCostButtonSave);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.editCost(
                        t,
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
