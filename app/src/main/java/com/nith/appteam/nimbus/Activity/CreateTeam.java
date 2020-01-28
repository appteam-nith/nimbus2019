package com.nith.appteam.nimbus.Activity;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus.Model.EventModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CreateTeam extends AppCompatActivity {

    private TextView createBtn;
    private SharedPref sharedPref;
    private String id;
    private EditText editText,editText1;
    private String name,requestBody;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        sharedPref = new SharedPref(this);
        createBtn =  findViewById(R.id.create_button);
        id = getIntent().getStringExtra("eventId");
        editText = findViewById(R.id.teamName);
        editText1 = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().isEmpty()&&editText1.getText().toString().isEmpty()){
                    Toast.makeText(CreateTeam.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
                    return;
                }
                name = editText.getText().toString();
                createBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("eventId",id);
                    jsonObject.put("teamName",editText.getText().toString());
                    jsonObject.put("password",editText1.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestBody = jsonObject.toString();
                RequestQueue queue = Volley.newRequestQueue(CreateTeam.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/events/newteam", new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            if(name.equals(jsonObject1.getString("teamName")))
                            {
                                Toast.makeText(CreateTeam.this,"Success",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                })
                {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("token", sharedPref.getHashedValue());
                        return headers;
                    }
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        int mStatusCode = response.statusCode;

                        return super.parseNetworkResponse(response);
                    }


                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);
            }
        });
    }

}