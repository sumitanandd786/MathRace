package com.mathrace.utils.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.mathrace.R;
import com.mathrace.utils.BaseFragment;

import java.util.Calendar;

import static java.lang.String.format;

public class TimePickerDialog extends BaseFragment {
    // Time changed flag
    private boolean timeChanged = false;
    private boolean isAM_PM = false;

    // Time scrolled flag
    private boolean timeScrolled = false;
//    private RelativeLayout relativeLayoutOK, relativeLayoutCancel;
    private RelativeLayout rlSync;
    private Button btnSync;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_time_picker, container, false);

//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        final AbstractWheel hours = (AbstractWheel) view.findViewById(R.id.hour);
        hours.setViewAdapter(new NumericWheelAdapter(getActivity(), 0, 23));
//        hours.setCyclic(true);

        final AbstractWheel mins = (AbstractWheel) view.findViewById(R.id.mins);
        mins.setViewAdapter(new NumericWheelAdapter(getActivity(), 0, 59, "%02d"));

        final AbstractWheel seconds = (AbstractWheel) view.findViewById(R.id.seconds);
        seconds.setViewAdapter(new NumericWheelAdapter(getActivity(), 0, 59, "%02d"));
//        mins.setCyclic(true);
        rlSync = view.findViewById(R.id.rlSync);
        btnSync = view.findViewById(R.id.btnSync);

       /* relativeLayoutOK = view.findViewById(R.id.rl_ok);
        relativeLayoutCancel = view.findViewById(R.id.rl_cancel);
        relativeLayoutOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hh = hours.getCurrentItem();
                int mm  = mins.getCurrentItem();
                int ss = seconds.getCurrentItem();
                String str = format("%02d : %02d : %02d", hh, mm, ss);
                if (sendData != null) {
                    if (isAM_PM){
                        if (hh > 12){
                            str = str.concat(" PM");
                        }else {
                            str = str.concat(" AM");
                        }
                        sendData.sendData(str);
                    }else {
                        sendData.sendData(str);
                    }
                }
            }
        });*/
        rlSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               closeFragment();
            }
        });
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               closeFragment();
            }
        });

        // set current time
        Calendar c = Calendar.getInstance();
        int curHours = 0;//c.get(Calendar.HOUR_OF_DAY);
        int curMinutes = 0;//c.get(Calendar.MINUTE);

        hours.setCurrentItem(curHours);
        mins.setCurrentItem(curMinutes);
        // add listeners
        addChangingListener(mins, "min");
        addChangingListener(hours, "hour");

        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;
                    timeChanged = false;
                }
            }
        };
        hours.addChangingListener(wheelListener);
        mins.addChangingListener(wheelListener);

        OnWheelClickedListener click = new OnWheelClickedListener() {
            public void onItemClicked(AbstractWheel wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        hours.addClickingListener(click);
        mins.addClickingListener(click);

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
            public void onScrollingStarted(AbstractWheel wheel) {
                timeScrolled = true;
            }

            public void onScrollingFinished(AbstractWheel wheel) {
                timeScrolled = false;
                timeChanged = true;
                timeChanged = false;
            }
        };

        hours.addScrollingListener(scrollListener);
        mins.addScrollingListener(scrollListener);


        return view;
    }

    public void closeFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

    /**
     * Adds changing listener for spinnerwheel that updates the spinnerwheel label
     *
     * @param wheel the spinnerwheel
     * @param label the spinnerwheel label
     */
    private void addChangingListener(final AbstractWheel wheel, final String label) {
        wheel.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                //spinnerwheel.setLabel(newValue != 1 ? label + "s" : label);
            }
        });
    }

    public SendData sendData;

    public interface SendData {
        void sendData(String s);
    }

    public void setAM_PM(boolean AM_PM) {
        isAM_PM = AM_PM;
    }
}
