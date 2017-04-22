package com.example.prasanna.trainshadule.ServerRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.prasanna.trainshadule.Constants.Constants;
import com.example.prasanna.trainshadule.SyncTask.GetDelaysTask;
import com.example.prasanna.trainshadule.SyncTask.GetLinesTask;
import com.example.prasanna.trainshadule.SyncTask.GetRatesTask;
import com.example.prasanna.trainshadule.SyncTask.GetScheduleTask;
import com.example.prasanna.trainshadule.SyncTask.GetTrainStationsTask;
import com.example.prasanna.trainshadule.SyncTask.Task;

import java.util.Calendar;

/**
 * Created by prasanna on 4/12/17.
 */

public class Request {
    private Context context;
    private ProgressDialog pd;

    public Request(Context context, ProgressDialog pd){
        this.context = context;
        this.pd = pd;
    }

    public void getLines(){
        Task getLinesTask = new GetLinesTask(context,pd);
        getLinesTask.execute();
    }

    public void getTrainStations(int lineId){
        Task getTrainStationTask = new GetTrainStationsTask(context,pd,lineId);
        getTrainStationTask.execute();
    }

    public void getTrainSchedule(String fromCode, String toCode){
        Calendar now = Calendar.getInstance();
        String todayDate = String.format("%1$tY-%1$tm-%1$td", now);
        String todayTime = String.format("%1$tH:%1$tM:%1$tS", now);

        Log.i(Constants.TAG, "Today Date :- " + todayDate + " [Format - '%1$tY-%1$tm-%1$td']");
        Log.i(Constants.TAG, "Today Time :- " + todayTime + " [Format - '%1$tH:%1$tM:%1$tS']");

        Task getTrainScheduleTask = new GetScheduleTask(context,pd,
                fromCode,
                toCode,
                "00:00:00", //Replace with todayTime for view next available trains
                "23:59:59",
                todayDate,
                todayTime
        );
        getTrainScheduleTask.execute();
    }

    public void getTrainDelays(){
        Calendar now = Calendar.getInstance();
        String todayDate = String.format("%1$tY-%1$tm-%1$td", now);
        String todayTime = String.format("%1$tH:%1$tM:%1$tS", now);

        Task getTrainDelaysTask = new GetDelaysTask(context,pd,
                todayDate,
                todayTime
        );
        getTrainDelaysTask.execute();
    }

    public void getTrainRates(){
        Task getTrainRatesTask = new GetRatesTask(context,pd,
                "FOT",
                "BPT"
        );
        getTrainRatesTask.execute();
    }
}
