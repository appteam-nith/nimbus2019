package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

public class Events extends AppCompatActivity {
    private TextView name,info,next;
    private String clubId,inform,logourl;
    private Intent i;
    private SharedPref sharedPref;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        sharedPref = new SharedPref(this);
        toolbar = findViewById(R.id.toolbar);
        //        name = findViewById(R.id.clubName);
        setSupportActionBar(toolbar);
        info = findViewById(R.id.info12);
        next = findViewById(R.id.nextbtn);
        final Intent intent = getIntent();
//        name.setText(intent.getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(intent.getStringExtra("name"));
//        Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
        clubId = intent.getStringExtra("id");
        inform = intent.getStringExtra("info");
        logourl = intent.getStringExtra("logo");
        info.setText(inform);
        i = new Intent(Events.this,EventsList.class);
        i.putExtra("clubid",clubId);
        i.putExtra("clubName",intent.getStringExtra("name"));
        if(!sharedPref.getappactivityfirsttime(intent.getStringExtra("name"))){
            startActivity(i);
            finish();
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.setappactivityfirsttime(intent.getStringExtra("name"));
                startActivity(i);
                finish();
            }
        });
//        final Bundle bundle = getIntent().getExtras();
//        final String position = bundle.getString("clubname");
//        events.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Events.this,EventsList.class);
//                Bundle b = new Bundle();
//                b.putString("clubname",position);
//                i.putExtras(b);
//                startActivity(i);
//            }
//        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
