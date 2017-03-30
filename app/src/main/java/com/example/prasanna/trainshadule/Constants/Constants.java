package com.example.prasanna.trainshadule.Constants;

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

public class Constants {
    public static final String NAMESPACE = "http://ws.wso2.org/dataservice";
    public static final String ENDPOINT = "http://103.11.35.13:9080/services/RailwayWebServiceV2Proxy.RailwayWebServiceV2ProxyHttpSoap12Endpoint";

    public static final String TAG = "Train-Shedule";
}
