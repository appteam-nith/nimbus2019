package com.nith.appteam.nimbus.Activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nith.appteam.nimbus.Adapter.SponsorAdapter;
import com.nith.appteam.nimbus.Model.SponsorResponse;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SponsorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
     private SponsorAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_try);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView= (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter=new SponsorAdapter(this);
        recyclerView.setAdapter(adapter);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        getData();
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
                if (scrollRange + verticalOffset == 0)
                {
                    collapsingToolbar.setTitle("Sponsors");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void getData(){
        Call<SponsorResponse> call= Util.getRetrofitService().getSponsorList();

        call.enqueue(new Callback<SponsorResponse>() {
            @Override
            public void onResponse(Call<SponsorResponse> call, Response<SponsorResponse> response) {
                SponsorResponse sponsorResponse=response.body();

                if(sponsorResponse!=null&&response.isSuccess()){
                    adapter.refresh(sponsorResponse.getSponsors());
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    if(sponsorResponse.getSponsors().size()==0){
                        findViewById(R.id.coming_soon).setVisibility(View.VISIBLE);
                    }else{
                        findViewById(R.id.coming_soon).setVisibility(View.GONE);
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SponsorResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
