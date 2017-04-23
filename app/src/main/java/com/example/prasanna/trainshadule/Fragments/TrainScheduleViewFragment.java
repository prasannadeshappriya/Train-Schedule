package com.example.prasanna.trainshadule.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prasanna.trainshadule.ListAdapters.TrainScheduleAdapter;
import com.example.prasanna.trainshadule.Models.TrainSchedule;
import com.example.prasanna.trainshadule.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prasanna on 4/22/17.
 */

public class TrainScheduleViewFragment extends Fragment {
    private ArrayList<TrainSchedule> arrTrainSchedle;
    private ListView lstTrainSchedule;
    private TextView tvTrainScheduleDesc;
    private TextView tvTrainScheduleDate;
    private HashMap<String,String> hashDesc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train_schedule_view,container,false);
        lstTrainSchedule = (ListView) view.findViewById(R.id.lstTrainSchedule);
        if(!arrTrainSchedle.isEmpty()) {
            TrainScheduleAdapter adapter = new TrainScheduleAdapter(getContext(), arrTrainSchedle);
            lstTrainSchedule.setAdapter(adapter);
        }
        tvTrainScheduleDesc = (TextView) view.findViewById(R.id.tvTrainScheduleDesc);
        tvTrainScheduleDate = (TextView) view.findViewById(R.id.tvTrainScheduleDate);
        tvTrainScheduleDesc.setText(
                        hashDesc.get("from_station") + " - " +
                        hashDesc.get("to_station")
        );
        tvTrainScheduleDate.setText(hashDesc.get("date"));
        return view;
    }

    public void setTrainScheduleArray(ArrayList<TrainSchedule> arrTrainSchedule){
        this.arrTrainSchedle = arrTrainSchedule;
    }

    public void setTrainScheduleDesc(HashMap<String,String> hashDesc){
        this.hashDesc = hashDesc;
    }
}
