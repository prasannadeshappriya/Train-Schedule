package com.example.prasanna.trainshadule.UI;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.prasanna.trainshadule.Constants.Constants;
import com.example.prasanna.trainshadule.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class HomeActivity extends AppCompatActivity {
    private Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        test test = new test();
                        test.execute();
                    }
                }
        );
    }



    private class test extends AsyncTask<Void,Void,Void>{
        public ProgressDialog pd;
        public test(){
            pd = new ProgressDialog(getApplicationContext());
        }
        @Override
        protected Void doInBackground(Void... params) {
            String methodName = "getLines";
            SoapObject request = new SoapObject(Constants.NAMESPACE,methodName);
            request.addProperty("lang","en");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(Constants.ENDPOINT);
            String actionName = methodName;
            try {
                httpTransport.call(actionName, envelope);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"An error occoured while getting data from the server",Toast.LENGTH_LONG).show();
                Log.i(Constants.TAG,"Error on test AsyncTask :- " + e.toString());
            }
            //Need to check both of them seperately
            Log.i(Constants.TAG,"Result :- " + envelope.bodyOut.toString());
            Log.i(Constants.TAG,"Result :- " + envelope.bodyIn.toString());

            request = (SoapObject)envelope.bodyIn;
            String a = "Test"; //for debug request

            return null;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            pd.setIndeterminate(true);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Please wait");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            pd.dismiss();
            Log.i(Constants.TAG,"Process test successfully executed");
        }
    }
}
