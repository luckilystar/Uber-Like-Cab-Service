package com.nearcabs.userapp;

//import android.support.v7.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

//import com.bumptech.glide.Glide;

public class UserEditActivity extends AppCompatActivity {

    TextView txtName, txtPhone, txtEmail, txtPassword;
    String user_id;
    Button btnEdit;
    String PREFS_NAME = "auth_info";

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        txtName = (TextView)findViewById(R.id.txtName);
        txtPhone = (TextView)findViewById(R.id.txtPhone);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtPassword = (TextView)findViewById(R.id.txtPassword);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });
        try {
            String api_url = getString(R.string.server_url) + "/edit_profile_user.php";

            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            String user_id = sharedPreferences.getString("id", null);

            String get_user_request = "user_id=" + URLEncoder.encode(user_id, "UTF-8");

            JSONObject response_data = call_api(api_url, get_user_request);
            if (response_data.getString("status").equals("1")) {

                JSONObject user_response_data = response_data.getJSONObject("data");

                txtName.setText(user_response_data.getString("user_name"));
                txtPhone.setText(user_response_data.getString("phone"));
                txtEmail.setText(user_response_data.getString("email"));
                txtPassword.setText(user_response_data.getString("password"));
            } else {
                Toast.makeText(getApplicationContext(), "No user nearby", Toast.LENGTH_LONG).show();
            }
            requestQueue = Volley.newRequestQueue(UserEditActivity.this);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public JSONObject call_api(String api_url, String request_data) {
        try {
            URL url = new URL(api_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(request_data);
            writer.flush();
            writer.close();
            os.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String response = "";
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }

            Log.d("API response", response);

            JSONObject response_data = new JSONObject(response);
            return response_data;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        return null;
    }

    public void InsertData() {

        try {
            setContentView(R.layout.activity_edit_user);

            txtName = (TextView)findViewById(R.id.txtName);
            txtPhone = (TextView)findViewById(R.id.txtPhone);
            txtEmail = (TextView)findViewById(R.id.txtEmail);
            txtPassword = (TextView)findViewById(R.id.txtPassword);
            
            URL url = new URL(getString(R.string.server_url) + "update_user.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            String inputName = txtName.getText().toString();
            String inputPhone = txtPhone.getText().toString();
            String inputEmail = txtEmail.getText().toString();
            String inputPassword = txtPassword.getText().toString();

            String data = "name=" + URLEncoder.encode(inputName, "UTF-8") + "&phone=" + URLEncoder.encode(inputPhone, "UTF-8") + "&email=" + URLEncoder.encode(inputEmail, "UTF-8") + "&password=" + URLEncoder.encode(inputPassword, "UTF-8");
//            data= URLEncoder.encode(data,"UTF-8");
            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String response = "";
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
//            line=bufferedReader.readLine();

            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
//            conn.connect();

            JSONObject response_data = new JSONObject(response);
            if (response_data.getString("status").equals("1")) {
                Toast.makeText(getApplicationContext(), response_data.getString("data").toString(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), response_data.getString("data"), Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }


    }
}