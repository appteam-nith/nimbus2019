package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nith.appteam.nimbus.Adapter.CoreTeamActivityAdapter;
import com.nith.appteam.nimbus.Adapter.TeamsNew;
import com.nith.appteam.nimbus.Model.CoreTeam;
import com.nith.appteam.nimbus.Model.CoreTeamResponse;
import com.nith.appteam.nimbus.Model.DepartmentalClubs;
import com.nith.appteam.nimbus.Model.TeamListResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.ShadowTransformer;
import com.nith.appteam.nimbus.Adapter.TeamFragmentPagerAdapter;
import com.nith.appteam.nimbus.Model.TeamItem;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamActivity extends AppCompatActivity {
    private ViewPager viewPager, viewPager2;
    private TeamFragmentPagerAdapter adapter;
    private CoreTeamActivityAdapter adapter2;
    private ProgressBar progressBar;
    private TextView message, textcore, textdept;
    private TeamsNew teamsNew;
    private RecyclerView recyclerView;
    private ArrayList<DepartmentalClubs>list;
    private SharedPref sharedPref;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        initCollapsingToolbar();
        Bundle bundle = getIntent().getExtras();
        String valid = bundle.getString("check");
        if (valid.equals("Departmental Teams"))
        {
            url = "https://nimbus2019.herokuapp.com/departmentstudents";
        }
        else
        {
            url = "https://nimbus2019.herokuapp.com/corestudents";

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Teams");
        list = new ArrayList<>();
        sharedPref = new SharedPref(this);
        viewPager = findViewById(R.id.pager);
//        YoYo.with(Techniques.Bounce)
//                .duration(5000)
//                .repeat(0)
//                .playOn(findViewById(R.id.backdrop));
        YoYo.with(Techniques.RollIn)
                .duration(1000)
                .repeat(0)
                .playOn(findViewById(R.id.teamstitle));
        viewPager2 =  findViewById(R.id.pager2);
        progressBar =  findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.VISIBLE);
        message =  findViewById(R.id.message_textView);
        textcore =  findViewById(R.id.textcoreteam);
        textdept =  findViewById(R.id.textdeptteam);
        viewPager.setPageMargin(5);
        teamsNew = new TeamsNew(list,TeamActivity.this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(TeamActivity.this,2));
        recyclerView.setAdapter(teamsNew);
        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Toast.makeText(TeamActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String logo = jsonObject.getString("logo");
                        String id = jsonObject.getString("_id");
                            String info = jsonObject.getString("desc");
                        list.add(new DepartmentalClubs(id,name,logo,info));
                        teamsNew.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                Toast.makeText(TeamActivity.this,list.get(0).getNameClub()+list.get(1).getNameClub(),Toast.LENGTH_SHORT).show();
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("token", sharedPref.getHashedValue());
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
        teamsNew.notifyDataSetChanged();
    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Teams");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


//    private void getAllTeamList() {
//        progressBar.setVisibility(View.VISIBLE);
//        if (new Connection(this).isInternet()) {
//            Call<TeamListResponse> call = Util.getRetrofitService().getAllTeam();
//            call.enqueue(new Callback<TeamListResponse>() {
//                @Override
//                public void onResponse(Call<TeamListResponse> call, Response<TeamListResponse> response) {
//                    if (response != null && response.isSuccess()) {
//                        ArrayList<TeamItem> list = response.body().getTeamList();
//                        adapter = new TeamFragmentPagerAdapter(getSupportFragmentManager(), 2f, list);
//                        viewPager.setAdapter(adapter);
//                        ShadowTransformer shadowTransformer = new ShadowTransformer(viewPager, adapter);
//                        viewPager.setPageTransformer(false, shadowTransformer);
//                        viewPager.setOffscreenPageLimit(3);
//
//                        progressBar.setVisibility(View.GONE);
//                        viewPager.setVisibility(View.VISIBLE);
//                        message.setVisibility(View.GONE);
//                        textdept.setVisibility(View.VISIBLE);
//                    } else {
//                        message.setVisibility(View.VISIBLE);
//                        message.setText("Please Check Your Internet Connection");
//                        progressBar.setVisibility(View.GONE);
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<TeamListResponse> call, Throwable t) {
//                    t.printStackTrace();
//                    message.setVisibility(View.VISIBLE);
//                    message.setText("Please Check Your Internet Connection");
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
//        } else {
//            message.setVisibility(View.VISIBLE);
//            message.setText("Please Check Your Internet Connection");
//            progressBar.setVisibility(View.GONE);
//        }
//    }

//    private void getAllCoreTeamList() {
//        if (new Connection(this).isInternet()) {
//            Call<CoreTeamResponse> call = Util.getRetrofitService().getAllCoreTeam();
//            call.enqueue(new Callback<CoreTeamResponse>() {
//                @Override
//                public void onResponse(Call<CoreTeamResponse> call, Response<CoreTeamResponse> response) {
//                    if (response != null && response.isSuccess()) {
//                        ArrayList<CoreTeam> list = response.body().getCoreTeams();
//                        Log.e("TteamACtivity", "CoreTeamName:" + list.get(0).getLogo());
//
//                        int spacingInPixels = 40;
//                        teamsNew.setItemClickListener(new TeamsNew.ItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//                                Intent i = new Intent(TeamActivity.this,Events.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("clubname", String.valueOf(position));
//                                i.putExtras(bundle);
//                                startActivity(i);
//                            }
//                        });
////                        recyclerView.addItemDecoration(new SpaceItemDecoration(16,16,16,24));
//
////                        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
//                        recyclerView.setAdapter(teamsNew);
////                        adapter2 = new CoreTeamActivityAdapter(getSupportFragmentManager(), 2f, list);
////                        viewPager2.setAdapter(adapter2);
////                        ShadowTransformer shadowTransformer2 = new ShadowTransformer(viewPager2, adapter2);
////                        viewPager2.setPageTransformer(false, shadowTransformer2);
////                        viewPager2.setOffscreenPageLimit(3);
////                        viewPager2.setVisibility(View.VISIBLE);
////                        message.setVisibility(View.GONE);
////                        textcore.setVisibility(View.VISIBLE)
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<CoreTeamResponse> call, Throwable t) {
//                    t.printStackTrace();
//
//                }
//            });
//        }
//    }
}
