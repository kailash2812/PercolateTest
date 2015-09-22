package com.percolate.codetest.api;

import com.percolate.codetest.utils.CoffeeConverter;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Percolate Rest Client using RestAdapter
 */
public class PercolateRestClient {

    private static PercolateRestApi REST_CLIENT;

    private static String ROOT = "https://coffeeapi.percolate.com/";

    static {
        setupRestClient();
    }

    private PercolateRestClient() {}

    public static PercolateRestApi get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        CoffeeConverter coffeeConverter = new CoffeeConverter();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setConverter(coffeeConverter)
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();

        REST_CLIENT = restAdapter.create(PercolateRestApi.class);
    }
}