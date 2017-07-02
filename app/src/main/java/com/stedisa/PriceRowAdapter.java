package com.stedisa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stedisa.data.Transaction;

import java.util.List;

public class PriceRowAdapter extends ArrayAdapter<Transaction> {
    public PriceRowAdapter(Context c, List<Transaction> data) {
        super(c, R.layout.row, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.getName().setText(getItem(position).getName());
        holder.getPrice().setText(String.format("%.2f", getItem(position).getValue()));

        return convertView;
    }

    public class ViewHolder {
        private View row;
        private TextView name = null, price = null;

        public ViewHolder(View row) {
            this.row = row;
        }

        public TextView getName() {
            if (this.name == null) {
                this.name = (TextView) row.findViewById(R.id.listItemName);
            }
            return this.name;
        }

        public TextView getPrice() {
            if (this.price == null) {
                this.price = (TextView) row.findViewById(R.id.listItemPrice);
            }
            return this.price;
        }
    }
}
