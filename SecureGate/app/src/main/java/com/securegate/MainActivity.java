package com.securegate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Splash splash = new Splash();
        Thread thread = new Thread(splash);

        thread.start();


    }

    class CheckInternetConnection extends Thread{

        @Override
        public void run() {
            while (true){
                boolean checkConnection=new ApplicationUtility().checkConnection(MainActivity.this);
                if (!checkConnection){
//                    Toast.makeText(MainActivity.this,"Check your connection...",Toast.LENGTH_LONG).show();
                    onDestroy();
                    Log.d("Internet : ","Check your connection...");
                }
            }
        }
    }

    class Splash extends TimerTask {

        @Override
        public void run() {

            Timer t = new Timer();
            boolean checkConnection=new ApplicationUtility().checkConnection(MainActivity.this);
            if (checkConnection) {

                CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
                checkInternetConnection.start();


                t.schedule(new TimerTask() {
                    @Override
                    public void run() {


                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
//            finish();
                        startActivity(i);
                        finish();
                    }
                }, 3000);
            } else {
                Toast.makeText(MainActivity.this,
                        "connection not found...plz check connection", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}