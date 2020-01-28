package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import nl.dionsegijn.konfetti.KonfettiView;

public class QuizResultActivity extends AppCompatActivity {
    JSONObject jsonObject;
    String user = "14543";
    KonfettiView viewKonfetti;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        String quizId = QuizActivityNew.quizId;
        viewKonfetti=findViewById(R.id.viewKonfetti);
        JSONArray jsonArray = QuizActivityNew.jsonArraypost;
        result = findViewById(R.id.result);
        jsonObject = new JSONObject();
        viewKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0,359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(nl.dionsegijn.konfetti.models.Shape.CIRCLE, nl.dionsegijn.konfetti.models.Shape.RECT)
                .addSizes(new nl.dionsegijn.konfetti.models.Size(12,5))
                .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);
        try {
            jsonObject.put("quizId",quizId);
            jsonObject.put("answers",jsonArray);
            Log.v("postData", String.valueOf(jsonObject));
            postData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void postData() {
        final SharedPref sharedPref = new SharedPref(this);
        final String requestBody = jsonObject.toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/quiz/answers", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String correct = jsonObject.getString("correct");
                    Log.v("Correct",correct);
                    result.setText(correct);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizResultActivity.this,HomescreenNew.class));
        finishAffinity();
    }

}
