package com.percolate.codetest.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.percolate.codetest.Coffee;
import com.percolate.codetest.CoffeeHelper;
import com.percolate.codetest.Constants;
import com.percolate.codetest.R;
import com.percolate.codetest.api.PercolateRestClient;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Detail Fragment to display coffee item details
 */
public class DetailFragment extends Fragment {

    //tag for Logging
    public static final String TAG = DetailFragment.class.getSimpleName();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    Context mContext;
    String coffeeId, weeksText;
    int numWeeks;
    Coffee coffee;

    TextView nameTextView, descTextView, updatedTextView;
    ImageView imageView;

    /**
     * Detail Fragment new Instance method
     *
     * @param context
     * @param id
     * @return DetailFragment Instance
     */
    public static DetailFragment newInstance(Context context, String id) {
        DetailFragment fragment = new DetailFragment();
        fragment.mContext = context;
        fragment.coffeeId = id;
        return fragment;
    }

    /**
     * onActivityCreated
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mContext = this.getActivity();

        nameTextView = (TextView) view.findViewById(R.id.nameText);
        descTextView = (TextView) view.findViewById(R.id.descText);
        updatedTextView = (TextView) view.findViewById(R.id.updatedText);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        coffee = CoffeeHelper.getCoffeeById(mContext,
                getActivity().getResources().getString(R.string.content_provider_authority),
                coffeeId);

        nameTextView.setText(coffee.getName().toString());
        descTextView.setText(coffee.getDesc().toString());

        if (!coffee.getImageUrl().isEmpty()) {
            Picasso.with(mContext)
                    .load(coffee.getImageUrl())
                    .placeholder(R.drawable.drip_white)
                    .into(imageView);
        }

        PercolateRestClient.get().getCoffeeById(coffeeId, Constants.API_KEY, new Callback<Coffee>() {
            @Override
            public void success(Coffee coffeeObj, Response response) {
                try {
                    CoffeeHelper.updateCoffee(mContext,
                            getActivity().getResources().getString(R.string.content_provider_authority),
                            coffeeObj.getId(), coffeeObj);

                    Date date = sdf.parse(coffeeObj.getLastUpdatedAt().toString());

                    numWeeks = getWeeksBetween(date, new Date());
                    if (numWeeks > 0) {
                        if (numWeeks == 1) {
                            weeksText = "Updated " + getWeeksBetween(date, new Date()) + " week ago";
                        } else {
                            weeksText = "Updated " + getWeeksBetween(date, new Date()) + " weeks ago";
                        }
                        updatedTextView.setText(weeksText);
                    } else {
                        weeksText = "Updated on " + date;
                        updatedTextView.setText(weeksText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, retrofitError.toString());
            }
        });

        return view;
    }

    public static int getWeeksBetween(Date a, Date b) {
        a = resetTime(a);
        b = resetTime(b);

        Calendar cal = new GregorianCalendar();
        cal.setTime(a);
        int weeks = 0;
        while (cal.getTime().before(b)) {
            // add another week
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }
        return weeks;
    }

    public static Date resetTime(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        Button button = (Button) menuItem.getActionView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coffee != null) {
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("*/*");
                    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, coffee.getName());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, coffee.getDesc() + coffee.getImageUrl());
                    startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_using)));
                }
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
