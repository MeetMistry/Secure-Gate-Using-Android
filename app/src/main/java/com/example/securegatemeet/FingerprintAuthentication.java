package com.example.securegatemeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FingerprintAuthentication extends AppCompatActivity {

    private TextView mHeadingLable,mParaLable;
    private ImageView mFingerprintImage;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_authentication);

        mHeadingLable = (TextView)findViewById(R.id.headingLabel);
        mParaLable = (TextView)findViewById(R.id.paraLabel);
        mFingerprintImage = (ImageView)findViewById(R.id.fingerprintImage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {

                mParaLable.setText("Fingerprint Scanner not detected in device");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                mParaLable.setText("Permission not granted to use Fingerprint Scanner");

            } else if (!keyguardManager.isKeyguardSecure()) {

                mParaLable.setText("Add lock to your Phone first");

            } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                mParaLable.setText("You should add atleast 1 fingerprint to use this feature");

            } else {

                mParaLable.setText("Place your Finger on scanner to Access the app");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);

            }
        }
    }
}
