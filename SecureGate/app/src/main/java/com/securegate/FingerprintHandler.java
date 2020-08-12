package com.securegate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public  FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager,
                          FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal,
                0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Auth Error..." + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed...", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error : "+ helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("You can now Access the App...", true);
        ((Activity)context).startActivity(new Intent(context, DashboardActivity.class));
        ((Activity)context).finish();
    }

    private void update(String s, boolean b){

        TextView txtMsg = ((Activity)context).findViewById(R.id.txtMsg);

        txtMsg.setText(s);
        if (b == false){
            txtMsg.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }else {
            txtMsg.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }
}