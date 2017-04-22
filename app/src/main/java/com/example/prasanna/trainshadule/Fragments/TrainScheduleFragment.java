package com.example.prasanna.trainshadule.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prasanna.trainshadule.Constants.Constants;
import com.example.prasanna.trainshadule.DAO.DAO;
import com.example.prasanna.trainshadule.DAO.TrainLinesDAO;
import com.example.prasanna.trainshadule.DAO.TrainStationDAO;
import com.example.prasanna.trainshadule.Models.TrainStation;
import com.example.prasanna.trainshadule.R;
import com.example.prasanna.trainshadule.ServerRequest.Request;

import java.util.ArrayList;

/**
 * Created by prasanna on 4/22/17.
 */

public class TrainScheduleFragment extends Fragment {
    private Button btnTest;
    private ProgressDialog pd;
    private Request request;
    private TrainStationDAO trainStationDAO;
    private AutoCompleteTextView tvFromStation;
    private AutoCompleteTextView tvToStation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train_schedule,container,false);

        //Initialize
        pd = new ProgressDialog(getContext());
        btnTest = (Button) view.findViewById(R.id.btnTest);
        request = new Request(getContext(),pd);
        tvFromStation = (AutoCompleteTextView) view.findViewById(R.id.tvFromStation);
        tvToStation = (AutoCompleteTextView) view.findViewById(R.id.tvTOStation);
        trainStationDAO = new TrainStationDAO(getContext());

        ArrayList<String> arrTrainStations = trainStationDAO.getTrainStationName();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,arrTrainStations);
        tvFromStation.setAdapter(adapter);
        tvToStation.setAdapter(adapter);

        //Listeners
        btnTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        test_method();
                    }
                }
        );

        return view;
    }

    private void test_method(){
        //TrainStationDAO a = new TrainStationDAO(this);
        //a.tmp();
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
            request.getTrainSchedule(fromCode,toCode);
        }
    }
}
