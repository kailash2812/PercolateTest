package com.percolate.codetest;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.percolate.codetest.resolver.CoffeeColumns;
import com.squareup.picasso.Picasso;

public class CoffeeCursorAdapter extends CursorAdapter {

    private static class ViewHolder {
        TextView nameTextView;
        TextView descTextView;
        ImageView imageView;
        ImageView arrowView;
    }

    public CoffeeCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String nameText = cursor.getString(cursor.getColumnIndex(CoffeeColumns.NAME));
        String descText = cursor.getString(cursor.getColumnIndex(CoffeeColumns.DESCRIPTION));
        String imageUrl = cursor.getString(cursor.getColumnIndex(CoffeeColumns.IMAGEURL));

        if(nameText.isEmpty() && descText.isEmpty() && imageUrl.isEmpty()) {
            holder.arrowView.setVisibility(View.GONE);
        } else {
            holder.arrowView.setVisibility(View.VISIBLE);
        }

        holder.nameTextView.setText(nameText);
        holder.descTextView.setText(descText);

        if (!imageUrl.isEmpty()) {
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(cursor.getString(cursor.getColumnIndex(CoffeeColumns.IMAGEURL)))
                    .placeholder(R.drawable.drip_white)
                    .tag(context)
                    .into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, null);
        ViewHolder holder = new ViewHolder();

        holder.nameTextView = (TextView) view.findViewById(R.id.titleText);
        holder.descTextView = (TextView) view.findViewById(R.id.descText);
        holder.imageView = (ImageView) view.findViewById(R.id.imageView);
        holder.arrowView = (ImageView) view.findViewById(R.id.arrowView);
        view.setTag(holder);

        return view;
    }
}