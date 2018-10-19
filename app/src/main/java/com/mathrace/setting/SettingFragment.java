package com.mathrace.setting;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mathrace.R;
import com.mathrace.utils.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingFragment extends BaseFragment {
    //    @BindView(R.id.onSpotRegistration)
    Button onSpotRegistration;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind( this, view);
        onSpotRegistration = (Button) view.findViewById(R.id.onSpotRegistration);
        onSpotRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSpotRegistrationInvoke();
            }
        });
        return view;
    }


//    @OnClick(R.id.onSpotRegistration)
    public void onSpotRegistrationInvoke() {
        RegistrationFragment registrationFragment = new RegistrationFragment();
        String transactionName = registrationFragment.getClass().getSimpleName();
        changeFragment(registrationFragment, true, transactionName, transactionName);
    }

}
