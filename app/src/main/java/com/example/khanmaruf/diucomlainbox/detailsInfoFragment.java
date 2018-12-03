package com.example.khanmaruf.diucomlainbox;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class detailsInfoFragment extends Fragment {



    String campus, date , status, type, detail, building;
    TextView tvCampus,tvdate,tvStatus,tvType,tvDetail, tvBuilding;


    public detailsInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_details_info, container, false);

        campus = getArguments().getString("campus");
        date = getArguments().getString("date");
        status = getArguments().getString("status");
        type = getArguments().getString("type");
        detail = getArguments().getString("detail");
        building = getArguments().getString("building");



        tvCampus = (TextView) v.findViewById(R.id.tb_campus);
        tvdate = (TextView) v.findViewById(R.id.tb_date);
        tvStatus = (TextView) v.findViewById(R.id.tb_status);
        tvType = (TextView) v.findViewById(R.id.tb_type);
        tvDetail = (TextView) v.findViewById(R.id.tb_detail);
        tvBuilding = (TextView) v.findViewById(R.id.tb_detail);


        tvCampus.setText(campus);
        tvdate.setText(date);
        tvStatus.setText(status);
        tvType.setText(type);
        tvDetail.setText(detail);
        tvBuilding.setText(building);




        return v;
    }
}
