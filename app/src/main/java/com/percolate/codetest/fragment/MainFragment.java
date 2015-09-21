package com.percolate.codetest.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.percolate.codetest.Coffee;
import com.percolate.codetest.CoffeeCursorAdapter;
import com.percolate.codetest.CoffeeHelper;
import com.percolate.codetest.Constants;
import com.percolate.codetest.DetailActivity;
import com.percolate.codetest.R;
import com.percolate.codetest.api.PercolateRestClient;
import com.percolate.codetest.resolver.CoffeeColumns;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //tag for logging
    public static final String TAG = MainFragment.class.getSimpleName();

    // Identifies a particular Loader being used in this component
    private static final int COFFEE_LOADER = 1;

    //activity context
    Context mContext;

    //Cursor Adapter for coffee objects
    CoffeeCursorAdapter mAdapter;

    //listview for coffee items
    ListView listView;

    public static MainFragment newInstance(Context context) {
        MainFragment fragment = new MainFragment();
        fragment.mContext = context;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mContext = this.getActivity();

        listView = (ListView) view.findViewById(R.id.listView);

        //Initializing the cursor loader
        getLoaderManager().initLoader(COFFEE_LOADER, null, this);

        mAdapter = new CoffeeCursorAdapter(mContext, null);
        listView.setAdapter(mAdapter);

        PercolateRestClient.get().getCoffee(Constants.API_KEY, new Callback<List<Coffee>>() {

            @Override
            public void success(List<Coffee> coffeeList, Response response) {
                if (coffeeList.isEmpty()) {
                    Toast.makeText(mContext, getResources().getString(R.string.no_items_found),
                            Toast.LENGTH_SHORT).show();
                }
                for (Coffee coffee : coffeeList) {
                    if (isAdded()) {
                        CoffeeHelper.insertCoffeeObject(mContext, getResources().
                                getString(R.string.content_provider_authority), coffee);
                    }
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, retrofitError.toString());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.EXTRA_ID,
                        cursor.getString(cursor.getColumnIndex(CoffeeColumns.ID)));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
    /*
     * Takes action based on the ID of the Loader that's being created
     */
        switch (loaderID) {
            case COFFEE_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this.getActivity(),
                        CoffeeColumns.getCONTENT_URI(getActivity().getResources().getString(R.string.content_provider_authority)),
                        null,
                        null,
                        null,
                        null
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    /*
 * Defines the callback that CursorLoader calls
 * when it's finished its query
 */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    /*
     * Moves the query results into the adapter, causing the
     * ListView fronting this adapter to re-display
     */
        mAdapter.changeCursor(cursor);
    }

    /*
 * Invoked when the CursorLoader is being reset. For example, this is
 * called if the data in the provider changes and the Cursor becomes stale.
 */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    /*
     * Clears out the adapter's reference to the Cursor.
     * This prevents memory leaks.
     */
        mAdapter.changeCursor(null);
    }
}
