package com.percolate.codetest;

import android.widget.ListView;
import android.widget.TextView;

import com.percolate.codetest.activity.DetailActivity;
import com.percolate.codetest.fragment.DetailFragment;

/*
 * Detail Activity test class.
 *
 */
public class DetailActivityTest extends android.test.ActivityInstrumentationTestCase2<DetailActivity> {
    DetailActivity detailActivity;
    DetailFragment detailFragment;

    public DetailActivityTest() {
        super(DetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        detailActivity = getActivity();
        detailFragment = DetailFragment.newInstance(getActivity(), "");
    }

    public void testPreConditions() {
        assertNotNull(detailActivity);
        assertNotNull(detailFragment);
    }

    public void test_Should_Set_Title_TextView_Text() {
        TextView titleTextView = (TextView) detailFragment.getView().findViewById(R.id.nameText);
        assertEquals("some title here", titleTextView.getText().toString());
    }

    public void test_Should_Set_Desc_TextView_Text() {
        TextView descTextView = (TextView) detailFragment.getView().findViewById(R.id.descText);
        assertEquals("some desc here", descTextView.getText().toString());
    }

}