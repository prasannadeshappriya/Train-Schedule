package com.example.prasanna.trainshadule.SyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by prasanna on 4/21/17.
 */

public class Task extends AsyncTask<Void,Void,Void>{
    public Context context;
    public ProgressDialog pd;

    public Task(Context _context, ProgressDialog _pd) {
        this.pd = _pd;
        this.context = _context;
    }

    /*
        onPreExecute, onPostExecute, doInBackground
        These three methods are override by child classes
     */
    @Override
    protected void onPreExecute() {}

    @Override
    protected void onPostExecute(Void aVoid) {}

    @Override
    protected Void doInBackground(Void... params) {return null;}
}
