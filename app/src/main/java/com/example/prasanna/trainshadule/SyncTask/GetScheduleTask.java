package com.example.prasanna.trainshadule.SyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.prasanna.trainshadule.Constants.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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

    public GetScheduleTask(Context _context, ProgressDialog _pd,
                           String fromStationCode,
                           String toStationCode,
                           String arrivalTime,
                           String depatureTime,
                           String currentDate,
                           String currentTime) {
        super(_context, _pd);

        this.fromStationCode = fromStationCode;
        this.toStationCode = toStationCode;
        this.arrivalTime = arrivalTime;
        this.depatureTime = depatureTime;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
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
            String a = "Test"; //for debug request

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
    }
}
