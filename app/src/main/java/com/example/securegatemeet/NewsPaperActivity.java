package com.example.securegatemeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class NewsPaperActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Spinner languageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_paper);

        languageSpinner = (Spinner)findViewById(R.id.languageSpinner);
        btnBack = (ImageButton)findViewById(R.id.backButton);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewsPaperActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Languages));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(myAdapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment;
                if (i == 0) {

                    fragment = new GujaratiFragment();
                    loadFragment(fragment);
                } else if (i == 1) {

                    fragment = new HindiFragment();
                    loadFragment(fragment);
                } else if (i == 2) {

                    fragment = new EnglishFragment();
                    loadFragment(fragment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewsPaperActivity.this, ActivityFragment.class));
                finish();
            }
        });
    }

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
