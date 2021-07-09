package h_mal.appttude.com.driver;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import h_mal.appttude.com.driver.ui.driver.HomeFragment;
import h_mal.appttude.com.driver.ui.driver.MainActivity;

import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    private MainActivity mainActivity = null;
    private HomeFragment hdf;

    @Before
    public void setUp() throws Exception {
        mainActivity = activityActivityTestRule.getActivity();

        hdf = new HomeFragment();
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, hdf).commit();
    }

    @Test
    public void testViews(){
        View view = hdf.getView().findViewById(R.id.driver);

        assertNotNull(view);

//        Bundle bundle = new Bundle();
//        bundle.putInt("selectedListItem", 0);
//        FragmentFactory factory = new FragmentFactory();
//        homeDriverFragment hdf = new homeDriverFragment();
//
//        launchInContainer(hdf.getClass(), bundle, factory);
//        Espresso.onView(ViewMatchers.withId(2131231038)).check(ViewAssertions.matches(ViewMatchers.withText("Hello World!")));
    }

    @After
    public void TearDown() throws Exception{
        mainActivity = null;
    }

}