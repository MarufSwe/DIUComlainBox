package com.example.khanmaruf.diucomlainbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
   // private SharedPreferences mPerformences;
    private EditText editTextStudentId, editTextPassword ;
    int b = 0;

    String student_id, student_password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handleSSLHandshake();

        editTextStudentId = (EditText) findViewById(R.id.et_student_id);
        editTextPassword = (EditText) findViewById(R.id.et_student_password);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                student_id = editTextStudentId.getText().toString();
                student_password = editTextPassword.getText().toString();


                String myURL = "https://undrooping-till.000webhostapp.com/diucomplainbox/api/loginUser.php";
                //String myURL = "http://metrocem.pgmaq.com/api/index.php/login";
                //String myURL = "https://undrooping-till.000webhostapp.com/equipement.php";

                Log.d("sd","Run");

                StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response + "  responce "+ response.length(),Toast.LENGTH_SHORT).show();
                        b = response.length();
                        // Toast.makeText(getContext(), "Done it",Toast.LENGTH_SHORT).show();
                         if (b > 5) {
                            Toast.makeText(getApplicationContext(), "Login Success",Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                             saveUserName();
                             getUserName();
                             startActivity(intent);
//                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Fail  ----",Toast.LENGTH_SHORT).show();
                            Log.d("sd",response+ "  "+response.toString());
                        }
                        b = 0 ;

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error data  for check Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.d("sd",error.toString()+ "  ");
                    }
                }) {
                    protected Map<String,String> getParams() {
                        Map<String ,String> parr = new HashMap<String,String>();

                        parr.put("student_id", student_id);
                        parr.put("password", student_password);

                        return parr;
                    }

                };


                AppController.getInstance().addToRequestQueue(stringRequest);



            }
        });
    }


    //for SharedPreferences

    public void saveUserName(){
        SharedPreferences sharedPreferences = getSharedPreferences("student id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("student_id", editTextStudentId.getText().toString());
        editor.putString("password", editTextPassword.getText().toString());
        editor.apply();

        //Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
    }

    public void getUserName(){
        SharedPreferences sharedPreferences = getSharedPreferences("student id", Context.MODE_PRIVATE);

        String id = sharedPreferences.getString("student_id","");
       // Toast.makeText(this, "Id: "+ id, Toast.LENGTH_LONG).show();
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
}
