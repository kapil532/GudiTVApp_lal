package com.tv.guditvapp.utils;

import android.util.Log;

public class GudiLogs {

    private static String gudiLog = "GudiLogs_";

    public static void logDebug(String tag, String log_value)
    {
        if (MODE.DEBUG)
            Log.d(gudiLog, tag + ">> " + log_value);
    }

    public static void logError(String tag, String log_value) {
        if (MODE.DEBUG)
            Log.e(gudiLog, tag + ">> " + log_value);
    }

    public static void logError(String tag, String log_value, Throwable throwable) {
        if (MODE.DEBUG)
            Log.e(gudiLog, tag + ">> " + log_value, throwable);
    }

    public static void logDebug(String tag, Exception e) {
        if (MODE.DEBUG) {
            String logStr = "Exception -> " + e.getClass().getSimpleName();
            String localMgs = e.getLocalizedMessage();
            if (localMgs != null) {
                logStr += " \n >> localMsg --> " + localMgs;
            }
            String msg = e.getMessage();
            if (msg != null) {
                logStr += "\n msg  --> " + msg;
            }
            Log.d(gudiLog, tag + ">> " + logStr);
        }
    }

    public static class MODE {
        public static boolean DEBUG = true; // app is in debug mode
    }
}
