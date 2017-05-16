package com.example.prasanna.trainshadule.Utilities;

/**
 * Created by prasanna on 3/30/17.
 *
     -----------------------------------------------------------------
     MethodName              ActionName              Properties
     -----------------------------------------------------------------
     getDwlaySnippet         getDwlaySnippet         cDate -> currentDate
     cTime -> currentTime
     lang  -> en
     -----------------------------------------------------------------
     getRates                getRates                StartStationCode
     EndStationCode
     -----------------------------------------------------------------
     getSchedule             getSchedule             StartStationCode    -> from station code
     EndStationCode      -> to station code
     ArrivalTime
     DepatureTime
     CurrentDate
     CurrentTime
     lang                -> en
     -----------------------------------------------------------------
     getAllStations/         Same as Method Name     line    -> String value of line ID
     getStations                                     lang    -> en
     -----------------------------------------------------------------
     getLines                getLines                lang -> en
     -----------------------------------------------------------------
 */

public abstract class Constants {
    public static final String NAMESPACE = "http://ws.wso2.org/dataservice";
    public static final String ENDPOINT = "http://103.11.35.13:9080/services/RailwayWebServiceV2Proxy.RailwayWebServiceV2ProxyHttpSoap12Endpoint";

    public static final String TAG = "Train-Shedule";

    //Database Constants
    public static final String DATABASE_NAME = "train_schedule";
    public static final int DATABASE_VERSION = 1;

    //Train Schedule constants
    public static final String MONDAY_TO_SATURDAY = "MONDAY TO SATURDAY";
    public static final String SATURDAY_AND_SUNDAY = "SATURDAY AND SUNDAY";
    public static final String MONDAY_TO_FRIDAY = "MONDAY TO FRIDAY";
    public static final String DAILY = "DAILY";

    //Fragment Tag names
    public static final String FRAGMENT_TRAIN_SCHEDULE = "train_schedule";
    public static final String FRAGMENT_FEEDBACK = "feedback";
    public static final String FRAGMENT_TRAIN_SCHEDULE_VIEW = "train_schedule_view";

    public static final String SERVER_TOKEN = "sd832onsas9d1n23ld2ffwefwefcwfadlsnnio21dNEJNOCFOIEWOFJOIJOQIEWJFQ8156516516!@#@$@#%^$l23n4l23";
    public static final String SERVER_HOST_URL = "http://train-schedule-v10.herokuapp.com/train_schedule_feedback";
    public static final String SERVER_LOCAL_URL = "http://10.0.2.2:8000/train_schedule_feedback";
}
