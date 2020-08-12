package com.example.securegatemeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ActivityFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btnNewsPaper, btnZomato, btnRestaurant;

    public ActivityFragment() {
        // Required empty public constructor
    }

    public static ActivityFragment newInstance(String param1, String param2) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_activity, container, false);

         btnNewsPaper = view.findViewById(R.id.newspaperButton);
         btnZomato = view.findViewById(R.id.zomatoButton);
         btnRestaurant = view.findViewById(R.id.restaurantButton);

         btnZomato.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Uri uri = Uri.parse("https://www.zomato.com");
                 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                 startActivity(intent);
             }
         });

         btnRestaurant.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Uri uri = Uri.parse("https://www.dineout.co.in");
                 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                 startActivity(intent);
             }
         });

         btnNewsPaper.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getActivity(), NewsPaperActivity.class));
             }
         });

        return view;
    }
}
