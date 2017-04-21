package com.example.prasanna.trainshadule.UI;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.prasanna.trainshadule.R;
import com.example.prasanna.trainshadule.ServerRequest.Request;

public class HomeActivity extends AppCompatActivity {
    private Button btnTest;
    private ProgressDialog pd;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize
        pd = new ProgressDialog(this);
        btnTest = (Button) findViewById(R.id.btnTest);
        request = new Request(this,pd);

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
        request.getTrainDelays();
    }
}
