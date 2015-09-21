package com.percolate.codetest;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.percolate.codetest.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

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
