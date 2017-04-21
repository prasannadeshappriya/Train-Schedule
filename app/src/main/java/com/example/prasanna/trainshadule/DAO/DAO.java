package com.example.prasanna.trainshadule.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.prasanna.trainshadule.Constants.Constants;
import com.example.prasanna.trainshadule.Database.Database;

/**
 * Created by prasanna on 4/21/17.
 */

public class DAO {
    public Database database;
    public SQLiteDatabase sqldb;

    public DAO(Context context){
        database = new Database(
                context,
                Constants.DATABASE_NAME,
                null,
                Constants.DATABASE_VERSION
        );
        sqldb = database.getDatabase();
    }
}
