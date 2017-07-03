package com.stedisa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stedisa.R;
import com.stedisa.Util;
import com.stedisa.data.Category;

import java.util.List;

public class CategoryRowAdapter extends ArrayAdapter<Category> {
    public CategoryRowAdapter(Context c, List<Category> data) {
        super(c, R.layout.category_row, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_row, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Category c = getItem(position);
        holder.getName().setText(c.getName());
        holder.getImage().setImageResource(Util.getImageIdByName(getContext(), c.getIcon()));

        return convertView;
    }

    public class ViewHolder {
        private View row;
        private ImageView image = null;
        private TextView name = null;

        public ViewHolder(View row) {
            this.row = row;
        }

        public TextView getName() {
            if (this.name == null) {
                this.name = (TextView) row.findViewById(R.id.categoryName);
            }
            return this.name;
        }

        public ImageView getImage() {
            if (this.image == null) {
                this.image = (ImageView) row.findViewById(R.id.categoryImage);
            }
            return this.image;
        }
    }
}
