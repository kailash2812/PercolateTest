package com.percolate.codetest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.percolate.codetest.Coffee;
import com.percolate.codetest.resolver.CoffeeColumns;

public class CoffeeHelper {
    private static final String TAG = CoffeeHelper.class.getSimpleName();

    public static Uri insertCoffeeObject(Context context, String authority,
                                         Coffee coffee) {

        ContentValues cv = new ContentValues();
        cv.put(CoffeeColumns.ID, coffee.getId());
        cv.put(CoffeeColumns.NAME, coffee.getName());
        cv.put(CoffeeColumns.DESCRIPTION, coffee.getDesc());
        cv.put(CoffeeColumns.IMAGEURL, coffee.getImageUrl());
        cv.put(CoffeeColumns.LASTUPDATEDAT, coffee.getLastUpdatedAt());

        Uri returnUri = context.getContentResolver().insert(
                CoffeeColumns.getCONTENT_URI(authority), cv);

        return returnUri;
    }


    public static boolean updateCoffee(Context context, String authority,
                                       String coffeeId, Coffee coffee) {

        ContentValues cv = new ContentValues();
        cv.put(CoffeeColumns.LASTUPDATEDAT, coffee.getLastUpdatedAt());

        int rows = context.getContentResolver().update(
                CoffeeColumns.getCONTENT_URI(authority), cv,
                CoffeeColumns.ID + " = ?",
                new String[]{coffeeId});

        return rows > 0 ? true : false;
    }

    public static Coffee getCoffeeById(Context context, String authority,
                                   String coffeeId) {

        Cursor c = null;

        try {
            c = context.getContentResolver().query(CoffeeColumns.getCONTENT_URI(authority),
                    null, CoffeeColumns.ID + " = ?",
                    new String[]{coffeeId}, null);

            if (c.moveToFirst()) {
                Coffee channel = createFromCursor(c);
                return channel;
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return null;
    }

    public static Coffee createFromCursor(Cursor c) {
        if (c == null) {
            return null;
        }

        boolean isEnabled, isFavorite;
        String id = c.getString(
                c.getColumnIndex(CoffeeColumns.ID));
        String name = c.getString(
                c.getColumnIndex(CoffeeColumns.NAME));
        String desc = c.getString(
                c.getColumnIndex(CoffeeColumns.DESCRIPTION));
        String imageUrl = c.getString(
                c.getColumnIndex(CoffeeColumns.IMAGEURL));
        String lastUpdated = c.getString(
                c.getColumnIndex(CoffeeColumns.LASTUPDATEDAT));

        Coffee coffee = new Coffee(id, name, desc, imageUrl, lastUpdated);

        return coffee;
    }

    public static boolean deleteAll(Context context,
                                    String authority) {

        int rows = context.getContentResolver().delete(
                CoffeeColumns.getCONTENT_URI(authority), null, null);

        return rows > 0 ? true : false;
    }


}//End
