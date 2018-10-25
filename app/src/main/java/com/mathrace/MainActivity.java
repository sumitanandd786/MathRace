package com.mathrace;

import android.os.Bundle;
import android.app.Activity;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mathrace.fragment.DashboardFragment;
import com.mathrace.utils.BaseActivity;
import com.mathrace.utils.Constants;

public class MainActivity extends BaseActivity {//implements AddFragmentCallBack{

    private Activity activity;
    public static FrameLayout mFragmentContainer;
    public static Toolbar toolbar;
    boolean flagForBackPressed = false;
    DashboardFragment dashboardMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        if (savedInstanceState == null) {
            if (dashboardMainFragment == null)
                dashboardMainFragment = new DashboardFragment();
            changeFragment(dashboardMainFragment, true, Constants.FragmentTags.DashboardMain, Constants.FragmentTags.DashboardMain);
        }
        //loadFragment(new SettingFragment(), "setting");
    }

    /*public void loadFragment(Fragment fragment, String tag) {
        if (AppUtils.isActivityAvailable(activity)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentFrame, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
        }
    }*/



//    @Override
//    public void changeFragment(Fragment targetFragment, boolean addToBackStack, String transactionName, String tag) {
//        try {
//            if (AppUtils.isActivityAvailable(activity)) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentFrame, targetFragment, tag);
//                if (addToBackStack)
//                    fragmentTransaction.addToBackStack(transactionName);
//                fragmentTransaction.commit();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (flagForBackPressed) {
                finish();

            }
            flagForBackPressed = true;
            Toast.makeText(activity, "press Back again to exit", Toast.LENGTH_SHORT).show();
            //0UtilityMethod.ShowSnackBarNotification("press Back again to exit");
        } else {
            flagForBackPressed = false;
            super.onBackPressed();
        }    }
}
