package com.example.securegatemeet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoardActivity extends AppCompatActivity {

    private ImageButton btnSetting;
    private TextView wingTextView;
    private BottomNavigationView bottomNavigationView;
    private ExtendedFloatingActionButton btnQuickAction;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;

    public static Context contextOfApplication;
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        loadFragment(new ActivityFragment());

        contextOfApplication = getApplicationContext();

        auth =FirebaseAuth.getInstance();
        user =auth.getCurrentUser();

        btnSetting = (ImageButton)findViewById(R.id.settingButton);
        wingTextView = (TextView)findViewById(R.id.wingTextView);


        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoardActivity.this, SettingActivity.class));
//                finish();
            }
        });

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        reference = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wingName = dataSnapshot.child("wing").getValue().toString();
                String flatNo = dataSnapshot.child("flatNo").getValue().toString();
                wingTextView.setText("Wing " + wingName + " " + flatNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.activity:
                    fragment = new ActivityFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.houseHold:
                    fragment = new HouseHoldFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.community:
                    fragment = new CommunityFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
