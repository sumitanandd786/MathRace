package com.mathrace.utils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mathrace.R;
import com.mathrace.interfaces.AddFragmentCallBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements AddFragmentCallBack {


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }

    @Override
    public void changeFragment(Fragment fragment, boolean addToBackStack, String transactionName, String tag) {
        try {
            if (AppUtils.isActivityAvailable(getActivity())) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentFrame, fragment, tag);
                if (addToBackStack)
                    fragmentTransaction.addToBackStack(transactionName);
                fragmentTransaction.commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
