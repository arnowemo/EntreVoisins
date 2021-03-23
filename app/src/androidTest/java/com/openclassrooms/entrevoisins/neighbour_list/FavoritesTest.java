package com.openclassrooms.entrevoisins.neighbour_list;


import android.preference.PreferenceManager;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;



@RunWith(AndroidJUnit4.class)
public class FavoritesTest {



    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }



    /**
     *  test vérifiant que l’onglet Favoris n’affiche que les voisins marqués comme favoris.
     */

    @Test
    public void test_TabFavorites_OnlyDisplays_NeighboursMarkedAsFavorites(){

        // ajout d'un voisin au favoris "Caroline"
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.add_favorite)).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        // ajout d'un voisin au favoris "Laetitia"
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(6,click()));
        onView(withId(R.id.add_favorite)).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        //click sur l'onglet Favoris
        onView(withContentDescription(R.string.tab_favorites_title)).perform(click());

        // verification que la vue favrois contient 2 items
        onView(ViewMatchers.withId(R.id.list_fav_neighbours))
                .check(withItemCount(2));

        // verification que Caroline et Laetitia sont présentes dans la vue favoris
        onView(ViewMatchers.withId(R.id.list_fav_neighbours))
                .check(matches(hasDescendant(withText("Caroline"))))
                .check(matches(hasDescendant(withText("Laetitia"))));








    }

}
