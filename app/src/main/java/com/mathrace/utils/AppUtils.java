package com.mathrace.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mathrace.R;

public class AppUtils {


    public static boolean isActivityAvailable(Context activity) {

        if (activity == null) {
            return false;
        }

        if (activity instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) activity).isDestroyed()) {
                    return false;
                }
            }

            if (((Activity) activity).isFinishing()) {
                return false;
            }
        }

        return true;
    }


}
