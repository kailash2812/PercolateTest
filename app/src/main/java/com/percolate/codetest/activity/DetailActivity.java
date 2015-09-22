package com.percolate.codetest.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.percolate.codetest.utils.Constants;
import com.percolate.codetest.R;
import com.percolate.codetest.fragment.DetailFragment;

/**
 * Detail Activity that loads detail fragment with coffe item details.
 */
public class DetailActivity extends AppCompatActivity {

    //tag for logging
    public static final String TAG = MainActivity.class.getSimpleName();

    Context mContext;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        mContext = this;

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar_custom_view);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        detailFragment = DetailFragment.newInstance(mContext, getIntent().getExtras().getString(Constants.EXTRA_ID));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail,
                detailFragment).commit();

    }

}
