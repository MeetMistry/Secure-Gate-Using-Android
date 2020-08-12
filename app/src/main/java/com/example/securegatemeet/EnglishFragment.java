package com.example.securegatemeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EnglishFragment extends Fragment {

    private Button btnTimeOfIndia, btnIndianExpress, btnTheHindu, btnHindustanTimes;

    public EnglishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english, container, false);

        btnTimeOfIndia = view.findViewById(R.id.thetimesofindiaButton);
        btnIndianExpress = view.findViewById(R.id.theindianexpressButton);
        btnTheHindu = view.findViewById(R.id.thehinduButton);
        btnHindustanTimes = view.findViewById(R.id.hindustantimesButton);

        btnTimeOfIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://timesofindia.indiatimes.com/news/2");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnIndianExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://indianexpress.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnTheHindu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.thehindu.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnHindustanTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.hindustantimes.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return view;
    }
}
