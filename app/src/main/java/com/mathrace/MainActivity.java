package com.mathrace;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mathrace.setting.SettingFragment;
import com.mathrace.util.AppUtils;

public class MainActivity extends AppCompatActivity {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        loadFragment(new SettingFragment(), "setting");
    }

    public void loadFragment(Fragment fragment, String tag) {
        if (AppUtils.isActivityAvailable(activity)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentFrame, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
        }
    }

}
