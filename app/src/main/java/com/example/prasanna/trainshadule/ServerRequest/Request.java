package com.example.prasanna.trainshadule.ServerRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.prasanna.trainshadule.UI.HomeActivity;
import com.example.prasanna.trainshadule.Utilities.Constants;
import com.example.prasanna.trainshadule.Fragments.TrainScheduleFragment;
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
    private HomeActivity homeActivity;

    public Request(Context context, ProgressDialog pd){
        this.context = context;
        this.pd = pd;
    }

    public Request(Context context, ProgressDialog pd, HomeActivity homeActivity){
        this.context = context;
        this.pd = pd;
        this.homeActivity = homeActivity;
    }

    public void getLines(){
        Task getLinesTask = new GetLinesTask(context,pd,homeActivity);
        getLinesTask.execute();
    }

    public void getTrainSchedule(String fromCode, String toCode, TrainScheduleFragment fragment,
                                 String todayDate, String todayTime, int method){
        //Method
        //0 -> Daily Schedule
        //1 -> Next Train

        Task getTrainScheduleTask = null;
        if(method==0) {
            Log.i(Constants.TAG, "Daily schedule is processing");
            getTrainScheduleTask = new GetScheduleTask(context, pd,
                    fromCode,
                    toCode,
                    "00:00:00",
                    "23:59:59",
                    todayDate,
                    todayTime,
                    fragment
            );
        }else if (method==1){
            Log.i(Constants.TAG, "Next train schedule is processing");
            getTrainScheduleTask = new GetScheduleTask(context, pd,
                    fromCode,
                    toCode,
                    todayTime,
                    "23:59:59",
                    todayDate,
                    todayTime,
                    fragment
            );
        }
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
