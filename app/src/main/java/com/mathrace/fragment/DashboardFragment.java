package com.mathrace.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;

import com.mathrace.MathRaceApplication;
import com.mathrace.R;
import com.mathrace.interfaces.AddFragmentCallBack;
import com.mathrace.utils.Chronometer;
import com.mathrace.utils.emotionrating.BaseRating;
import com.mathrace.utils.emotionrating.SmileRating;

public class DashboardFragment extends Fragment implements SmileRating.OnSmileySelectionListener, SmileRating.OnRatingSelectedListener,View.OnClickListener,Chronometer.OnChronometerTickListener {
    private static final String TAG = "DashboardFragment";
    private AddFragmentCallBack mListener;
    MathRaceApplication mathRaceApplication;
    private View parentView;
    private SmileRating mSmileRating;
    private Button mStartStopButton, mResetButton;
    private Chronometer mStopWatchChronometer;
    private TextClock mCurrentTimeClock;
    private boolean isStart;

    public DashboardFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init();
        return parentView;
    }

    public void init() {
        mStartStopButton = (Button) parentView.findViewById(R.id.dashboard_start_stop_btn);
        mResetButton = (Button) parentView.findViewById(R.id.dashboard_reset_btn);
        mStopWatchChronometer = (Chronometer)parentView.findViewById(R.id.dashboard_stopwatch_chrono);
        mCurrentTimeClock = (TextClock)parentView.findViewById(R.id.dashboard_clock_tc);
        mSmileRating = (SmileRating) parentView.findViewById(R.id.ratingView);

        //mCurrentTimeClock.setFormat12Hour("hh:mm:ss a");

        mSmileRating.setOnSmileySelectionListener(this);
        mSmileRating.setOnRatingSelectedListener(this);
        mStartStopButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);
    }

    @Override
    public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:
                Log.i(TAG, "Bad");
                break;
            case SmileRating.GOOD:
                Log.i(TAG, "Good");
                break;
            case SmileRating.GREAT:
                Log.i(TAG, "Great");
                break;
            case SmileRating.OKAY:
                Log.i(TAG, "Okay");
                break;
            case SmileRating.TERRIBLE:
                Log.i(TAG, "Terrible");
                break;
            case SmileRating.NONE:
                Log.i(TAG, "None");
                break;
        }
    }


    @Override
    public void onRatingSelected(int level, boolean reselected) {
        Log.i(TAG, "Rated as: " + level + " - " + reselected);
    }

    public void startStopChronometer(View view){
        if(isStart){
            mStopWatchChronometer.stop();
            isStart = false;
            mResetButton.setAlpha(1.0f);
            mResetButton.setEnabled(true);
            mStartStopButton.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.deshboard_start_gradient));
            ((Button)view).setText(getActivity().getString(R.string.start_btn_text));
        }else{
            mStopWatchChronometer.setBase(SystemClock.elapsedRealtime());
            mStopWatchChronometer.start();
            isStart = true;
            mResetButton.setAlpha(0.5f);
            mResetButton.setEnabled(false);
            mStartStopButton.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.TomatoRed));
            ((Button)view).setText(getActivity().getString(R.string.stop_btn_txt));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddFragmentCallBack) {
            mListener = (AddFragmentCallBack) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dashboard_start_stop_btn:
                startStopChronometer(mStartStopButton);
                break;

            case R.id.dashboard_reset_btn:
                mStopWatchChronometer.setBase(SystemClock.elapsedRealtime());
                mStopWatchChronometer.stop();
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        mStopWatchChronometer = chronometer;
    }
}
