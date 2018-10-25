package com.mathrace.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mathrace.R;
import com.mathrace.utils.AppUtils;
import com.mathrace.utils.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends BaseFragment {

    TextView cancel, register;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        cancel = view.findViewById(R.id.cancel);
        register = view.findViewById(R.id.register);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtils.isActivityAvailable(getActivity()))
                    return;

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtils.isActivityAvailable(getActivity()))
                    return;

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

}
