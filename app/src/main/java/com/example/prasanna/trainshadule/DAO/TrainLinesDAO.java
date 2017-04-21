package com.example.prasanna.trainshadule.DAO;

import android.content.Context;

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
}
