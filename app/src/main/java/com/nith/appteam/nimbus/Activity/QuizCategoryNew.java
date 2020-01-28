package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nith.appteam.nimbus.Adapter.LeaderBoardAdapterNew;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

public class QuizCategoryNew extends AppCompatActivity {
Toolbar toolbar;
TextView enter_leader,enter_quiz;
String quizId;
int time;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category_new);
        Bundle bundle = getIntent().getExtras();
        quizId = bundle.getString("quizId");
        time = bundle.getInt("time");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            YoYo.with(Techniques.ZoomIn)
                    .duration(1300)
                    .repeat(0)
                    .playOn(findViewById(R.id.enter_quiz));

            YoYo.with(Techniques.ZoomIn)
                    .duration(1300)
                    .repeat(0)
                    .playOn(findViewById(R.id.enter_leader));
        }

    },0);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enter_leader = findViewById(R.id.enter_leader);
        enter_quiz=findViewById(R.id.enter_quiz);
        enter_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizCategoryNew.this, QuizActivityNew.class);
                intent.putExtra("quizId",quizId);
                intent.putExtra("time",time);
                startActivity(intent);
                finish();
            }
        });
        enter_leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizCategoryNew.this, LeaderBoardActivity.class);
                intent.putExtra("quizId",quizId);
                startActivity(intent);
                finish();
            }
        });

    }
}
