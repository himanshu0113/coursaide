package com.example.ubantu.coursaide_admin;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityMT17015Test {

    @Rule
    public ActivityTestRule<MainActivity_MT17015> mActivityTestRule = new ActivityTestRule<>(MainActivity_MT17015.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.Signup), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout),
                                        5),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3593303);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.su_email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatEditText.perform(scrollTo(), replaceText("jalaj"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3584966);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.su_email), withText("jalaj"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatEditText2.perform(scrollTo(), replaceText("jalaj1"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.su_email), withText("jalaj1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3584966);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.su_email), withText("jalaj1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatEditText4.perform(scrollTo(), replaceText("jalaj17017@iiitd.ac.in"));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.su_email), withText("jalaj17017@iiitd.ac.in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText5.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.su_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        appCompatEditText6.perform(scrollTo(), replaceText("sherlocked23"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.su_password_confirm),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        appCompatEditText7.perform(scrollTo(), replaceText("sherlocked23"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.su_next), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                12)));
        appCompatButton2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3463092);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.su_credit_comp_editt),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1)));
        appCompatEditText8.perform(scrollTo(), replaceText("8"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.su_mtech_r_button), withText("M.Tech"),
                        childAtPosition(
                                allOf(withId(R.id.mtech_btech),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0)));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.su_add_course_fab),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        11),
                                1)));
        floatingActionButton.perform(scrollTo(), click());

        ViewInteraction autoCompleteTextView = onView(
                childAtPosition(
                        allOf(withId(R.id.dynamicview),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        0));
        autoCompleteTextView.perform(scrollTo(), replaceText("cse508"), closeSoftKeyboard());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.su_add_course_fab),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        11),
                                1)));
        floatingActionButton2.perform(scrollTo(), click());

        ViewInteraction autoCompleteTextView2 = onView(
                childAtPosition(
                        allOf(withId(R.id.dynamicview),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        0));
        autoCompleteTextView2.perform(scrollTo(), replaceText("cse525"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.su_save), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                14)));
        appCompatButton3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3524460);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Semester Courses_MT17015"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3590836);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.add_course_fab),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        floatingActionButton3.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView = onView(
                childAtPosition(
                        allOf(withId(R.id.dynamicview),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        0));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("cse502"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3561498);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.add_course_fab),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        floatingActionButton4.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                childAtPosition(
                        allOf(withId(R.id.dynamicview),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        0));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("cse"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3546537);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withText("cse"),
                        childAtPosition(
                                allOf(withId(R.id.dynamicview),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0)));
        appCompatAutoCompleteTextView3.perform(scrollTo(), replaceText("cse544"));

        ViewInteraction appCompatAutoCompleteTextView4 = onView(
                allOf(withText("cse544"),
                        childAtPosition(
                                allOf(withId(R.id.dynamicview),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView4.perform(closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.clasg_check_button), withText("Check Clash"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                2)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.clashview), withText("\ncse502"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1)));
        appCompatTextView2.perform(scrollTo(), replaceText("\ncse502\ncse544"));

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.clashview), withText("\ncse502\ncse544"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1),
                        isDisplayed()));
        appCompatTextView3.perform(closeSoftKeyboard());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.clashview), withText("\ncse502"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1)));
        appCompatTextView4.perform(scrollTo(), replaceText("\ncse502\ncse544"));

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.clashview), withText("\ncse502\ncse544"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1),
                        isDisplayed()));
        appCompatTextView5.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button3), withText("Save Courses_MT17015"),
                        childAtPosition(
                                allOf(withId(R.id.viewClash),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                0)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton7.perform(scrollTo(), click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.title), withText("TA Courses_MT17015"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView6.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3522733);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.ta_course_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                1)));
        appCompatEditText9.perform(scrollTo(), replaceText("OS"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.checkBox), withText("TUTS"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                4)));
        appCompatCheckBox.perform(scrollTo(), click());

        pressBack();

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.checkBox2), withText("LABS"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                5)));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.checkBox3), withText("LECTURES"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                6)));
        appCompatCheckBox3.perform(scrollTo(), click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.clash_check_button), withText("Check Clash"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                3)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.clash_check_button), withText("Check Clash"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                3)));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.clash_check_button), withText("Check Clash"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                3)));
        appCompatButton10.perform(scrollTo(), click());

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.add_course_fab),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        floatingActionButton5.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView5 = onView(
                childAtPosition(
                        allOf(withId(R.id.dynamicview),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        0));
        appCompatAutoCompleteTextView5.perform(scrollTo(), replaceText("cse502"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.clash_check_button), withText("Check Clash"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.constraintLayout),
                                        0),
                                3)));
        appCompatButton11.perform(scrollTo(), click());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.clashview), withText("\nOS"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1)));
        appCompatTextView7.perform(scrollTo(), replaceText("\nOS\ncse502"));

        ViewInteraction appCompatTextView8 = onView(
                allOf(withId(R.id.clashview), withText("\nOS\ncse502"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1),
                        isDisplayed()));
        appCompatTextView8.perform(closeSoftKeyboard());

        ViewInteraction appCompatTextView9 = onView(
                allOf(withId(R.id.clashview), withText("\nOS"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1)));
        appCompatTextView9.perform(scrollTo(), replaceText("\nOS\ncse502"));

        ViewInteraction appCompatTextView10 = onView(
                allOf(withId(R.id.clashview), withText("\nOS\ncse502"),
                        childAtPosition(
                                withId(R.id.viewClash),
                                1),
                        isDisplayed()));
        appCompatTextView10.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.button3), withText("Save Courses_MT17015"),
                        childAtPosition(
                                allOf(withId(R.id.viewClash),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                0)));
        appCompatButton12.perform(scrollTo(), click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.button3), withText("Save Courses_MT17015"),
                        childAtPosition(
                                allOf(withId(R.id.viewClash),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                0)));
        appCompatButton13.perform(scrollTo(), click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton14.perform(scrollTo(), click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton15.perform(scrollTo(), click());

        ViewInteraction tabView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction tabView2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs),
                                0),
                        0),
                        isDisplayed()));
        tabView2.perform(click());

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3402232);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.su_save), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                14)));
        appCompatButton16.perform(scrollTo(), click());

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3587218);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3598173);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.student_username),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0)));
        appCompatEditText10.perform(scrollTo(), click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.student_username),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0)));
        appCompatEditText11.perform(scrollTo(), click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.student_username),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0)));
        appCompatEditText12.perform(scrollTo(), replaceText("jalaj17017@iiitd.ac "), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.student_password),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText13.perform(scrollTo(), replaceText("sherlocked"), closeSoftKeyboard());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                3)));
        appCompatButton17.perform(scrollTo(), click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText14.perform(scrollTo(), click());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText15.perform(scrollTo(), replaceText("sherlocked23"));

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked23"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText16.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                3)));
        appCompatButton18.perform(scrollTo(), click());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.student_username), withText("jalaj17017@iiitd.ac "),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0)));
        appCompatEditText17.perform(scrollTo(), replaceText("jalaj17017@iiitd.ac.in"));

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.student_username), withText("jalaj17017@iiitd.ac.in"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText18.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked23"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText19.perform(scrollTo(), replaceText("sherlocked"));

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText20.perform(closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(R.id.login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                3)));
        appCompatButton19.perform(scrollTo(), click());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText21.perform(scrollTo(), click());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText22.perform(scrollTo(), click());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText23.perform(scrollTo(), replaceText("sherlocked23"));

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked23"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText24.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.student_password), withText("sherlocked23"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText25.perform(pressImeActionButton());

        pressBack();

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(R.id.login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                3)));
        appCompatButton20.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3474681);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView11 = onView(
                allOf(withId(R.id.title), withText("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView11.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView12 = onView(
                allOf(withId(R.id.title), withText("Settings_MT17015"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView12.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3581102);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView13 = onView(
                allOf(withId(R.id.title), withText("Get Time Table"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView13.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3577944);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView14 = onView(
                allOf(withId(R.id.title), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView14.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3592100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(R.id.Admin_login_button), withText("Login as admin"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                4)));
        appCompatButton21.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3592059);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.admin_username),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0)));
        appCompatEditText26.perform(scrollTo(), replaceText("urvashi17062@iiitd.ac.in"), closeSoftKeyboard());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.admin_password),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        appCompatEditText27.perform(scrollTo(), replaceText("testpassword"), closeSoftKeyboard());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                3)));
        appCompatButton22.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3459725);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView15 = onView(
                allOf(withId(R.id.title), withText("Add new admin"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView15.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3586118);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText28.perform(scrollTo(), click());

        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText29.perform(scrollTo(), replaceText("jasmeet17018@iiitd.ac.in"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3486232);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText30.perform(scrollTo(), replaceText("sherlocked"), closeSoftKeyboard());

        ViewInteraction appCompatButton23 = onView(
                allOf(withId(R.id.addAdminButton), withText("Add Admin"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatButton23.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3530059);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText31 = onView(
                allOf(withId(R.id.usernameEditText), withText("jasmeet17018@iiitd.ac.in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText31.perform(scrollTo(), replaceText("jasmeet17039@iiitd.ac.in"));

        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.usernameEditText), withText("jasmeet17039@iiitd.ac.in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText32.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText33 = onView(
                allOf(withId(R.id.usernameEditText), withText("jasmeet17039@iiitd.ac.in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText33.perform(scrollTo(), click());

        ViewInteraction appCompatEditText34 = onView(
                allOf(withId(R.id.usernameEditText), withText("jasmeet17039@iiitd.ac.in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText34.perform(scrollTo(), replaceText("prerna17039@iiitd.ac.in"));

        ViewInteraction appCompatEditText35 = onView(
                allOf(withId(R.id.usernameEditText), withText("prerna17039@iiitd.ac.in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText35.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(R.id.addAdminButton), withText("Add Admin"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatButton24.perform(scrollTo(), click());

        pressBack();

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView16 = onView(
                allOf(withId(R.id.title), withText("Update Courses_MT17015"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView16.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3524898);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton25 = onView(
                allOf(withId(R.id.ad_c_nav), withText("Add a course"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2)));
        appCompatButton25.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3587091);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText36 = onView(
                allOf(withId(R.id.acCnameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText36.perform(scrollTo(), replaceText("cse641"), closeSoftKeyboard());

        ViewInteraction appCompatEditText37 = onView(
                allOf(withId(R.id.acCcodeEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText37.perform(scrollTo(), replaceText("cse641"), closeSoftKeyboard());

        ViewInteraction appCompatEditText38 = onView(
                allOf(withId(R.id.acCnameEditText), withText("cse641"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText38.perform(scrollTo(), replaceText("Deep Learning"));

        ViewInteraction appCompatEditText39 = onView(
                allOf(withId(R.id.acCnameEditText), withText("Deep Learning"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText39.perform(closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.acSplSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        6),
                                1)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton26 = onView(
                allOf(withId(R.id.acAddButton), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                13)));
        appCompatButton26.perform(scrollTo(), click());

        pressBack();

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3469745);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView17 = onView(
                allOf(withId(R.id.title), withText("Upload Time Table"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView17.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3591452);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView18 = onView(
                allOf(withId(R.id.title), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView18.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3586330);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
