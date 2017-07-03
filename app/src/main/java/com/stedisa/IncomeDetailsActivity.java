package com.stedisa;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stedisa.data.Database;
import com.stedisa.data.DatabaseChangeListener;
import com.stedisa.data.Transaction;

public class IncomeDetailsActivity extends Activity implements DatabaseChangeListener {

    public static final String EXTRA_INCOME = "EXTRA_INCOME";
    private TextView title;
    private TextView price;
    private TextView category;
    private ImageView categoryImage;
    private TextView description;

    private Transaction t;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_details);

        db = Database.getInstance();
        db.addListener(this);

        t = (Transaction) getIntent().getSerializableExtra(EXTRA_INCOME);

        title = (TextView) findViewById(R.id.incomeDetailsTitle);
        price = (TextView) findViewById(R.id.incomeDetailsPrice);
        category = (TextView) findViewById(R.id.incomeDetailsCategory);
        categoryImage = (ImageView) findViewById(R.id.incomeDetailsCategoryImage);
        description = (TextView) findViewById(R.id.incomeDetailsDescription);
        refresh();

        Button edit = (Button) findViewById(R.id.incomeDetailsEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditIncomeActivity.class);
                intent.putExtra(EditIncomeActivity.EXTRA_INCOME, t);
                v.getContext().startActivity(intent);
            }
        });

        Button delete = (Button) findViewById(R.id.incomeDetailsDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteIncome(t);
                finish();
            }
        });
    }

    @Override
    public void onChange() {
        refresh();
    }

    private void refresh() {
        t = db.refreshIncome(t);
        title.setText(t.getName());
        price.setText(t.getValueString());
        category.setText(t.getCategory().getName());
        categoryImage.setImageResource(Util.getImageIdByName(this, t.getCategory().getIcon()));
        description.setText(t.getDescription());
    }
}
