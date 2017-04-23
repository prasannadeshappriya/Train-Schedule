package com.example.prasanna.trainshadule.SyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.prasanna.trainshadule.ServerRequest.Request;
import com.example.prasanna.trainshadule.UI.HomeActivity;
import com.example.prasanna.trainshadule.Utilities.Constants;
import com.example.prasanna.trainshadule.DAO.TrainLinesDAO;
import com.example.prasanna.trainshadule.Models.TrainLine;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by prasanna on 4/21/17.
 */

public class GetLinesTask extends Task{
    public GetLinesTask(Context _context, ProgressDialog _pd, HomeActivity homeActivity) {
        super(_context, _pd);
        this.homeActivity = homeActivity;
    }
    private TrainLinesDAO trainLinesDAO = new TrainLinesDAO(context);
    private HomeActivity homeActivity;

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
            String methodName = "getLines";
            SoapObject request = new SoapObject(Constants.NAMESPACE, methodName);
            request.addProperty("lang", "en");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(Constants.ENDPOINT);
            String actionName = methodName;

            httpTransport.call(actionName, envelope);

            Log.i(Constants.TAG, "Result :- " + envelope.bodyOut.toString());
            Log.i(Constants.TAG, "Result :- " + envelope.bodyIn.toString());

            request = (SoapObject) envelope.bodyIn;

            //Insert Data into Database
            if(trainLinesDAO.getItemCount()==0) {
                Log.i(Constants.TAG, "Train line table is empty, Inserting data ...");
                for(int i=0; i<request.getPropertyCount(); i++){
                    SoapObject result = (SoapObject) request.getProperty(i);
                    trainLinesDAO.addTrainLines(
                            new TrainLine(
                                    String.valueOf(result.getProperty("id")),
                                    String.valueOf(result.getProperty("LineName"))
                            )
                    );
                }
            }else{
                Log.i(Constants.TAG, "Updating Train line table, Please wait ...");
                ArrayList<TrainLine> arrTrainLine = new ArrayList<>();

                for(int i=0; i<request.getPropertyCount(); i++){
                    SoapObject result = (SoapObject) request.getProperty(i);
                    arrTrainLine.add(
                            new TrainLine(
                                    String.valueOf(result.getProperty("id")),
                                    String.valueOf(result.getProperty("LineName"))
                            )
                    );
                }
                trainLinesDAO.updateTrainLines(arrTrainLine);
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
        Log.i(Constants.TAG, "GetLine Task successfully executed");
        Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();

        Task getTrainStationTask = new GetTrainStationsTask(context,pd,2,homeActivity);
        getTrainStationTask.execute();
    }
}
