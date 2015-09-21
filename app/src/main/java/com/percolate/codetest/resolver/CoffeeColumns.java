package com.percolate.codetest.resolver;

/**
 * Coffee Table Columns
 *
 * @author Kailash
 */

import android.net.Uri;
import android.provider.BaseColumns;

import com.percolate.codetest.provider.CoffeeProvider;

public class CoffeeColumns implements BaseColumns {

    /**
     * CoffeeColumns Empty Constructor
     */
    private CoffeeColumns() {

    }

    /**
     * Get Content URI
     *
     * @param authority
     * @return Uri
     */
    public static Uri getCONTENT_URI(String authority) {

        Uri authorityUri = Uri.parse("content://" + authority);
        Uri contentUri = Uri.withAppendedPath(authorityUri,
                CoffeeProvider.COFFEE_TABLE);

        return contentUri;
    }

    /**
     * Column Names
     */
    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "desc";

    public static final String IMAGEURL = "imageUrl";

    public static final String LASTUPDATEDAT = "lastUpdatedAt";

}