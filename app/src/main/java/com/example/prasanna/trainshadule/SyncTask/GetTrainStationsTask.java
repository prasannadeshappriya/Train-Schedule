package com.example.prasanna.trainshadule.SyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.prasanna.trainshadule.Utilities.Constants;
import com.example.prasanna.trainshadule.DAO.TrainLinesDAO;
import com.example.prasanna.trainshadule.DAO.TrainStationDAO;
import com.example.prasanna.trainshadule.Models.TrainStation;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;

/**
 * Created by prasanna on 4/21/17.
 */

public class GetTrainStationsTask extends Task {
    private int lineId;
    private TrainStationDAO trainStationDAO = new TrainStationDAO(context);
    private TrainLinesDAO trainLinesDAO = new TrainLinesDAO(context);

    public GetTrainStationsTask(Context _context, ProgressDialog _pd, int _lineId) {
        super(_context, _pd); this.lineId = _lineId;
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
            String methodName;
            if(lineId==0) {
                methodName = "getAllStations";
            }else{
                methodName = "getStations";
            }
            ArrayList<Integer> arrLineId = trainLinesDAO.getLineIdArray();

            for(int line_id : arrLineId) {
                SoapObject request = new SoapObject(Constants.NAMESPACE, methodName);
                if(lineId==0) {
                    request.addProperty("line", String.valueOf(lineId));
                }else{
                    request.addProperty("line", String.valueOf(line_id));
                }
                request.addProperty("lang", "en");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(Constants.ENDPOINT);
                String actionName = methodName;

                httpTransport.call(actionName, envelope);

                Log.i(Constants.TAG, "Result :- " + envelope.bodyOut.toString());
                Log.i(Constants.TAG, "Result :- " + envelope.bodyIn.toString());

                request = (SoapObject) envelope.bodyIn;
                if (trainStationDAO.getItemCount(String.valueOf(line_id)) == 0) {
                    Log.i(Constants.TAG, "Train station table is empty, Inserting data ...");
                    for (int i = 0; i < request.getPropertyCount(); i++) {
                        SoapObject result = (SoapObject) request.getProperty(i);
                        trainStationDAO.addTrainStation(
                                new TrainStation(
                                        String.valueOf(result.getProperty("StationCode")),
                                        String.valueOf(result.getProperty("StationNameEng")),
                                        String.valueOf(line_id)
                                )
                        );
                    }
                } else {
                    if (lineId != 0) {
                        Log.i(Constants.TAG, "Updating Train station table, Please wait ...");
                        ArrayList<TrainStation> arrTrainStation = new ArrayList<>();

                        for (int i = 0; i < request.getPropertyCount(); i++) {
                            SoapObject result = (SoapObject) request.getProperty(i);
                            arrTrainStation.add(
                                    new TrainStation(
                                            String.valueOf(result.getProperty("StationCode")),
                                            String.valueOf(result.getProperty("StationNameEng")),
                                            String.valueOf(line_id)
                                    )
                            );
                        }
                        trainStationDAO.updateTrainStations(arrTrainStation, String.valueOf(line_id));
                    }
                }
                if(lineId==0){break;}
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
    }
}
