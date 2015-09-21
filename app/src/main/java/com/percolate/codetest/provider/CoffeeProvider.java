package com.percolate.codetest.provider;

/**
 * Content Provider Class
 * @author Kailash
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.percolate.codetest.R;

public class CoffeeProvider extends ContentProvider {

    @SuppressWarnings("unused")
    private static String LOG_TAG = CoffeeProvider.class.getSimpleName();

    private static DataSource dataSource;
    private static Context context;
    private static final UriMatcher uriMatcher = new UriMatcher(0);
    private static String authority;

    //STATIC TABLES
    public static final String COFFEE_TABLE = "coffee";

    //STATIC TABLES CONSTANTS
    private static final int COFFEE = 1;


    @Override
    public boolean onCreate() {

        context = getContext();

        // Get the authority string from our properties file.
        authority = context.getString(R.string.content_provider_authority);
        // Configure the uri matching variables.
        initUriMatchers();

        //context
        // Instantiate our DataSource The DataSource will do any work needed to
        // prepare the data source in its constructor.
        dataSource = new DataSource(context);
        return true;
    }

    /**
     * Method for querying datasource
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return Cursor
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Match the incoming uri to the data source.
        String dataLocation = matchUri(uri);

        Cursor cursor = dataSource.query(context, dataLocation, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(context.getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert Method
     * @param uri
     * @param values
     * @return Uri
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Match the incoming uri to the data source.
        String dataLocation = matchUri(uri);

        Long rowId = dataSource.insert(dataLocation, null, values);

        if (rowId > 0) {
            Uri modifiedUri = ContentUris.withAppendedId(uri, rowId);
            context.getContentResolver().notifyChange(modifiedUri, null);
            return modifiedUri;
        } else {
            throw new SQLException("Failed to insert row for:	" + uri);
        }
    }

    /**
     * delete Method
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return count
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Match the incoming uri to the data source.
        String dataLocation = matchUri(uri);

        int count = dataSource.delete(dataLocation, selection, selectionArgs);

        if (count > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    /**
     * update Method
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return count
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Match the incoming uri to the data source.
        String dataLocation = matchUri(uri);

        int count = dataSource.update(dataLocation, values, selection, selectionArgs);
        if (count > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    /**
     * getType Method
     * @param uri
     * @return Type
     */
    @Override
    public String getType(Uri uri) {
        // Not implementing different MIME types.
        return null;
    }

    /**
     * Matches the incoming uri to a string to use for the data selection
     * source.
     *
     * @param uri
     *            Uri to be matched.
     * @return A string value to determine the data source for the uri.
     */
    private String matchUri(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case COFFEE:
                return COFFEE_TABLE;
            default:
                throw new IllegalArgumentException("Could not match Uri:	" + uri);
        }
    }

    /**
     * Initializes the uri matching variables for incoming Uris.
     */
    private void initUriMatchers() {
        uriMatcher.addURI(authority, COFFEE_TABLE, COFFEE);

    }

}
