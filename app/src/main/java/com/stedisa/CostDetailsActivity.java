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

public class CostDetailsActivity extends Activity implements DatabaseChangeListener{

    public static final String EXTRA_COST = "EXTRA_COST";
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
        setContentView(R.layout.activity_cost_details);

        db = Database.getInstance();
        db.addListener(this);

        t = (Transaction) getIntent().getSerializableExtra(EXTRA_COST);

        title = (TextView) findViewById(R.id.costDetailsTitle);
        price = (TextView) findViewById(R.id.costDetailsPrice);
        category = (TextView) findViewById(R.id.costDetailsCategory);
        categoryImage = (ImageView) findViewById(R.id.costDetailsCategoryImage);
        description = (TextView) findViewById(R.id.costDetailsDescription);
        refresh();

        Button edit = (Button) findViewById(R.id.costDetailsEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditCostActivity.class);
                intent.putExtra(EditCostActivity.EXTRA_COST, t);
                v.getContext().startActivity(intent);
            }
        });

        Button delete = (Button) findViewById(R.id.costDetailsDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCost(t);
                finish();
            }
        });
    }

    @Override
    public void onChange() {
        refresh();
    }

    private void refresh() {
        t = db.refreshCost(t);
        title.setText(t.getName());
        price.setText(t.getValueString());
        category.setText(t.getCategory().getName());
        categoryImage.setImageResource(Util.getImageIdByName(this, t.getCategory().getIcon()));
        description.setText(t.getDescription());
    }
}
