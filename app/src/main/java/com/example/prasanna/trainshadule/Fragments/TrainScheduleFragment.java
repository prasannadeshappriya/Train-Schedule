package com.example.prasanna.trainshadule.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prasanna.trainshadule.DialogFragments.CalenderFragment;
import com.example.prasanna.trainshadule.UI.HomeActivity;
import com.example.prasanna.trainshadule.Utilities.Constants;
import com.example.prasanna.trainshadule.DAO.TrainStationDAO;
import com.example.prasanna.trainshadule.Models.TrainSchedule;
import com.example.prasanna.trainshadule.R;
import com.example.prasanna.trainshadule.ServerRequest.Request;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by prasanna on 4/22/17.
 */

public class TrainScheduleFragment extends Fragment implements CalenderFragment.EditNameDialogListener {
    private Button btnSearch;
    private ProgressDialog pd;
    private Request request;
    private TrainStationDAO trainStationDAO;
    private AutoCompleteTextView tvFromStation;
    private AutoCompleteTextView tvToStation;
    private CheckBox chkNextTrain;
    private CheckBox chkDailySchedule;
    private TextView tvDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train_schedule,container,false);

        //Initialize
        pd = new ProgressDialog(getContext(),R.style.AppTheme_Dark_Dialog);
        btnSearch = (Button) view.findViewById(R.id.btnTest);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        request = new Request(getContext(),pd);
        tvFromStation = (AutoCompleteTextView) view.findViewById(R.id.tvFromStation);
        tvToStation = (AutoCompleteTextView) view.findViewById(R.id.tvTOStation);
        trainStationDAO = new TrainStationDAO(getContext());
        chkNextTrain = (CheckBox) view.findViewById(R.id.chkNextTrain);
        chkDailySchedule = (CheckBox) view.findViewById(R.id.chkDailySchedule);
        chkDailySchedule.setChecked(true);
        chkNextTrain.setChecked(false);

        ArrayList<String> arrTrainStations = trainStationDAO.getTrainStationName();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,arrTrainStations);
        tvFromStation.setAdapter(adapter);
        tvToStation.setAdapter(adapter);

        //Listeners
        btnSearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search();
                    }
                }
        );

        chkDailySchedule.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chkDailySchedule.setChecked(true);
                        chkNextTrain.setChecked(false);
                    }
                }
        );

        chkNextTrain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chkNextTrain.setChecked(true);
                        chkDailySchedule.setChecked(false);
                    }
                }
        );

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        tvDate.setText(String.format("%1$tY-%1$tm-%1$td", now));
        tvDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getFragmentManager();
                        CalenderFragment calenderFragment = new CalenderFragment();
                        String[] date = tvDate.getText().toString().split("-");
                        calenderFragment.setDate(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
                        calenderFragment.setTargetFragment(TrainScheduleFragment.this, 300);
                        calenderFragment.show(fm, "calender_fragment");
                    }
                }
        );

        tvToStation.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        closeKeyboard();
                    }
                }
        );

        tvFromStation.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        closeKeyboard();
                    }
                }
        );

        return view;
    }

    public void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void synchronize(Context context, ProgressDialog pd, HomeActivity activity){
        Request request = new Request(context,pd, activity);
        request.getLines();
    }

    private void search(){
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        String todayDate = tvDate.getText().toString();
        String todayTime = String.format("%1$tH:%1$tM:%1$tS", now);

        int method;
        if(chkDailySchedule.isChecked()){
            method = 0;
        }else{
            method = 1;
        }

        tvToStation.setText(tvToStation.getText().toString().toUpperCase());
        tvFromStation.setText(tvFromStation.getText().toString().toUpperCase());
        String fromCode = trainStationDAO.getTrainStationCode(tvFromStation.getText().toString());
        String toCode = trainStationDAO.getTrainStationCode(tvToStation.getText().toString().toUpperCase());

        if(fromCode.equals("")){
            Toast.makeText(getContext(), tvFromStation.getText().toString() + " is an invalid station name!", Toast.LENGTH_LONG).show();
        }else if(toCode.equals("")){
            Toast.makeText(getContext(), tvToStation.getText().toString() + " is an invalid station name!", Toast.LENGTH_LONG).show();
        }else {
            Log.i(Constants.TAG, "From :- " + fromCode + ", To :- " + toCode);
            request.getTrainSchedule(fromCode,toCode,this,todayDate,todayTime,method);
        }
    }

    public void viewTrainScheduleFragment(ArrayList<TrainSchedule> arrTrainScheduleResult){
        TrainScheduleViewFragment trainScheduleViewFragment = new TrainScheduleViewFragment();

        //Tempory Hardcode Data
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        String todayDate = String.format("%1$tY-%1$tm-%1$td", now);

        trainScheduleViewFragment.setTrainScheduleArray(arrTrainScheduleResult);
        HashMap<String,String> map = new HashMap<>();
        map.put("from_station", tvFromStation.getText().toString());
        map.put("to_station", tvToStation.getText().toString());
        map.put("date",todayDate);
        trainScheduleViewFragment.setTrainScheduleDesc(map);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frmMain, trainScheduleViewFragment);
        transaction.commit();
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        tvDate.setText(inputText);
    }
}
