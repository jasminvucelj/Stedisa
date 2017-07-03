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

import java.util.List;

public class GridIconAdapter extends ArrayAdapter<String> {
    public GridIconAdapter(Context c, List<String> data) {
        super(c, R.layout.grid_image, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_image, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = getItem(position);
        holder.getImage().setImageResource(Util.getImageIdByName(getContext(), name));

        return convertView;
    }

    public class ViewHolder {
        private View row;
        private ImageView image = null;

        public ViewHolder(View row) {
            this.row = row;
        }

        public ImageView getImage() {
            if (this.image == null) {
                this.image = (ImageView) row.findViewById(R.id.categoryImage);
            }
            return this.image;
        }
    }
}
