package com.stedisa;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.stedisa.adapter.GridIconAdapter;
import com.stedisa.data.Category;
import com.stedisa.data.CategoryType;
import com.stedisa.data.Database;

import java.util.Arrays;
import java.util.List;

public class AddCategoryActivity extends Activity {

    private String selected;
    private CategoryType categoryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        final Database db = Database.getInstance();

        final List<String> icons = Arrays.asList(
                "airplane", "ambulance", "banana", "bed", "beer", "bicycle", "bottle", "bra", "brain",
                "bulb", "buldozer", "burger", "cake", "car", "cards", "cart", "cd", "cheese", "christmas",
                "citrus", "cocktail", "coffee", "computer", "credit", "cutlery", "dice", "dress", "fish",
                "gamepad", "gift", "hdd", "heels", "hotdog", "kettle", "laptop", "money", "monitor",
                "monster", "noodles", "pants", "paste", "pineapple", "pizza", "pokemon", "ring", "shake",
                "shirt", "shoe", "shop", "shroom", "strawberry", "sushi", "therapy", "tickets", "toilet",
                "tooth", "towel", "tv", "wallet", "wardrobe", "watermelon", "wine"
        );
        final GridView iconGrid = (GridView) findViewById(R.id.newCategoryIconGrid);
        final GridIconAdapter gridIconAdapter = new GridIconAdapter(this, icons);
        iconGrid.setAdapter(gridIconAdapter);
        selected = gridIconAdapter.getItem(0);
        iconGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = gridIconAdapter.getItem(position);
            }
        });
        gridIconAdapter.notifyDataSetChanged();
        iconGrid.setSelection(0);

        final EditText title = (EditText) findViewById(R.id.newCategoryTitle);
        final Spinner type = (Spinner) findViewById(R.id.newCategoryType);
        String[] types = getResources().getStringArray(R.array.category_type);
        type.setSelection(0);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryType = position == 0 ? CategoryType.INCOME : CategoryType.COST;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button cancel = (Button) findViewById(R.id.newCategoryButtonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button add = (Button) findViewById(R.id.newCategoryButtonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addCategory(
                        title.getText().toString(),
                        categoryType,
                        selected
                );
                finish();
            }
        });
    }

}
