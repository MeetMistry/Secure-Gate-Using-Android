package com.securegate;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerprintSignin extends AppCompatActivity {

    TextView txt,txtMsg;
    ImageView img;
    private FingerprintManager fingerprintManager;

    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_signin);

        txt = findViewById(R.id.txt);
        txtMsg = findViewById(R.id.txtMsg);
        img = findViewById(R.id.img);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            if (!fingerprintManager.isHardwareDetected()){

                txtMsg.setText("Fingerprint Scanner not detected...");
            }else if (ContextCompat
                    .checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                    != PackageManager.PERMISSION_GRANTED){

                txtMsg.setText("Permission not granted to use Fingerprint Scanner...");
            }else if (!fingerprintManager.hasEnrolledFingerprints()){

                txtMsg.setText("No Fingerprints found...");
            }else {

                txtMsg.setText("Place your finger...");

                generateKey();


                    FingerprintManager.CryptoObject cryptoObject;
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey(){

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                    KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setUserAuthenticationRequired(true)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build());

        }catch (KeyStoreException | IOException | CertificateException |
                NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                NoSuchProviderException e){

            e.printStackTrace();

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException |
                IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

    }
}
