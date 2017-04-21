package com.example.prasanna.trainshadule.ServerRequest;

import android.app.ProgressDialog;
import android.content.Context;
import com.example.prasanna.trainshadule.SyncTask.GetLinesTask;

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
        GetLinesTask getLinesTask = new GetLinesTask(context,pd);
        getLinesTask.execute();
    }

}
