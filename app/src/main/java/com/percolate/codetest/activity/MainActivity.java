package com.percolate.codetest.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.percolate.codetest.R;
import com.percolate.codetest.fragment.MainFragment;

/**
 * Main Activity that loads main fragment with list view of coffee items
 */
public class MainActivity extends AppCompatActivity {

    //tag for logging
    public static final String TAG = MainActivity.class.getSimpleName();

    Context mContext;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mContext = this;

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar_custom_view);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mainFragment = MainFragment.newInstance(mContext);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main,
                mainFragment).commit();

    }

}
