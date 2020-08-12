package com.example.securegatemeet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//Splash Screen internet connection checking
public class Connection {
    ConnectivityManager connectivityManager;
    NetworkInfo info;

    public boolean checkConnection(Context context){
        boolean flag = false;
        try{
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = connectivityManager.getActiveNetworkInfo();

            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                System.out.println(info.getTypeName());
                flag = true;
            }

            if (info.getType() == ConnectivityManager.TYPE_MOBILE){
                System.out.println(info.getTypeName());
                flag = true;
            }
        } catch (Exception e){
            System.out.println("Check Your Internet connection"+e);
        }
        return flag;
    }
}
