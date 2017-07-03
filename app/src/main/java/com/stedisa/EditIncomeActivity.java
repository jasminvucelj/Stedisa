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
import com.stedisa.data.Transaction;

import java.util.List;

public class EditIncomeActivity extends Activity {
    
    public static final String EXTRA_INCOME = "EXTRA_INCOME";

    private Category selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);
        
        final Database db = Database.getInstance();

        final Transaction t = (Transaction) getIntent().getSerializableExtra(EXTRA_INCOME);

        final ListView categoryList = (ListView) findViewById(R.id.editIncomeCategory);
        categoryList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        List<Category> categories = db.getIncomeCategories();
        final CategoryRowAdapter adapter = new CategoryRowAdapter(this, db.getIncomeCategories());
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

        Button cancel = (Button) findViewById(R.id.editIncomeButtonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText title = (EditText) findViewById(R.id.editIncomeTitle);
        title.setText(t.getName());
        final EditText quantity = (EditText) findViewById(R.id.editIncomeQuantity);
        quantity.setText(t.getValueString());
        final EditText description = (EditText) findViewById(R.id.editIncomeDescription);
        description.setText(t.getDescription());

        Button add = (Button) findViewById(R.id.editIncomeButtonSave);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.editIncome(
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
