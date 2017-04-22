package com.example.prasanna.trainshadule.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.example.prasanna.trainshadule.Constants.Constants;
import com.example.prasanna.trainshadule.Models.TrainStation;

import java.util.ArrayList;

/**
 * Created by prasanna on 4/21/17.
 */

public class TrainStationDAO extends DAO {
    private Context context;
    private String tableName = "train_stations";
    private String command;

    public TrainStationDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void addTrainStation(TrainStation trainStation){
        ContentValues contentValues = new ContentValues();
        contentValues.put("station_code",trainStation.getStationCode());
        contentValues.put("station_name",trainStation.getStationName());
        contentValues.put("line_code",trainStation.getLineCode());
        sqldb.insert(
                tableName,
                null,
                contentValues
        );
        Log.i(Constants.TAG, "Train Stations insert successfully!");
    }

    private int getIndex(ArrayList<TrainStation> lstTrainStations, TrainStation trainStation){
        for(int i=0; i<lstTrainStations.size(); i++){
            if(lstTrainStations.get(i).getStationName().equals(trainStation.getStationName())){
                return i;
            }
        }
        return -1;
    }

    public int getItemCount(String line_code){
        command = "SELECT station_name FROM " + tableName + " WHERE line_code =\"" + line_code + "\";";
        Log.i(Constants.TAG,"Query [GetItemCount method in TrainStationDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command, null);
        return c.getCount();
    }

    public ArrayList<TrainStation> getTrainStations(){
        command = "SELECT * FROM " + tableName + " WHERE 1;";
        Log.i(Constants.TAG,"Query [getTrainStations method in TrainStationDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command,null);
        Log.i(Constants.TAG,"Cursor count [getTrainStations method in TrainStationDAO] :- " + c.getCount());

        ArrayList<TrainStation> arrTrainStations = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                arrTrainStations.add(
                        new TrainStation(
                                c.getString(c.getColumnIndex("station_code")),
                                c.getString(c.getColumnIndex("station_name")),
                                c.getString(c.getColumnIndex("line_code"))
                        )
                );
            } while (c.moveToNext());
        }
        return arrTrainStations;
    }

    public String getTrainStationCode(String TrainStationName){
        command = "SELECT station_code FROM " + tableName + " WHERE station_name =\"" + TrainStationName + "\";";
        Log.i(Constants.TAG,"Query [getTrainStationCode method in TrainStationDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command,null);
        Log.i(Constants.TAG,"Cursor count [getTrainStationCode method in TrainStationDAO] :- " + c.getCount());

        ArrayList<String> arrStationCode = new ArrayList<>();
        if(c.getCount()==0){return "";}
        if(c.moveToFirst()) {
            arrStationCode.add(
                    c.getString(c.getColumnIndex("station_code"))
            );
        }
        return arrStationCode.get(0);
    }

    public ArrayList<String> getTrainStationName(){
        command = "SELECT DISTINCT station_name FROM " + tableName + " WHERE 1 GROUP BY station_name ORDER BY station_name;";
        Log.i(Constants.TAG,"Query [getTrainStations method in TrainStationDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command,null);
        Log.i(Constants.TAG,"Cursor count [getTrainStations method in TrainStationDAO] :- " + c.getCount());

        ArrayList<String> arrTrainStationNames = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                if(!c.getString(c.getColumnIndex("station_name")).equals("anyType{}")) {
                    arrTrainStationNames.add(
                            c.getString(c.getColumnIndex("station_name"))
                    );
                }
            } while (c.moveToNext());
        }

        return arrTrainStationNames;
    }

    public void tmp(){
        command = "DELETE FROM " + tableName + ";";
        sqldb.execSQL(command);
    }

    public void updateTrainStations(ArrayList<TrainStation> arrTrainStationsNew, String lineCode){

        command = "SELECT * FROM " + tableName + " WHERE line_code =\"" + lineCode + "\";";
        Log.i(Constants.TAG,"Query [updateTrainStations method in TrainStationDAO] :- " + command);
        Cursor c = sqldb.rawQuery(command,null);
        Log.i(Constants.TAG,"Cursor count [updateTrainStations method in TrainStationDAO] :- " + c.getCount());

        ArrayList<TrainStation> arrTrainStation = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                arrTrainStation.add(
                        new TrainStation(
                                c.getString(c.getColumnIndex("station_code")),
                                c.getString(c.getColumnIndex("station_name")),
                                c.getString(c.getColumnIndex("line_code"))
                        ));
            } while (c.moveToNext());
        }

        //Check weather data is updated or up-to-date
        boolean isUpdated = false;

        ArrayList<Integer> arrRemoveExceptIndex = new ArrayList<>();
        for(TrainStation trainStation : arrTrainStationsNew) {
            String station_code = trainStation.getStationCode();
            String station_name = trainStation.getStationName();
            String line_code = trainStation.getLineCode();

            boolean con = true;
            for (int i = 0; i < arrTrainStation.size(); i++) {
                if (arrTrainStation.get(i).getStationCode().equals(station_code)) {
                    con = false;
                    if(arrTrainStation.get(i).getStationName().equals(station_name)) {
                        arrRemoveExceptIndex.add(getIndex(arrTrainStation, trainStation));
                        break;
                    }else {
                        //Should update Station name for station id
                        isUpdated = true;
                        command = "UPDATE " + tableName + " SET station_name =\"" + station_name + "\" WHERE station_code =\"" + station_code + "\";";
                        sqldb.execSQL(command);
                        arrRemoveExceptIndex.add(getIndex(arrTrainStation, trainStation));
                        Log.i(Constants.TAG, "Statino name is successfully updated to " + station_name + " [Station_ID - " + station_code + "]");
                        break;
                    }
                }
            }

            if(con) {
                isUpdated = true;
                ContentValues contentValues = new ContentValues();
                contentValues.put("line_code",line_code);
                contentValues.put("station_name",station_name);
                contentValues.put("station_code",station_code);
                sqldb.insert(
                        tableName,
                        null,
                        contentValues
                );
                arrRemoveExceptIndex.add(getIndex(arrTrainStation, trainStation));
                Log.i(Constants.TAG,"Station name :- " + station_name + " [Station_Code - " + station_code + "] is not found and updated");
            }
        }

        for (int i = 0; i< arrTrainStation.size(); i++){
            boolean con = true;
            for(int j=0; j<arrRemoveExceptIndex.size(); j++){
                if(i==j){
                    con = false;
                    break;
                }
            }
            if(con){
                isUpdated = true;
                TrainStation trainStation = arrTrainStation.get(i);
                command = "DELETE FROM " + tableName + " WHERE station_name =\"" + trainStation.getStationName() + "\" AND station_code =\"" + trainStation.getStationCode() + "\";";
                Log.i(Constants.TAG, "Item deleted [ID :- " + trainStation.getStationCode() + ", Name :- " + trainStation.getStationName() + "]");
            }
        }

        Log.i(Constants.TAG, (isUpdated) ? "Some data is updated!" : "Data is up to date!");
    }
}
