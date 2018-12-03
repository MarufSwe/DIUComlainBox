package com.example.khanmaruf.diucomlainbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class PreviousFragment extends Fragment {

    ListView list;
    String[] Campus ;//= {"Main", "Permanent", "Uttora"};
    String[] Status ;// = {"Done", "Pending", "New"};
    String [] Date  ;// = {"12-07-2018", "02-08-2018", "12-07-2015"};
    String[] id  ;//= {"Main", "Permanent", "Uttora"};
    String[] building  ;// = {"Done", "Pending", "New"};
    String [] type  ;//= {"12-07-2018", "02-08-2018", "12-07-2015"};
    String[] detail  ;//= {"Done", "Pending", "New"};

//    Campus Status Date id building  type detail

    public PreviousFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_previous, container, false);
        list = (ListView)  v.findViewById(R.id.list);

        fetchingData();

//        MakeAdapte objMakeAdapter = new MakeAdapte(getActivity(),Campus,Date,Status);
//        list.setAdapter(objMakeAdapter);


        return v;
    }


    void fetchingData() {

        String myURL = "https://undrooping-till.000webhostapp.com/diucomplainbox/api/getPreviousComplain.php?id="+getStudentId();



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Campus = new String[response.length()];
                Status = new String[response.length()] ;
                Date = new String[response.length()];
                id = new String[response.length()];
                building = new String[response.length()] ;
                type = new String[response.length()];
                detail = new String[response.length()];





                for (int i = 0 ; i < response.length();i++){

                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);

                        Campus [i]  = jsonObject.getString("campus")+" Campus";
                        Status [i] =  jsonObject.getString("status");
                        Date [i]  = jsonObject.getString("date");
                        id [i]  = jsonObject.getString("id");
                        building [i] =  jsonObject.getString("building");
                        type [i]  = jsonObject.getString("type");
                        detail [i]  = jsonObject.getString("detail");


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Exception","so");
                        Toast.makeText(getContext(),"Data Somethink Wrong "+e,Toast.LENGTH_SHORT).show();
                    }
                }

                Toast.makeText(getContext(),"Data  Found  ",Toast.LENGTH_LONG).show();
              //  listViewName.setAdapter(new ArrayAdapter(getContext(),R.layout.listshow,R.id.textViewName ,name));
                MakeAdapte objMakeAdapter = new MakeAdapte(getActivity(),Campus,Date,Status);
                list.setAdapter(objMakeAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("--Volley Lod--",error);
                //textWorning.setText(" Problem Please Check your internet");
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {

                // Intent i=new Intent(getActivity(),detailsInfo_activity.class);
                Log.d("Variable Inatial", "method :::::: "+position1);

                detailsInfoFragment objdetailInfoFragment = new detailsInfoFragment();
                Bundle args = new Bundle();


                args.putString("campus",Campus[position1]);
                args.putString("date",Date[position1]);
                args.putString("status",Status[position1]);
                args.putString("building",building[position1]);
                args.putString("type",type[position1]);
                args.putString("detail",detail[position1]);

                objdetailInfoFragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame,objdetailInfoFragment).commit();
                // fragmentManager.beginTransaction().add(R.id.frame, objdetailInfoFragment).replace(R.id.frame, objdetailInfoFragment).commit();


            }
        });
    }


    class MakeAdapte extends ArrayAdapter<String> {

        Context context;
        String [] date;
        String [] campus;
        String[] status;


        MakeAdapte(Context c ,String [] campus, String [] date, String[] status){
            super(c, R.layout.listshow, R.id.tvCampus,campus);
            this.context = c;
            this.date = date;
            this.campus = campus;
            this.status = status;
        }


        @Override
        public View getView(int position,View convertView,ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.listshow,parent,false);

           // ImageView imageView = (ImageView) row.findViewById(R.id.imageView);
            TextView tvCampus = (TextView) row.findViewById(R.id.tvCampus);
            TextView tvDate = (TextView) row.findViewById(R.id.tvDate);
            TextView tvStatus = (TextView) row.findViewById(R.id.tvStatus);


           // imageView.setImageResource(image1[position]);
            tvDate.setText(date[position]);
            tvCampus.setText(campus[position]);
            tvStatus.setText(status[position]);

            return row;
        }

    }

    public String getStudentId(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("student id", Context.MODE_PRIVATE);

        String id = sharedPreferences.getString("student_id","");

        return id;
    }


}
