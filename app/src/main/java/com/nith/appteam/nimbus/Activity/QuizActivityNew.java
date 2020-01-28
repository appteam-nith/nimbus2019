package com.nith.appteam.nimbus.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jsibbold.zoomage.ZoomageView;
import com.nith.appteam.nimbus.Model.CategoryQuiz;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class QuizActivityNew extends AppCompatActivity {
    public static String quizId="NULL";
    TextView timeLeft,imagepresent;
    TextView question , option1,option2,option3,option4,button,number;
    CardView o1,o2,o3,o4;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    SharedPref sharedPref;
    int timeLimit;
    List <String> pictureUrl;
//    JSONArray jsonArray;
    int selected = 0,transition=0;
//    int transition = 0;
    ZoomageView zoomageView;
//    String user = "14543";
    private long remaining = 0;
    JSONObject jsonObjectpost;
    int visiblePhoto = 0;
    public static JSONArray jsonArraypost;
    List<String> questions,optionA,optionB,optionC,optionD,questionsId,answers;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_new);
        sharedPref= new SharedPref(this);
        questions = new ArrayList<>();
        jsonArraypost = new JSONArray();
        jsonObjectpost = new JSONObject();
        linearLayout = findViewById(R.id.activity_quiz_question);
        progressBar = findViewById(R.id.pb);
        optionA = new ArrayList<>();
        optionB = new ArrayList<>();
        optionC = new ArrayList<>();
        optionD = new ArrayList<>();
        imagepresent = findViewById(R.id.imagepresent);
        pictureUrl = new ArrayList<>();
        question = findViewById(R.id.questions);
        number = findViewById(R.id.number);
        zoomageView = findViewById(R.id.myZoomageView);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        o1=findViewById(R.id.optionA);
        o2= findViewById(R.id.optionB);
        o3 = findViewById(R.id.optionC);
        o4 = findViewById(R.id.optionD);
        timeLeft = findViewById(R.id.time_left);
        questionsId = new ArrayList<>();
        button = findViewById(R.id.finish);
        answers = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        quizId = bundle.getString("quizId");
        timeLimit = bundle.getInt("time");
        timeLimit*=1000;
        imagepresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visiblePhoto = 1;
                zoomageView.setVisibility(View.VISIBLE);
            }
        });
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        Log.v("timeLimitJi", String.valueOf(timeLimit));
        if (quizId.equals("NULL"))
        quizId = "5c7ec33efb6fc072012e8aef";
        Toast.makeText(this, quizId, Toast.LENGTH_SHORT).show();

        try {
            jsonObjectpost.put("quizId",quizId);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        postData();
    }
    int loop = 1;
    int j = 0;
    public void postData()
    {
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("quizId",quizId);
            Log.v("JSONBody", String.valueOf(jsonBody));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/quiz/questions", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONArray jsonObject = new JSONArray(response);
//                    Toast.makeText(QuizActivityNew.this, (CharSequence) jsonObject, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonObject.length(); i++)
                    {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                        questions.add(jsonObject1.getString("question"));
                        questionsId.add(jsonObject1.getString("_id"));
                        optionA.add(jsonObject1.getString("option1"));
                        optionB.add(jsonObject1.getString("option2"));
                        optionC.add(jsonObject1.getString("option3"));
                        optionD.add(jsonObject1.getString("option4"));
                        pictureUrl.add(jsonObject1.getString("picture"));
//                        pictureUrl.add("http://res.cloudinary.com/appteamnith/image/upload/v1553197285/poif4novhqih6ycoobkt.jpg");
                    Log.v("Display options : ",jsonObject1.getString("option1") + jsonObject1.getString("option2")+jsonObject1.getString("option3")+jsonObject1.getString("option4"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(QuizActivityNew.this, String.valueOf(questions.size()), Toast.LENGTH_SHORT).show();
                if (questions.size() >= 10) {
                    TextView ques = findViewById(R.id.ques);
                    ques.setText("Ques ");
                    visiblePhoto = 0;
                    zoomageView.setVisibility(View.GONE);
                    number.setText("1");
                    YoYo.with(Techniques.ZoomIn)
                            .duration(800)
                            .repeat(0)
                            .playOn(findViewById(R.id.questions));
                    YoYo.with(Techniques.ZoomIn)
                            .duration(800)
                            .repeat(0)
                            .playOn(findViewById(R.id.time_left));
                    YoYo.with(Techniques.ZoomIn)
                            .duration(800)
                            .repeat(0)
                            .playOn(findViewById(R.id.option1));
                    YoYo.with(Techniques.ZoomIn)
                            .duration(800)
                            .repeat(0)
                            .playOn(findViewById(R.id.option2));
                    YoYo.with(Techniques.ZoomIn)
                            .duration(800)
                            .repeat(0)
                            .playOn(findViewById(R.id.option3));
                    YoYo.with(Techniques.ZoomIn)
                            .duration(800)
                            .repeat(0)
                            .playOn(findViewById(R.id.option4));
                    YoYo.with(Techniques.ZoomIn)
                            .duration(800)
                            .repeat(0)
                            .playOn(findViewById(R.id.imagepresent));

                    if (!pictureUrl.get(0).equals("")) {
                        imagepresent.setVisibility(View.VISIBLE);
                        Picasso.get().load(pictureUrl.get(0)).into(zoomageView);
                    }
                    else
                        imagepresent.setVisibility(View.GONE);
                    new CountDownTimer(timeLimit, 1000) {
                        public void onTick(final long millisUntilFinished) {
                            linearLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            long millis = millisUntilFinished;
                            timeLeft.setText("00:" + millisUntilFinished / 1000);
                            question.setText(questions.get(0));
                            option1.setText(optionA.get(0));
                            option2.setText(optionB.get(0));
                            option3.setText(optionC.get(0));
                            option4.setText(optionD.get(0));
                            o1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selected = 1;
                                    remaining = millisUntilFinished/1000;
                                    cancel();
                                    onFinish();
                                }
                            });
                            o2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selected = 2;
                                    remaining = millisUntilFinished/1000;
                                    cancel();
                                    onFinish();
                                }
                            });
                            o3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selected = 3;
                                    remaining = millisUntilFinished/1000;
                                    cancel();
                                    onFinish();
                                }
                            });
                            o4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selected = 4;
                                    remaining = millisUntilFinished/1000;
                                    cancel();
                                    onFinish();
                                }
                            });
                            //
                        }

                        public void onFinish() {
                            JSONObject jsonObjectNew = new JSONObject();
//                        jsonObjectNew.put("questionsId",questionsId.get(0));
                            try {
                                jsonObjectNew.put("questionId", questionsId.get(0));
                                jsonObjectNew.put("answer", String.valueOf(selected));
                                jsonObjectNew.put("remainingTime",remaining);
                                jsonArraypost.put(jsonObjectNew);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            selected = 0;
                            question(1);
                        }
                    }.start();
                }

                button.setVisibility(View.GONE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            jsonObjectpost.putOpt("answers",jsonArraypost);
                            Log.v("ArrayPost", String.valueOf(jsonArraypost));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

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
                headers.put("token", sharedPref.getHashedValue());
//                Log.v("")
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
            protected Response<String> parseNetworkResponse(NetworkResponse response)
            {
                int mStatusCode = response.statusCode;

                return super.parseNetworkResponse(response);
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
    int time;
    public void question (final int loop)
    {
        time = loop;
        if (time >= 10) {
            try {
                jsonObjectpost.putOpt("answers",jsonArraypost);
                Log.v("Array Show", String.valueOf(jsonArraypost));
                if (transition == 0) {
                    transition = 1;
                    Intent intent = new Intent(QuizActivityNew.this, QuizResultActivity.class);
//                intent.put
                    startActivity(intent);
                }
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Quiz Ended", Toast.LENGTH_SHORT).show();
        }
            else if (questions.size() >= 10)
            {
                zoomageView.setVisibility(View.GONE);
                visiblePhoto = 0;
                if (!pictureUrl.get(time).equals("")) {
                    imagepresent.setVisibility(View.VISIBLE);
                    Picasso.get().load(pictureUrl.get(time)).into(zoomageView);
                }
                    else
                    imagepresent.setVisibility(View.GONE);
            YoYo.with(Techniques.ZoomIn)
                    .duration(800)
                    .repeat(0)
                    .playOn(findViewById(R.id.questions));
            YoYo.with(Techniques.ZoomIn)
                    .duration(800)
                    .repeat(0)
                    .playOn(findViewById(R.id.time_left));
                YoYo.with(Techniques.ZoomIn)
                        .duration(800)
                        .repeat(0)
                        .playOn(findViewById(R.id.option1));
                YoYo.with(Techniques.ZoomIn)
                        .duration(800)
                        .repeat(0)
                        .playOn(findViewById(R.id.option2));
                YoYo.with(Techniques.ZoomIn)
                        .duration(800)
                        .repeat(0)
                        .playOn(findViewById(R.id.option3));
                YoYo.with(Techniques.ZoomIn)
                        .duration(800)
                        .repeat(0)
                        .playOn(findViewById(R.id.option4));
                YoYo.with(Techniques.ZoomIn)
                        .duration(800)
                        .repeat(0)
                        .playOn(findViewById(R.id.imagepresent));
                Log.v("Hello", String.valueOf(loop));
            new CountDownTimer(timeLimit, 1000) {
                public void onTick(final long millisUntilFinished) {
                    long millis = millisUntilFinished;

                    number.setText(String.valueOf(loop+1));
                    question.setText(questions.get(time));
                    option1.setText(optionA.get(time));
                    option2.setText(optionB.get(time));
                    option3.setText(optionC.get(time));
                    option4.setText(optionD.get(time));
                    selected = 0;
                    timeLeft.setText("00:" + millisUntilFinished / 1000);
                    o1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selected = 1;
                            remaining = millisUntilFinished/1000;
                            cancel();
                            onFinish();
                        }
                    });
                    o2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selected = 2;
                            remaining = millisUntilFinished/1000;
                            cancel();
                            onFinish();
                        }
                    });
                    o3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selected = 3;
                            remaining = millisUntilFinished/1000;
                            cancel();
                            onFinish();
                        }
                    });
                    o4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selected = 4;
                            remaining = millisUntilFinished/1000;
                            cancel();
                            onFinish();
                        }
                    });
//                timeLeft.setText(hms);
                }

                public void onFinish() {
                    JSONObject jsonObjectNew = new JSONObject();
//                        jsonObjectNew.put("questionsId",questionsId.get(0));
                    try {
                        jsonObjectNew.put("questionId", questionsId.get(time));
                        jsonObjectNew.put("answer", String.valueOf(selected));
                        jsonObjectNew.put("remainingTime",remaining);
                        jsonArraypost.put(jsonObjectNew);
                        time++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (time < 10)
                        question(time);
                    else {
                        Toast.makeText(QuizActivityNew.this, "Quiz Ended", Toast.LENGTH_SHORT).show();
                        try {
                            jsonObjectpost.putOpt("answers",jsonArraypost);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v("Array Show", String.valueOf(jsonArraypost));
                        if (transition == 0) {
                            transition = 1;
                            Intent intent = new Intent(QuizActivityNew.this, QuizResultActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    }
                }
            }.start();
        }
        else
            return;
    }

    @Override
    public void onBackPressed()
    {
if (visiblePhoto == 1)
{
    visiblePhoto = 0;
    zoomageView.setVisibility(View.GONE);
}
else {
    CustomDialog customDialog = new CustomDialog(QuizActivityNew.this);
    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    customDialog.show();
    customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if (CustomDialog.status == 1) {
                transition = 1;
                if (questionsId.size() >= 10) {
                    for (int i = time; i < 10; i++) {
                        selected = 0;
                        remaining = timeLimit;
                        JSONObject jsonObjectNew = new JSONObject();
                        try {
                            jsonObjectNew.put("questionId", questionsId.get(i));
                            jsonObjectNew.put("answer", String.valueOf(selected));
                            jsonObjectNew.put("remainingTime", remaining);
                            jsonArraypost.put(jsonObjectNew);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        jsonObjectpost.putOpt("answers", jsonArraypost);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                        Toast.makeText(QuizActivityNew.this, String.valueOf(jsonArraypost.length()), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(QuizActivityNew.this, QuizResultActivity.class));
                    finishAffinity();
                } else
                    startActivity(new Intent(QuizActivityNew.this, HomescreenNew.class));
                finishAffinity();
            }

        }
    });
}
    }
}

