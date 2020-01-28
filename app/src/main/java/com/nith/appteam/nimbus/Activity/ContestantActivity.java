package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nith.appteam.nimbus.Adapter.TeamsAdapter;
import com.nith.appteam.nimbus.CustomView.EventDialog;
import com.nith.appteam.nimbus.Model.ContestantInfo;
import com.nith.appteam.nimbus.Model.PrizeModel;
import com.nith.appteam.nimbus.Model.TeamsModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

public class ContestantActivity extends AppCompatActivity {
    private String id,eventname;
    private ArrayList<TeamsModel>list;
    private RecyclerView recyclerView;
    private SharedPref sharedPref;
    private TeamsAdapter teamsAdapter;
    private AlphaInAnimationAdapter alphaAdapter;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contestant);
        sharedPref = new SharedPref(this);
        eventname = getIntent().getStringExtra("name");
         id = getIntent().getStringExtra("eventId");
         String desc = getIntent().getStringExtra("desc");
         ArrayList<String>rules = new ArrayList<>();
         rules = getIntent().getStringArrayListExtra("rules");
        String prize = getIntent().getStringExtra("prize");
        ArrayList<PrizeModel>prizeModels1 = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<PrizeModel>>(){}.getType();
        List<PrizeModel> prizeModels = gson.fromJson(prize, type);
        for (PrizeModel model : prizeModels){
            prizeModels1.add(model);
        }
        EventDialog eventDialog=new EventDialog(ContestantActivity.this,desc,rules,prizeModels1);
        eventDialog.show();
         android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
         toolbar.setTitle(eventname + " Teams");
         toolbar.setTitleTextColor(Color.WHITE);
         setSupportActionBar(toolbar);
         button = findViewById(R.id.fab);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(ContestantActivity.this,CreateTeam.class);
                 intent.putExtra("eventId",id);
                 startActivity(intent);
             }
         });
         list = new ArrayList<>();
         recyclerView = findViewById(R.id.recycler_view);
        teamsAdapter = new TeamsAdapter(this,list,id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setItemAnimator(new SlideInRightAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);
        alphaAdapter = new AlphaInAnimationAdapter(teamsAdapter);
        alphaAdapter.setDuration(1000);
        recyclerView.setAdapter(alphaAdapter);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,getString(R.string.base_url)+"/events/teamsinfo", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(ContestantActivity.this,response,Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id  = jsonObject.getString("_id");
                        String teamName = jsonObject.getString("teamName");
                        ArrayList<ContestantInfo>list1 = new ArrayList<>();
                        JSONArray jsonArray1 = jsonObject.getJSONArray("members");
                        for(int j=0;j<jsonArray1.length();j++){
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                            String name = jsonObject1.getString("name");
                            String Rollno  = jsonObject1.getString("rollNumber");
                            ContestantInfo contestantInfo = new ContestantInfo(name,Rollno);
                            list1.add(contestantInfo);

                        }
                        list.add(new TeamsModel(id,list1,teamName));

                    }
                    alphaAdapter.notifyDataSetChanged();
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
        alphaAdapter.notifyDataSetChanged();
    }
}
