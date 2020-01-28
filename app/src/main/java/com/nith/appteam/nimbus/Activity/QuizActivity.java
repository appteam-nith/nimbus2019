package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nith.appteam.nimbus.Adapter.QuizAllAdapter;
import com.nith.appteam.nimbus.Model.QuizAllModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuizActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button leaderboard,instructions_button;
    private SharedPref pref;
    List<QuizAllModel> quizAllModels;
    private TextView enter_quiz,enter_leader;
    private CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref pref= new SharedPref(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_home);
//        YoYo.with(Techniques.Bounce)
//                .duration(5000)
//                .repeat(0)
//                .playOn(findViewById(R.id.backdrop));
        YoYo.with(Techniques.RollIn)
                .duration(1000)
                .repeat(0)
                .playOn(findViewById(R.id.title));
        recyclerView = findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progress);
        final SharedPref sp=new SharedPref(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        quizAllModels = new ArrayList<>();
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.core_view);
        initCollapsingToolbar();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();
    }
    public void getData()
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.base_url)+"/quiz" ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonArray)
                    {
                        try {
                            JSONArray jsonArray1 = new JSONArray(jsonArray);
                            for (int i = 0;i<jsonArray1.length() ; i++)
                            {
                                JSONObject jsonObject = (JSONObject) jsonArray1.get(i);
                                String name = jsonObject.getString("quizName");
                                String id = jsonObject.getString("_id");
                                String organisedBy = jsonObject.getString("organizedBy");
                                int time = jsonObject.getInt("timeLimit");
                                quizAllModels.add(new QuizAllModel(id,name,organisedBy,time));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        QuizAllAdapter quizAllAdapter = new QuizAllAdapter(quizAllModels,QuizActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(QuizActivity.this));
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(quizAllAdapter);
                    quizAllAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                    }
                });
        requestQueue.add(request);
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
                    collapsingToolbar.setTitle("Quiz");
                    isShow = true;
                } else if (isShow)
                {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}
