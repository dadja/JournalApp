package reptxstudio.journalapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {


    @Rule public final ActivityTestRule<SignInActivity> sigIn = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void ShoudlbeAbleToClickOnGSignInButton(){
          onView(withId(R.id.signin_btn)).perform(click());
    }

}
