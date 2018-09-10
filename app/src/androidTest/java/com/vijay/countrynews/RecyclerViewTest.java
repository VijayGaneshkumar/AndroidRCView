package com.vijay.countrynews;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vijay.countrynews.Views.MainActivity;

import junit.framework.Assert;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by vijayganeshkumar on 04/09/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {

    /**
     * The M activity test rule.
     */
    @Rule
    public ActivityTestRule mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Recycler view test.
     */
    @Test
    public void recyclerViewTest() {
        mActivityTestRule.getActivity();
        Espresso.onView(isRoot()).perform(waitFor(5000));
        onView(withId(R.id.newsItemRecyclerView)).perform(RecyclerViewActions.scrollToPosition(5));
        // once the view is loaded check if the item count is more than 0
        Assert.assertTrue(getRecyclerViewItemCount() >0) ;

    }
    private int getRecyclerViewItemCount(){
        RecyclerView recyclerView =  mActivityTestRule.getActivity().findViewById(R.id.newsItemRecyclerView);
        return recyclerView.getAdapter().getItemCount();
    }


    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

}
