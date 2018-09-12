package com.vijay.countrynews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by vijayganeshkumar on 02/09/18.
 */

public class AppUtils {

    static final String TAG = AppUtils.class.getSimpleName();

    public static boolean hasDataConnectivity(Context context) {
        boolean bIsConnected = false;
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager != null)
            {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                bIsConnected = (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
            }
        }
        catch (Exception ex) {
            bIsConnected = false;
            Log.d(TAG,"########## Caught Exception #########" );
            Log.d(TAG,"########## Exception Reason :"+ex.toString());
        }
        return bIsConnected;
    }

}
