package com.nith.appteam.nimbus.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.annotations.SerializedName;
import com.nith.appteam.nimbus.Adapter.EventsListAdapter;
import com.nith.appteam.nimbus.Adapter.LeaderBoardAdapter;
import com.nith.appteam.nimbus.Adapter.LeaderBoardAdapterNew;
import com.nith.appteam.nimbus.Model.LeaderBoardModel;
import com.nith.appteam.nimbus.Model.LeaderBoardModelNew;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoardActivity extends AppCompatActivity {
    List<GalleryNew> gallery;
    String quizId = "NULL";
    List<Integer> scoreList ;
    List <LeaderBoardModelNew> nameList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<LeaderBoardUserModel> users;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Bundle bundle = getIntent().getExtras();
        quizId = bundle.getString("quizId");
        jsonObject = new JSONObject();
        nameList = new ArrayList<>();
        scoreList = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.leader_progress);
        recyclerView = (RecyclerView) findViewById(R.id.leader_recycler);
        toolbar = (Toolbar) findViewById(R.id.leader_toolbar);
        toolbar.setTitle("LeaderBoard");
        toolbar.setTitleTextColor(Color.WHITE);
        if (quizId.equals("NULL"))
            quizId = "5c7ec33efb6fc072012e8aef";
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            jsonObject.put("quizId",quizId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postData();
    }
    private void postData() {
        final String requestBody = jsonObject.toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/quiz/leaderboard", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length() ; i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String name = jsonObject.getString("name");
                        int score = jsonObject.getInt("score");
                        String id = jsonObject.getString("_id");
                        String rollno = jsonObject.getString("rollNumber");
                        String encoded = jsonObject.getString("profilePicture");
                        Log.v("name123", String.valueOf(jsonObject));
                        nameList.add(new LeaderBoardModelNew(name, ""+ String.valueOf(score), id, rollno,encoded));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                Collections.sort(nameList,new CustomComparator());
                Collections.reverse(nameList);
                LeaderBoardAdapterNew leaderBoardAdapterNew = new LeaderBoardAdapterNew(nameList,LeaderBoardActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));
                recyclerView.setAdapter(leaderBoardAdapterNew);
                leaderBoardAdapterNew.notifyDataSetChanged();
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
//                headers.put("token", );
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
            protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
    public class CustomComparator implements Comparator<LeaderBoardModelNew> {
        @Override
        public int compare(LeaderBoardModelNew o1, LeaderBoardModelNew o2) {
            return o1.getScore().compareTo(o2.getScore());
        }
    }
    public void getLeaderBoard() {
        ApiInterface mAPI = Util.getRetrofitService();
        Call<LeaderBoardModel> mService = mAPI.getLeaderBoard();
        mService.enqueue(new Callback<LeaderBoardModel>() {
            @Override
            public void onResponse(Call<LeaderBoardModel> call, Response<LeaderBoardModel> response) {
                if(response!=null&&response.isSuccess()){
                    users = response.body().getUsers();
                    if(users.size()>0)
                        adapter = new LeaderBoardAdapter(users, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<LeaderBoardModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }


    public class LeaderBoardUserModel {

        @SerializedName("name")
        private String name;

        @SerializedName("photo")
        private String photo;

        @SerializedName("roll_no")
        private String rollNo;

        @SerializedName("quiz")
        private Quiz sets;

        public LeaderBoardUserModel(String name, String photo, String rollNo, Quiz sets) {
            this.photo=photo;
            this.name = name;
            this.rollNo = rollNo;
            this.sets = sets;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRollNo() {
            return rollNo;
        }

        public void setRollNo(String rollNo) {
            this.rollNo = rollNo;
        }

        public Quiz getSets() {
            return sets;
        }

        public void setSets(Quiz sets) {
            this.sets = sets;
        }

    }

    public class Quiz{

        @SerializedName("score")
        private int score;
        @SerializedName("sets")
        private int sets;
        public Quiz(int score, int sets) {
            this.score = score;
            this.sets = sets;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSets() {
            return sets;
        }

        public void setSets(int sets) {
            this.sets = sets;
        }
    }
}
