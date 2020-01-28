package com.nith.appteam.nimbus.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.nith.appteam.nimbus.Adapter.EventsListAdapter;
import com.nith.appteam.nimbus.Model.EventModel;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.Model.PrizeModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

public class EventsList extends AppCompatActivity {
    private List<EventModel> eventslist;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private SharedPref sharedPref;
    String id,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getStringExtra("clubid");
        name = getIntent().getStringExtra("clubName");
        getSupportActionBar().setTitle(name + " Events");
        toolbar.setTitleTextColor(Color.WHITE);
        eventslist = new ArrayList<>();
        sharedPref = new SharedPref(this);
        recyclerView = findViewById(R.id.recycler_view);
        EventsListAdapter eventsListAdapter = new EventsListAdapter(eventslist,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setItemAnimator(new SlideInRightAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);
        final AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(eventsListAdapter);
        alphaAdapter.setDuration(1000);
        recyclerView.setAdapter(alphaAdapter);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("clubId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("CLUBBID",id);
        final String requestBody = jsonObject.toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/departments", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.v("Response",response);
//                Toast.makeText(EventsList.this,response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject  =new JSONObject(response);
                    Log.v("jsononjj", String.valueOf(jsonObject));
                    JSONArray jsonArray = jsonObject.getJSONArray("events");
                    Log.v("eventsarray", String.valueOf(jsonArray));
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("_id");
                        String eventName = jsonObject1.getString("name");
                        String organized = jsonObject1.getString("organizedBy");
                        String venue = jsonObject1.getString("venue");
                        String desc = jsonObject1.getString("desc");
                        String date = jsonObject1.getString("date");
                        ArrayList<String> rules = new ArrayList<>();
                        JSONArray rule = jsonObject1.getJSONArray("rules");
                        for(int j=0;j<rule.length();j++){
                            rules.add(rule.getJSONObject(j).getString("rule"));
                        }
                        JSONArray prize = jsonObject1.getJSONArray("prize");
                        ArrayList<PrizeModel>prizeModels = new ArrayList<>();
                        for(int k=0;k<prize.length();k++){
                            prizeModels.add(new PrizeModel(prize.getJSONObject(k).getInt("position"),prize.getJSONObject(k).getString("prize")));
                        }
//                        Toast.makeText(EventsList.this,id+eventName+organized+venue+date+desc+rules.toString()+prize.toString(),Toast.LENGTH_SHORT).show();
                        EventModel eventModel = new EventModel(id,eventName,organized,desc,venue,date,prizeModels,rules,true);
                        eventslist.add(eventModel);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alphaAdapter.notifyDataSetChanged();
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
