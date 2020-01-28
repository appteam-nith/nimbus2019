package com.nith.appteam.nimbus.Activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nith.appteam.nimbus.Adapter.NewGalleryAdapter;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryNew extends AppCompatActivity {
    List <GalleryModel> gallery2018;
    RecyclerView recyclerView;
    ArrayList<String>image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_new);
        initCollapsingToolbar();
//        YoYo.with(Techniques.Bounce)
//                .duration(5000)
//                .repeat(0)
//                .playOn(findViewById(R.id.backdrop));
        YoYo.with(Techniques.RollIn)
                .duration(1000)
                .repeat(0)
                .playOn(findViewById(R.id.title));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gallery2018 = new ArrayList<>();
        image = new ArrayList<>();

//        gallery2018.add(new GalleryModel("https://pngimage.net/wp-content/uploads/2018/06/members-png.png","2016"));
//        gallery2018.add(new GalleryModel("https://pngimage.net/wp-content/uploads/2018/06/members-png.png","2017"));
//        gallery2018.add(new GalleryModel("https://pngimage.net/wp-content/uploads/2018/06/members-png.png","2018"));
//        gallery2018.add(new GalleryModel("https://pngimage.net/wp-content/uploads/2018/06/members-png.png","2019"));
        recyclerView = findViewById(R.id.recycler_view);
        final NewGalleryAdapter adapter = new NewGalleryAdapter(gallery2018,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest("https://nimbus2k17api.herokuapp.com/api/v1/galleryAll",null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(GalleryNew.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONArray array = response.getJSONArray("albums");
                    for(int i=0;i<array.length();i++){
                        String title = array.getJSONObject(i).getString("title");
                        String headerImage = array.getJSONObject(i).getString("headerImage");
                        JSONArray images = array.getJSONObject(i).getJSONArray("images");
                        ArrayList<String>imagekaurl = new ArrayList<>();
                        for(int j=0;j<images.length();j++){
                            imagekaurl.add(images.getJSONObject(j).getString("imageUrl"));
                        }
                        gallery2018.add(new GalleryModel(headerImage,title,imagekaurl));
                        adapter.notifyDataSetChanged();
                    }

//                    Toast.makeText(GalleryNew.this, gallery2018.toString(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
        adapter.notifyDataSetChanged();

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
                    collapsingToolbar.setTitle("Gallery");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
