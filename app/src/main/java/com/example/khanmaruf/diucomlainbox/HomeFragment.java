package com.example.khanmaruf.diucomlainbox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class HomeFragment extends Fragment {

    private TextView equipment, harassment, demand;
    private ImageView imageViewEquipment, imageHarassment, imageViewDemand , emmergencyCall;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        imageViewEquipment = v.findViewById(R.id.imageViewEquipment);
        imageHarassment = v.findViewById(R.id.imageViewHarassment);
        imageViewDemand = v.findViewById(R.id.imageViewDemands);

        equipment = v.findViewById(R.id.tbEquipment);
        harassment = v.findViewById(R.id.tbHarassment);
        demand = v.findViewById(R.id.tbDamage);

        emmergencyCall = v.findViewById(R.id.btEmerjency);

        //for equipment text button

        equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EquipmentFragment objFragment = new EquipmentFragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();

            }
        });


        //for equipment image button

        imageViewEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EquipmentFragment objFragment = new EquipmentFragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();

            }
        });


        //for harassment text button

        harassment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                harassmentFragment objFragment = new harassmentFragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();

            }
        });


        //for harassment image button

        imageHarassment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                harassmentFragment objFragment = new harassmentFragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();

            }
        });



        //for demand text button

        demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemandFragment objFragment = new DemandFragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();

            }
        });


        //for demand image button

        imageViewDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemandFragment objFragment = new DemandFragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();

            }
        });


        //for Emmergency call

        emmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = "tel:"+ "01711187538";
                startActivity( new Intent(Intent.ACTION_CALL, Uri.parse(number)));

            }
        });



        //for take photo




        return v;

    }
}