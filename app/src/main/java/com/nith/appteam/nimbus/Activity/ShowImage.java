package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jsibbold.zoomage.ZoomageView;
import com.nith.appteam.nimbus.Adapter.ImageAdapter;
import com.nith.appteam.nimbus.Adapter.NewGalleryAdapter;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowImage extends AppCompatActivity {
    ArrayList<String> image;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    ZoomageView zoomageView;
    int zoom = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Images");
        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setBackground(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        Bundle bundle = getIntent().getExtras();
        String year = bundle.getString("year");
        zoomageView = findViewById(R.id.myZoomageView);
        relativeLayout = findViewById(R.id.relativeLayout);
        image = new ArrayList<>();
        image = bundle.getStringArrayList("image");
//        Toast.makeText(ShowImage.this,image.toString(),Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recycler_view);
        final ImageAdapter imageAdapter = new ImageAdapter(image,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setItemClickListener(new ImageAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                relativeLayout.setVisibility(View.VISIBLE);
                String image1 = image.get(position);
                Picasso.get().load(image1).into(zoomageView);
                zoom = 1;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (zoom == 1)
        {
            zoom = 0;
            relativeLayout.setVisibility(View.GONE);
        }
        else
        super.onBackPressed();
    }
}
