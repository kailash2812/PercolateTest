package com.percolate.codetest.provider;

/**
 * SQL Helper for Percolate Coffee Table
 *
 * @author Kailash
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.percolate.codetest.resolver.CoffeeColumns;

public class SqlHelper extends SQLiteOpenHelper {
    @SuppressWarnings("unused")
    private static String LOG_TAG = "SqlHelper";

    public SqlHelper(Context context, String name, CursorFactory factory,
                     int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * Method to Create Tables.
         *
         * @param fields
         */
        db.execSQL("CREATE TABLE " + CoffeeProvider.COFFEE_TABLE + " ("
                + CoffeeColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CoffeeColumns.ID + " INTEGER," + CoffeeColumns.NAME
                + " TEXT, " + CoffeeColumns.DESCRIPTION + " TEXT, "
                + CoffeeColumns.IMAGEURL + " TEXT, "
                + CoffeeColumns.LASTUPDATEDAT + " TEXT, " +
                "UNIQUE (" + CoffeeColumns.ID + ") ON CONFLICT REPLACE )");
    }

    /**
     * Method onUpgrade
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     * @return void
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            upgradeDatabase(db, oldVersion, newVersion);
        }
    }

    /**
     * Method UpgradeDatabase
     *
     * @param db
     * @param oldVersion
     * @return void
     */
    private void upgradeDatabase(SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
        while (oldVersion < newVersion) {

            String upgradeQuery1 = "DROP TABLE IF EXISTS "
                    + CoffeeProvider.COFFEE_TABLE;

            db.execSQL(upgradeQuery1);

            oldVersion += 1;

            Log.d(LOG_TAG, "OldVersion:" + oldVersion + "New Version:" + newVersion);

            onCreate(db);
        }
    }
}
