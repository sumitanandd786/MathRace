package com.mathrace.interfaces;

import android.support.v4.app.Fragment;

public interface AddFragmentCallBack {
    void changeFragment(Fragment fragment,boolean addToBackStack,String transactionName,String tag);
}
