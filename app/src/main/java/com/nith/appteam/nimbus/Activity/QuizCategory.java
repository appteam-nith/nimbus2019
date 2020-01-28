package com.nith.appteam.nimbus.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus.Adapter.QuizAdapter;
import com.nith.appteam.nimbus.Model.CategoryQuiz;
import com.nith.appteam.nimbus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizCategory extends AppCompatActivity {
    Activity activity;
    RecyclerView recyclerView;
    List<CategoryQuiz> quizes;
    QuizAdapter quizAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);
        quizes = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        getData();
    }
    public void getData()
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(QuizCategory.this);//
        JsonArrayRequest request = new JsonArrayRequest(getString(R.string.base_url)+"/quiz",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String quizName = jsonObject.getString("quizName");
                                String quizId = jsonObject.getString("quizId");
                                String clubName = jsonObject.getString("clubName");
                                String ImageUrl = jsonObject.getString("imageUrl");
                                quizes.add(new CategoryQuiz(quizName,clubName,quizId,ImageUrl));
                            }
                            catch(JSONException e) {
                            }
                        }
                        quizAdapter = new QuizAdapter(quizes,QuizCategory.this);
                        recyclerView.setLayoutManager(new GridLayoutManager(QuizCategory.this,2));
                        recyclerView.setAdapter(quizAdapter);
                        quizAdapter.notifyDataSetChanged();
//                        pb.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
        requestQueue.add(request);
    }
}
