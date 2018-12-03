package com.example.khanmaruf.diucomlainbox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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


public class EquipmentFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    ImageView ivImage;
    Button cameraButton, submitButton;
    TextView tvPlace, tvEquipment;
    EditText tvDetail;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Spinner campusE, buildingE, spinner, spinner2;


    int b = 0;

    String   campus, building,	place, equipmentId , detail, student_student_id , encoded_string ;


    public EquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_equipment, container, false);

        handleSSLHandshake();

        ivImage =(ImageView) v.findViewById(R.id.ivImage);
        cameraButton = (Button) v.findViewById(R.id.cameraButton);
        submitButton = (Button) v.findViewById(R.id.submit_equipment);
        tvPlace = (TextView) v.findViewById(R.id.tbPlaceEq);
        tvEquipment = (TextView) v.findViewById(R.id.tbEquipmentId);
        tvDetail = (EditText) v.findViewById(R.id.editTextEquipmentDetail);

        campusE = (Spinner) v.findViewById(R.id.spinnerCampusE);
        buildingE = (Spinner) v.findViewById(R.id.spinnerBuildingE);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });


        //for validation

        tvPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (tvPlace.getText().length()<2){
                    tvPlace.setError("required place name");
                }
            }
        });


        tvPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (tvPlace.getText().length()<3){
                    tvPlace.setError("required place name");
                }
            }
        });


        tvEquipment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (tvEquipment.getText().length()<5){
                    tvEquipment.setError("required Equipment ID");
                }
            }
        });


        tvDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (tvDetail.getText().length()<15){
                    tvDetail.setError("required Detaill");
                }
            }
        });



        // Submit button

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                campus = campusE.getSelectedItem().toString();
                building = buildingE.getSelectedItem().toString();
                place = tvPlace.getText().toString();
                equipmentId = tvEquipment.getText().toString();
                detail = tvDetail.getText().toString();
                student_student_id = getStudentId();




                String myURL = "https://undrooping-till.000webhostapp.com/diucomplainbox/api/equipement.php";
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
                            Toast.makeText(getContext(), "Data Pass",Toast.LENGTH_SHORT).show();
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
                        parr.put("detail",detail);
                        parr.put("equipmentID", equipmentId);
                        parr.put("image", encoded_string);
                        parr.put("student_student_id", student_student_id);

                        return parr;
                    }
                };


                AppController.getInstance().addToRequestQueue(stringRequest);
            }
        });


        // SelectImage();h

        spinner = v.findViewById(R.id.spinnerCampusE);
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



        spinner2 = v.findViewById(R.id.spinnerBuildingE);
        spinner2.setOnItemSelectedListener(this);

        return v;

    }

    public void setSpinner2(Button cameraButton) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        switch (adapterView.getId()){
//            case R.id.:
//                //Do something
//                Toast.makeText(this, "Alarm Selected: " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.sp_optionSelection:
//                //Do another thing
//                Toast.makeText(this, "Option Selected: " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                break;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //for camera

    private void SelectImage(){

        final  CharSequence[] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                }else if (items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                }else if (items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
                //    Log.d("Get Out ", "1");
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);

        //  Log.d("--------", "1");
        if (resultCode == Activity.RESULT_OK){
            //  Log.d("Eesult ok", "1");
            if (requestCode == REQUEST_CAMERA){

                Log.d("Request Camera", "1");
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                ivImage.setImageBitmap(bmp);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bmp);


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] array = stream.toByteArray();
                encoded_string = Base64.encodeToString(array,0);


            }else if (requestCode == SELECT_FILE){
                Log.d("--Save FIle----", "1");
                Uri selectImageUri  = data.getData();
                ivImage.setImageURI(selectImageUri);

                Bitmap bmp = null;
                try {
                    bmp =  MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), selectImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] array = stream.toByteArray();
                encoded_string = Base64.encodeToString(array,0);
                //  cameraButton.(selectImageUri);

//
//               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectImageUri);
//
//                Drawable d = new BitmapDrawable(getResources(),bitmap);
//                cameraButton.setBackgroundDrawable(d);

            }
        }
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



