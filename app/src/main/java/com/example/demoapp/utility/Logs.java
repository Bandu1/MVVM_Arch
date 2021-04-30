package com.example.demoapp.utility;

/**
 * Created by       :ABC
 * Date             : 29/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of Logs
 * Revisions        : 1 - XYZ     19-11-2018
 *			         Change – Add in add()
 *
 *                    2 - PQR     20-11-2018
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class Logs {
    private static String TAG = "DemoApp";
    private static final boolean isLoggable = true;

    public static void INFO(String string) { if (isLoggable) android.util.Log.i(TAG, string); }

    public static void ERROR(String string) { if (isLoggable) android.util.Log.e(TAG, string); }

    public static void DEBUG(String string) { if (isLoggable) android.util.Log.d(TAG, string); }

    public static void VERBOSE(String string) {
        if (isLoggable) android.util.Log.v(TAG, string);
    }

    public static void WARN(String string) {
        if (isLoggable) android.util.Log.w(TAG, string);
    }

}
