package com.mathrace.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mathrace.R;
import com.mathrace.utils.setting.TimePickerDialog;
import com.mathrace.utils.BaseFragment;


public class SettingFragment extends BaseFragment {
    Button onSpotRegistration, adjustStartTimer;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        onSpotRegistration = view.findViewById(R.id.onSpotRegistration);
        adjustStartTimer = view.findViewById(R.id.adjustStartTimer);
        onSpotRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSpotRegistrationInvoke();
            }
        });
        adjustStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustStartTimerInvoke();
            }
        });


    }


    public void onSpotRegistrationInvoke() {
        RegistrationFragment registrationFragment = new RegistrationFragment();
        String transactionName = registrationFragment.getClass().getSimpleName();
        changeFragment(registrationFragment, true, transactionName, transactionName);
    }

    public void adjustStartTimerInvoke() {
        TimePickerDialog timePickerDialog = (TimePickerDialog) getActivity().getSupportFragmentManager().findFragmentByTag(TimePickerDialog.class.getSimpleName());
        if (timePickerDialog == null || !timePickerDialog.isVisible()) {
            timePickerDialog = new TimePickerDialog();
        }
        String transactionName = timePickerDialog.getClass().getSimpleName();
        changeFragment(timePickerDialog, true, transactionName, transactionName);

        timePickerDialog.sendData = new TimePickerDialog.SendData()

        {
            @Override
            public void sendData(String s) {
//                tv_answer2.setText(s);
//                timePickerDialog.closeFragment();
            }
        }

        ;
    }

}
