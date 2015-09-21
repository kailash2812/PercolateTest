package com.percolate.codetest.api;

import com.percolate.codetest.Coffee;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.Callback;

/**
 * Retrofit api declaration for Percolate endpoints.
 */
public interface PercolateRestApi {

    /**
     * Query list of coffee
     */
    @GET("/api/coffee/")
    public void getCoffee(@Query("api_key") String apiKey, Callback<List<Coffee>> callback);

    /**
     * Query list of coffee by Id
     */
    @GET("/api/coffee/{id}/")
    void getCoffeeById(@Path("id") String Id, @Query("api_key") String apiKey, Callback<Coffee> cb);

}
