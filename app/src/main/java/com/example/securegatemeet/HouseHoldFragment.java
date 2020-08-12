package com.example.securegatemeet;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HouseHoldFragment extends Fragment {

    private static final int REQUEST_READ_CONTACTS = 79 ;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private TextView userTextView, phnTextView;
    private Button btnAddGuest;
    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    Model m = new Model();
    private static ArrayList<Model> models = new ArrayList<>();
//    ArrayList<Model> temp = new ArrayList<>();
    private TextView addTextView,titleTxtView,descriptionTxtView;
    private static final int PICK_CONTACT = 1;
    private DatabaseReference guestReference;
    ArrayList guestList = new ArrayList();


    View view;


    public HouseHoldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_house_hold, container, false);

        userTextView = view.findViewById(R.id.userTextView);
        btnAddGuest = view.findViewById(R.id.addGustButton);
//        phnTextView = view.findViewById(R.id.phnTextView);

//        createRecylerView("","");

//        addTextView = (TextView)view.findViewById(R.id.addGuest);

        titleTxtView = view.findViewById(R.id.titleTv);
        descriptionTxtView = view.findViewById(R.id.descriptionTv);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        guestReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Guest");
//        Toast.makeText(view.getContext(),"User : "+models,Toast.LENGTH_LONG).show();
//        addGuests();

        reference = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        guestReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Guest");

        createRecylerView("Aman","9876543210");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("firstName").getValue().toString();
                String lastname = dataSnapshot.child("lastName").getValue().toString();
                userTextView.setText(firstname + " " + lastname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Anything want to do manually
            }
        });


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            clickOnButton();

        } else {
            requestPermissions();
        }





        return view;
    }

    private void createRecylerView(String title, String des) {

//        Toast.makeText(view.getContext(),title+" "+des, Toast.LENGTH_LONG).show();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        Log.i("TEST : ","HELLO : "+viewGuests());
        myAdapter = new MyAdapter(view.getContext(), viewGuests());
        mRecyclerView.setAdapter(myAdapter);

    }

    private void addGuests(String name, String num){
        reference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Guest");
        reference.child(name).setValue(num);

    }

    private ArrayList<Model> viewGuests(){
        guestReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Guest");

        guestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                guestList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    guestList.add(key);
                    Toast.makeText(view.getContext(),"Key : "+guestList,Toast.LENGTH_LONG).show();
                    Log.i("GUESTS : ",""+models);
                    getMyList(key,ds.getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.i("END...","END RETURN...");
        return models;
    }

    private ArrayList<Model> getMyList(String title, String des) {

        if (title.isEmpty() || des.isEmpty()){
            return models;
        }


        m = new Model();
        m.setTitle(title);
        m.setDescription(des);
        m.setImg(R.drawable.meet1);
        models.add(m);

        return models;

    }



    private void requestPermissions() {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.READ_CONTACTS)) {
            clickOnButton();
        } else {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.READ_CONTACTS)){
        } else {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();

                    Cursor c = getContext().getContentResolver().query(contactData, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        String name = "";

                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                                    .CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                            while (numbers.moveToNext()) {
                                name = numbers.getString(numbers.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                                titleTxtView.setText(name);
//                                descriptionTxtView.setText(num);
                                addGuests(name,num);

                            }
                            createRecylerView(name,num);
                        }
                    }
                }
        }
    }



    public void clickOnButton() {

        btnAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
    }
}
