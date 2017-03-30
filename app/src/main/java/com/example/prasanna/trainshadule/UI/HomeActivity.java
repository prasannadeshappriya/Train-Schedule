package com.example.prasanna.trainshadule.UI;

import android.app.ProgressDialog;
import android.content.Context;
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
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize
        pd = new ProgressDialog(this);
        btnTest = (Button) findViewById(R.id.btnTest);

        //Listeners
        btnTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        test_method();
                    }
                }
        );
    }

    private void test_method(){
        test test = new test(this);
        test.execute();
    }


    private class test extends AsyncTask<Void,Void,Void>{
        private Context context;
        public test(Context _context){
            this.context = _context;
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
        protected Void doInBackground(Void... params) {
            try {
                String methodName = "getLines";
                SoapObject request = new SoapObject(Constants.NAMESPACE,methodName);
                request.addProperty("lang","en");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(Constants.ENDPOINT);
                String actionName = methodName;

                httpTransport.call(actionName, envelope);

                //Need to check both of them seperately
                Log.i(Constants.TAG,"Result :- " + envelope.bodyOut.toString());
                Log.i(Constants.TAG,"Result :- " + envelope.bodyIn.toString());

                request = (SoapObject)envelope.bodyIn;
                String a = "Test"; //for debug request

            }catch (Exception e){
                Log.i(Constants.TAG,"Error on test AsyncTask :- " + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            pd.dismiss();
            Log.i(Constants.TAG,"Process test successfully executed");
            Toast.makeText(context,"Done",Toast.LENGTH_LONG).show();
        }
    }
}
