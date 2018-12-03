package com.example.khanmaruf.diucomlainbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class DemandFragment extends Fragment implements AdapterView.OnItemSelectedListener {


   // ImageView ivImage;
    Button cameraButton, submitButton;
    EditText etPlace, etYourDemand, etDetail;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Spinner campusD, buildingD, spinner, spinner2;

    int b = 0;

    String   campus, building,	place, yourDemand, detail, student_student_id ;


    public DemandFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_demand, container, false);


        handleSSLHandshake();


        submitButton = (Button) v.findViewById(R.id.submit_demand);
        etPlace = (EditText) v.findViewById(R.id.etPlaceDemand);
        etYourDemand = (EditText) v.findViewById(R.id.etYourDemand);
        etDetail = (EditText) v.findViewById(R.id.etDetailsDemand);

        campusD = (Spinner) v.findViewById(R.id.forCampusD);
        buildingD = (Spinner) v.findViewById(R.id.forBuildingD);


        //for validation

//        spinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
////                if (spinner.getSelectedItem().toString().equalsIgnoreCase("Choose Your Campus")){
////                    //spinner.setError("required place name");
////                    int a=1;
////                   // ((TextView)spinner.getChildAt(0)).setError("choose building");
////                }
//                if (spinner.getSelectedItem().toString().trim().equals("Pick one")) {
//                   // Toast.makeText(CallWs.this, "Error", Toast.LENGTH_SHORT).show();
//                    ((TextView)spinner.getChildAt(0)).setError("choose building");
//                }
//            }
//        });

        etPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etPlace.getText().length()<2){
                    etPlace.setError("required place name");
                }
            }
        });

        etPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etPlace.getText().length()<2){
                    etPlace.setError("required place name");
                }
            }
        });

        etYourDemand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etYourDemand.getText().length()<7){
                    etYourDemand.setError("required Demand");
                }
            }
        });


        etDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etDetail.getText().length()<15){
                    etDetail.setError("required Details");
                }
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                campus = campusD.getSelectedItem().toString();
                building = buildingD.getSelectedItem().toString();
                place = etPlace.getText().toString();
                yourDemand = etYourDemand.getText().toString();
                detail = etDetail.getText().toString();
                student_student_id = getStudentId();


                String myURL = "https://undrooping-till.000webhostapp.com/diucomplainbox/api/demand.php";
                //String myURL = "http://metrocem.pgmaq.com/api/index.php/login";
                //String myURL = "https://undrooping-till.000webhostapp.com/equipement.php";

                Log.d("sd","Run");

                StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getContext(), response + "  responce r"+ response.length(),Toast.LENGTH_SHORT).show();
                        b = response.length();
                        // Toast.makeText(getContext(), "Done it",Toast.LENGTH_SHORT).show();
                       // if ( response.contains("false")== true) {
                        if ( b >= 4) {
                            Toast.makeText(getContext(), "Data base",Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(getContext(),HomeActivity.class);
//                            i.putExtra("user_id" , userName);
//                            startActivity(i);
//                            finish();
                        } else {
                            Toast.makeText(getContext(), "Login Fail  ----",Toast.LENGTH_SHORT).show();
                            Log.d("sd",response+ "  "+response.toString());
                        }
                        b = 0 ;

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Error data  for"+error.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("sd",error.toString()+ "  ");
                    }
                }) {
                    protected Map<String,String> getParams() {
                        Map<String ,String> parr = new HashMap<String,String>();

                        parr.put("campus", campus);
                        parr.put("building", building);
                        parr.put("place", place);
                        parr.put("demand_detail",detail);
                        parr.put("demand_type", yourDemand);
                        parr.put("student_student_id", student_student_id);

                        return parr;
                    }

                };


                AppController.getInstance().addToRequestQueue(stringRequest);

            }
        });


        spinner = v.findViewById(R.id.forCampusD);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Choose_Your_Campus,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<CharSequence> adapter2;
                if(position == 0){
                    adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.Choose_Your_Building,
                            android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);

                } else if(position == 1)  {
                    adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.Choose_Your_Building_main,
                            android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);

                } else if(position == 2)  {
                    adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.Choose_Your_Building_permanent,
                            android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);

                }  else if(position == 3)  {
                    adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.Choose_Your_Building_uttora,
                            android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });





        spinner2 = v.findViewById(R.id.forBuildingD);
        spinner2.setOnItemSelectedListener(this);

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    public String getStudentId(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("student id", Context.MODE_PRIVATE);

        String id = sharedPreferences.getString("student_id","");

        return id;
    }
}
