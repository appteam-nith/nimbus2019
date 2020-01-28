package com.nith.appteam.nimbus.Activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nith.appteam.nimbus.Adapter.contributorsAdapter;
import com.nith.appteam.nimbus.Model.contributorsItem;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

public class ContributorsActivity extends AppCompatActivity {

    RecyclerView rvContributors;
    contributorsAdapter ContributorAdapter;
    Toolbar tbContributers;
    ArrayList<contributorsItem> contributorsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);
        initCollapsingToolbar();
//        YoYo.with(Techniques.Bounce)
//                .duration(5000)
//                .repeat(0)
//                .playOn(findViewById(R.id.backdrop));
        Toolbar toolbar = (Toolbar) findViewById(R.id.contributors_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String BASE_URL = "https://github.com/";
        rvContributors = (RecyclerView) findViewById(R.id.contributors_view);
        contributorsItems = new ArrayList<>();

        //contributorsItems.add(new contributorsItem("", BASE_URL + ".png", BASE_URL + ""));

        //4th Year
        contributorsItems.add(new contributorsItem("Ashish Gurjar", BASE_URL + "akgurjar.png", BASE_URL + "akgurjar"));
        contributorsItems.add(new contributorsItem("Sahil Ramola", BASE_URL + "RamolaWeb.png", BASE_URL + "RamolaWeb"));
        contributorsItems.add(new contributorsItem("Sukhbir Singh", BASE_URL + "sukhbir-singh.png", BASE_URL + "sukhbir-singh"));
        contributorsItems.add(new contributorsItem("Jalaz Kumar", BASE_URL + "jaykay12.png", BASE_URL + "jaykay12"));
        contributorsItems.add(new contributorsItem("Narendra Dodwaria", BASE_URL + "narendra36.png", BASE_URL + "narendra36"));
        contributorsItems.add(new contributorsItem("Goutham Reddy ", BASE_URL + "zeus512.png", BASE_URL + "zeus512"));
        contributorsItems.add(new contributorsItem("Vijaya Laxmi", BASE_URL + "vijaya22.png", BASE_URL + "vijaya22"));

        //3rd Year
        contributorsItems.add(new contributorsItem("Aditya Arora", BASE_URL + "adi23arora.png", BASE_URL + "adi23arora"));
        contributorsItems.add(new contributorsItem("B Jatin Rao", BASE_URL + "Jatin0312.png", BASE_URL + "Jatin0312"));
        contributorsItems.add(new contributorsItem("Nitin Sharma", BASE_URL + "iamNitin16.png", BASE_URL + "iamNitin16"));
        contributorsItems.add(new contributorsItem("Suraj Negi", BASE_URL + "Akatsuki06.png", BASE_URL + "Akatsuki06"));
        contributorsItems.add(new contributorsItem("Parvesh Dhull", BASE_URL + "Parveshdhull.png", BASE_URL + "Parveshdhull"));

        //2nd Year
        contributorsItems.add(new contributorsItem("Bharat Shah", BASE_URL + "bharatshah1498.png", BASE_URL + "bharatshah1498"));
        contributorsItems.add(new contributorsItem("Kartik Saxena", BASE_URL + "SaxenaKartik.png", BASE_URL + "SaxenaKartik"));
        contributorsItems.add(new contributorsItem("Muskan Jhunjhunwalla", BASE_URL + "musukeshu.png", BASE_URL + "musukeshu"));
        contributorsItems.add(new contributorsItem("Anubhaw Bhalotia", BASE_URL + "anubhawbhalotia.png", BASE_URL + "anubhawbhalotia"));
        contributorsItems.add(new contributorsItem("Kaushal Kishore", BASE_URL + "kaushal16124.png", BASE_URL + "kaushal16124"));

        //1st Year
        contributorsItems.add(new contributorsItem("Utkarsh Singh", BASE_URL + "utkarshsingh99.png", BASE_URL + "utkarshsingh99"));
        contributorsItems.add(new contributorsItem("Vishal Parmar", BASE_URL + "Vishal17599.png", BASE_URL + "Vishal17599"));
        contributorsItems.add(new contributorsItem("Abhinav Lamba", BASE_URL + "Abhinavlamba.png", BASE_URL + "Abhinavlamba"));
        contributorsItems.add(new contributorsItem("Divyanshu",BASE_URL+"dextroxd.png",BASE_URL+"dextroxd"));
        contributorsItems.add(new contributorsItem("Nishant Singh Hada",BASE_URL+"thisisnsh.png",BASE_URL+"thisisnsh"));
        contributorsItems.add(new contributorsItem("Aakanksha Tewari",BASE_URL + "aakankshaa23.png",BASE_URL+"aakankshaa23")) ;
        contributorsItems.add(new contributorsItem("Abhiraj Singh Rathore", BASE_URL + "AbhirajSinghRathore.png", BASE_URL + "AbhirajSingh99"));
        contributorsItems.add(new contributorsItem("Alisha Mehta", BASE_URL + "Alisha1116.png", BASE_URL + "Alisha1116"));
        contributorsItems.add(new contributorsItem("Anshu Akansha", BASE_URL + "AnshuAkansha52227.png", BASE_URL + "AnshuAkansha"));
        contributorsItems.add(new contributorsItem("Daniyaal Khan", BASE_URL + "drtweety.png", BASE_URL + "drtweety"));
        contributorsItems.add(new contributorsItem("Tanvi Mahajan", BASE_URL + "tanvi003.png", BASE_URL + "tanvi003"));
        contributorsItems.add(new contributorsItem("Sarthak Gupta",BASE_URL+"SarthakGupta3.png",BASE_URL+"SarthakGupta3"));
        contributorsItems.add(new contributorsItem("Shweta Gurnani",BASE_URL+"shwetagurnani.png",BASE_URL+"shwetagurnani"));
        contributorsItems.add(new contributorsItem("Shubham Kumar",BASE_URL+"SkroX.png",BASE_URL+"SkroX"));
        contributorsItems.add(new contributorsItem("Vishal Shrivastava",BASE_URL+"vishalshrivastava16.png",BASE_URL+"vishalshrivastava16"));
        contributorsItems.add(new contributorsItem("Chandan Sitlani",BASE_URL+"ChandanSitlani.png",BASE_URL+"ChandanSitlani"));
        contributorsItems.add(new contributorsItem("Pawan Harariya",BASE_URL+"pawanharariya.png",BASE_URL+"pawanharariya"));
        contributorsItems.add(new contributorsItem("Ayush Kaushal",BASE_URL+"Yashu017.png",BASE_URL+"Yashu017"));
        contributorsItems.add(new contributorsItem("Ananya Sharma",BASE_URL+"Ananya-Sharma.png",BASE_URL+"Ananya-Sharma"));
        contributorsItems.add(new contributorsItem("Pranjal Dureja",BASE_URL+"pranjaldureja0002.png",BASE_URL+"pranjaldureja0002"));


        ContributorAdapter = new contributorsAdapter(contributorsItems, ContributorsActivity.this);
        rvContributors.setAdapter(ContributorAdapter);

//        tbContributers = (Toolbar) findViewById(R.id.contributors_toolbar);
//        tbContributers.setTitle("Contributors");
//        setSupportActionBar(tbContributers);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvContributors.setLayoutManager(new GridLayoutManager(this, 2));
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
                    collapsingToolbar.setTitle("Contributors");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
