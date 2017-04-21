package com.example.prasanna.trainshadule.ServerRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.prasanna.trainshadule.Constants.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
        getLinesTask getLinesTask = new getLinesTask(context);
        getLinesTask.execute();
    }

    private class getLinesTask extends AsyncTask<Void,Void,Void> {
        private Context context;

        public getLinesTask(Context _context) {
            this.context = _context;
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
                String methodName = "getLines";
                SoapObject request = new SoapObject(Constants.NAMESPACE, methodName);
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
}
