package com.example.securegatemeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Timer t = new Timer();
        boolean checkConnection = new Connection().checkConnection(this);
        if (checkConnection){
            t.schedule(new splash(), 3000);
        } else {
            Toast.makeText(SplashScreen.this, "Please check your Internet conectivity", Toast.LENGTH_LONG).show();
        }
    }

    class splash extends TimerTask {

        @Override
        public void run(){
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            finish();
            startActivity(i);
        }
    }
}
