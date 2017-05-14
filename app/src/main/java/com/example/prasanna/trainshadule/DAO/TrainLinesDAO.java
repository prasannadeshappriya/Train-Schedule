package com.example.prasanna.trainshadule.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.prasanna.trainshadule.Utilities.Constants;
import com.example.prasanna.trainshadule.Models.TrainLine;

import java.util.ArrayList;

/**
 * Created by prasanna on 4/21/17.
 */

public class TrainLinesDAO extends DAO {
    private Context context;
    private String tableName = "train_lines";
    private String command;

    public TrainLinesDAO(Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Integer> getLineIdArray(){
        command = "SELECT * FROM " + tableName + " WHERE 1;";
        Log.i(Constants.TAG,"Query [getLineIdArray method in TrainLinesDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command,null);
        Log.i(Constants.TAG,"Cursor count [getLineIdArray method in TrainLinesDAO] :- " + c.getCount());

        ArrayList<Integer> arrTrainLineId = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                arrTrainLineId.add(Integer.parseInt(c.getString(c.getColumnIndex("line_id"))));
            } while (c.moveToNext());
        }

        return arrTrainLineId;
    }

    public void addTrainLines(TrainLine trainLine){
        ContentValues contentValues = new ContentValues();
        contentValues.put("line_id",trainLine.getLine_id());
        contentValues.put("line_name",trainLine.getLine_name());
        sqldb.insert(
                tableName,
                null,
                contentValues
        );
        Log.i(Constants.TAG, "TrainLines [line_id - " + trainLine.getLine_id()
                    + ", line_name - " + trainLine.getLine_name() + "] insert successfully!");
    }

    public int getItemCount(){
        command = "SELECT line_id FROM " + tableName + ";";
        Log.i(Constants.TAG,"Query [GetItemCount method in TrainLinesDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command, null);
        return c.getCount();
    }

    private int getIndex(ArrayList<TrainLine> lstTrainLines, TrainLine trainLine){
        for(int i=0; i<lstTrainLines.size(); i++){
            if(lstTrainLines.get(i).getLine_name().equals(trainLine.getLine_name())){
                return i;
            }
        }
        return -1;
    }

    public void updateTrainLines(ArrayList<TrainLine> arrTrainLineNew){
        command = "SELECT * FROM " + tableName + " WHERE 1;";
        Log.i(Constants.TAG,"Query [UpdateTrainLines method in TrainLinesDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command,null);
        Log.i(Constants.TAG,"Cursor count [UpdateTrainLines method in TrainLinesDAO] :- " + c.getCount());

        ArrayList<TrainLine> arrTrainLine = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                arrTrainLine.add(
                        new TrainLine(
                                c.getString(c.getColumnIndex("line_id")),
                                c.getString(c.getColumnIndex("line_name"))
                        ));
            } while (c.moveToNext());
        }

        //Check weather data is updated or up-to-date
        boolean isUpdated = false;

        ArrayList<Integer> arrRemoveExceptIndex = new ArrayList<>();
        for(TrainLine trainLine : arrTrainLineNew) {
            String line_id = trainLine.getLine_id();
            String line_name = trainLine.getLine_name();

            boolean con = true;
            for (int i = 0; i < arrTrainLine.size(); i++) {
                if (arrTrainLine.get(i).getLine_id().equals(line_id)) {
                    con = false;
                    if(arrTrainLine.get(i).getLine_name().equals(line_name)) {
                        arrRemoveExceptIndex.add(getIndex(arrTrainLine,trainLine));
                        break;
                    }else {
                        //Should update line name for line id
                        isUpdated = true;
                        command = "UPDATE " + tableName + " SET line_name =\"" +line_name + "\" WHERE line_id =\"" + line_id + "\";";
                        sqldb.execSQL(command);
                        arrRemoveExceptIndex.add(getIndex(arrTrainLine,trainLine));
                        Log.i(Constants.TAG, "Line name is successfully updated to " + line_name + " [Line_ID - " + line_id + "]");
                        break;
                    }
                }
            }

            if(con) {
                isUpdated = true;
                ContentValues contentValues = new ContentValues();
                contentValues.put("line_id",line_id);
                contentValues.put("line_name",line_name);
                sqldb.insert(
                        tableName,
                        null,
                        contentValues
                );
                arrRemoveExceptIndex.add(getIndex(arrTrainLine,trainLine));
                Log.i(Constants.TAG,"Line name :- " + line_name + " [Line_id - " + line_id + "] is not found and updated");
            }
        }

        for (int i=0; i<arrTrainLine.size(); i++){
            boolean con = true;
            for(int j=0; j<arrRemoveExceptIndex.size(); j++){
                if(i==j){
                    con = false;
                    break;
                }
            }
            if(con){
                isUpdated = true;
                TrainLine trainLine = arrTrainLine.get(i);
                command = "DELETE FROM " + tableName + " WHERE line_name =\"" + trainLine.getLine_name() + "\" AND line_id =\"" + trainLine.getLine_id() + "\";";
                Log.i(Constants.TAG, "Item deleted [ID :- " + trainLine.getLine_id() + ", Name :- " + trainLine.getLine_name() + "]");
            }
        }

        Log.i(Constants.TAG, (isUpdated) ? "Some data is updated!" : "Data is up to date!");
    }
}
