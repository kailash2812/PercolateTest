package com.percolate.codetest.provider;

/**
 * DataSource Class
 *
 * @author Kailash
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.percolate.codetest.R;

public class DataSource {

    //tag for logging
    private static String TAG = DataSource.class.getSimpleName();

    private static SqlHelper sqlHelper;
    private static String DATABASE_NAME;
    public static final int VERSION = 1;

    /**
     * DataSource Constructor for Percolate. The constructor is responsible for creating SQLHelper
     * and providing a name for the database
     */
    public DataSource(Context context) {
        DATABASE_NAME = context.getString(R.string.content_provider_database_name);
        sqlHelper = new SqlHelper(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Wrapper method for querying a database.
     * @param context
     * @param dataLocation
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param sortOrder
     * @return Cursor with results of query.
     */
    public Cursor query(Context context, String dataLocation, String[] projection, String selection, String[] selectionArgs, String groupBy, String having,
                        String sortOrder) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(dataLocation);
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
        return cursor;
    }

    /**
     * Wrapper method for deleting database rows.
     * @param table
     * @param where
     * @param whereArgs
     * @return The number of rows delete.
     */
    public int delete(String table, String where, String[] whereArgs) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        return db.delete(table, where, whereArgs);
    }

    /**
     * Wrapper method for inserting new database rows.
     * @param table
     * @param object
     * @param values
     * @return The row ID of the new row.
     */
    public Long insert(String table, Object object, ContentValues values) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        return db.insert(table, null, values);
    }

    /**
     * Wrapper method for closing database rows.
     * @return void
     */
    public void close() {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.close();
    }

    /**
     * Wrapper method for updating database rows.
     * @param table
     * @param values
     * @param where
     * @param whereArgs
     * @return The number of rows modified.
     */
    public int update(String table, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        return db.update(table, values, where, whereArgs);
    }
}
