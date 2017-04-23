package com.example.prasanna.trainshadule.SyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.prasanna.trainshadule.Utilities.Constants;
import com.example.prasanna.trainshadule.Fragments.TrainScheduleFragment;
import com.example.prasanna.trainshadule.Models.TrainSchedule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by prasanna on 4/21/17.
 */

public class GetScheduleTask extends Task {
    private String fromStationCode;
    private String toStationCode;
    private String arrivalTime;
    private String depatureTime;
    private String currentDate;
    private String currentTime;
    private ArrayList<TrainSchedule> arrTrainSchedle;
    private TrainScheduleFragment fragment;

    public GetScheduleTask(Context _context, ProgressDialog _pd,
                           String fromStationCode,
                           String toStationCode,
                           String arrivalTime,
                           String depatureTime,
                           String currentDate,
                           String currentTime,
                           TrainScheduleFragment fragment) {
        super(_context, _pd);

        this.fromStationCode = fromStationCode;
        this.toStationCode = toStationCode;
        this.arrivalTime = arrivalTime;
        this.depatureTime = depatureTime;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        pd.setIndeterminate(true);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String methodName = "getSchedule";
            SoapObject request = new SoapObject(Constants.NAMESPACE, methodName);
            request.addProperty("StartStationCode", fromStationCode);
            request.addProperty("EndStationCode", toStationCode);
            request.addProperty("ArrivalTime", arrivalTime);
            request.addProperty("DepatureTime", depatureTime);
            request.addProperty("CurrentDate", currentDate);
            request.addProperty("CurrentTime", currentTime);
            request.addProperty("lang", "en");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(Constants.ENDPOINT);
            String actionName = methodName;

            httpTransport.call(actionName, envelope);

            //Need to check both of them seperately
            Log.i(Constants.TAG, "Result :- " + envelope.bodyOut.toString());
            Log.i(Constants.TAG, "Result :- " + envelope.bodyIn.toString());

            request = (SoapObject) envelope.bodyIn;
            arrTrainSchedle = new ArrayList<>();
            for(int i=0; i<request.getPropertyCount(); i++){
                SoapObject result = (SoapObject) request.getProperty(i);
                TrainSchedule schedule = new TrainSchedule(
                        result.getProperty("name").toString(),
                        result.getProperty("arrival").toString(),
                        result.getProperty("departure").toString(),
                        result.getProperty("destination").toString(),
                        result.getProperty("delay").toString(),
                        result.getProperty("comment").toString(),
                        result.getProperty("fdescriptioneng").toString(),
                        result.getProperty("tydescriptioneng").toString(),
                        result.getProperty("frtrstationnameeng").toString(),
                        result.getProperty("totrstationnameeng").toString()
                );
                arrTrainSchedle.add(schedule);
            }

        } catch (Exception e) {
            Log.i(Constants.TAG, "Error on test AsyncTask :- " + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //super.onPostExecute(aVoid);
        pd.dismiss();
        Log.i(Constants.TAG, "Process test successfully executed");
        Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
        fragment.viewTrainScheduleFragment(arrTrainSchedle);
    }
}
