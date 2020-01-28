package com.nith.appteam.nimbus.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JoinTeam extends AppCompatActivity
{   private TextView joinBtn;
    private EditText editText;
    private SharedPref sharedPref;
    private String eventid,teamid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        joinBtn=findViewById(R.id.join_button);
        editText = findViewById(R.id.password1);
        sharedPref = new SharedPref(this);
        eventid = getIntent().getStringExtra("eventId");
        teamid = getIntent().getStringExtra("teamId");
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(JoinTeam.this,"Please enter the password",Toast.LENGTH_SHORT).show();
                    return;
                }
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("eventId",eventid);
                    jsonObject.put("teamId",teamid);
                    jsonObject.put("password",editText.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonObject.toString();
                RequestQueue queue = Volley.newRequestQueue(JoinTeam.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/events/jointeam", new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(JoinTeam.this,response,Toast.LENGTH_LONG).show();
                        if(response.equals("OK")){
                            Toast.makeText(JoinTeam.this,"Success",Toast.LENGTH_LONG).show();
                            onBackPressed();
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