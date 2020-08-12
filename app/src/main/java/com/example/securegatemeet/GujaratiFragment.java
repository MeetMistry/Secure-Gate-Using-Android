package com.example.securegatemeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class GujaratiFragment extends Fragment {

    private Button btnGujaratSamachar, btnSandesh, btnDivyaBhaskar, btnGujaratMitra, btnGujaratGardian;

    public GujaratiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gujarati, container, false);

        btnGujaratSamachar = view.findViewById(R.id.gujaratSamacharButton);
        btnSandesh = view.findViewById(R.id.sandeshButton);
        btnDivyaBhaskar = view.findViewById(R.id.divyaBhaskarButton);
        btnGujaratMitra = view.findViewById(R.id.gujaratmitraButton);
        btnGujaratGardian = view.findViewById(R.id.gujaratgardianButton);

        btnGujaratSamachar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("http://epapergujaratsamachar.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnSandesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://sandeshepaper.in/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnDivyaBhaskar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://epaper.divyabhaskar.co.in/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnGujaratMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://epaper.gujaratmitra.in/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnGujaratGardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.gujaratguardian.in/Home");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        return view;
    }
}
