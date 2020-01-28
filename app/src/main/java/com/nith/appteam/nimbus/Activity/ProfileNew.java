package com.nith.appteam.nimbus.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.google.firebase.FirebaseApp;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileNew extends AppCompatActivity {
    private TextView textView;
    private EditText FirstName,Emailid,RollNo,Branch;
    private EditText Year;
    private ProgressBar progressBar;
    private SharedPref sharedPref;
    LinearLayout progress;
    String pass = "";
    int a = 0;
    private byte[] byteArray;
    private EditText studentName, rollNumber, branch, contactNumber, referral;
    private String Name, base64a, base64b, RollNumber, referal, ContactNumber, imgUrl;
    private CircleImageView profilePicture;
    private CircleImageView buttonLoadImage, save;
    private Bitmap bmp, img;
    private int PICK_PHOTO_CODE = 1046;

    public String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//        Toast.makeText(ProfileNew.this, imageEncoded, Toast.LENGTH_SHORT).show();
        return imageEncoded;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_new);
        pass = "https://res.cloudinary.com/dpxfdn3d8/image/upload/v1553173872/user.png";
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        textView = findViewById(R.id.submit);
        FirstName = findViewById(R.id.name);
        Emailid = findViewById(R.id.email);
        RollNo = findViewById(R.id.rollno);
        Branch = findViewById(R.id.branch);
        Year = findViewById(R.id.year);
        buttonLoadImage = findViewById(R.id.profilePicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                buttonLoadImage.setText("     ");
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }

            }
        });
        sharedPref = new SharedPref(this);
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (pass.equals(""))
                    pass = "https://res.cloudinary.com/dpxfdn3d8/image/upload/v1553173872/user.png";
                if (!FirstName.getText().toString().isEmpty() && !RollNo.getText().toString().isEmpty() && !Branch.getText().toString().isEmpty() && !Emailid.getText().toString().isEmpty()){
                    if (isValidEmail(Emailid.getText().toString())) {
                        textView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
//                        Toast.makeText(ProfileNew.this,sharedPref.getHashedValue(),Toast.LENGTH_LONG).show();

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("name", FirstName.getText().toString());
                            jsonObject.put("rollNumber", RollNo.getText().toString());
                            jsonObject.put("authId", sharedPref.getHashedValue());
                            jsonObject.put("branch", Branch.getText().toString());
                            jsonObject.put("year", Year.getText().toString());
                            jsonObject.put("profilePicture",pass);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v("TESTING", String.valueOf(jsonObject));
                        final String requestBody = jsonObject.toString();
                        RequestQueue queue = Volley.newRequestQueue(ProfileNew.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/auth/info", new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

//                                Toast.makeText(ProfileNew.this,response,Toast.LENGTH_LONG).show();
                                if(response.equals("OK")){
//                                    sharedPref.setUserName(FirstName.getText().toString());
//                                    sharedPref.setUserRollno(RollNo.getText().toString());
//                                    sharedPref.setUserEmail(Emailid.getText().toString());
//                                    sharedPref.setUserBranch(Branch.getText().toString());
//                                    sharedPref.setUserYearPos(Year.getText().toString());
                                    SharedPreferences sharedPreferences;
                                    sharedPreferences = getSharedPreferences("UserInfo2",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", FirstName.getText().toString());
                                    editor.putString("rollno", RollNo.getText().toString());
                                    editor.putString("email", Emailid.getText().toString());
                                    editor.putString("Branch", Branch.getText().toString());
                                    editor.putString("year_pos", Year.getText().toString());
                                    editor.putString("image",pass);
                                    editor.commit();
                                    sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
                                    editor = sharedPreferences.edit();
                                    editor.putString("name", FirstName.getText().toString());
                                    editor.putString("rollno", RollNo.getText().toString());
                                    editor.putString("email", Emailid.getText().toString());
                                    editor.putString("Branch", Branch.getText().toString());
                                    editor.putString("year_pos", Year.getText().toString());
                                    editor.putString("image",pass);
                                    editor.commit();

                                    //                                    Toast.makeText(ProfileNew.this, FirstName.getText().toString(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(ProfileNew.this,HomescreenNew.class));
                                    finish();

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
//                            @Override
//                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                                int mStatusCode = response.statusCode;
//                                Toast.makeText(ProfileNew.this,mStatusCode,Toast.LENGTH_SHORT).show();
//                                if(mStatusCode==200){
//                                    startActivity(new Intent(ProfileNew.this,HomescreenNew.class));
//                                    finish();
//                                    Toast.makeText(ProfileNew.this,sharedPref.getUserName(),Toast.LENGTH_LONG).show();
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                                else{
//                                    Toast.makeText(ProfileNew.this,"Unsuccessful attempt please try again",Toast.LENGTH_SHORT).show();
//                                }
//                                return super.parseNetworkResponse(response);
//                            }


                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        queue.add(stringRequest);

                    } else {
                        Toast.makeText(ProfileNew.this, "Enter Correct email address", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(ProfileNew.this, "Enter All Details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void execute(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byteArray = stream.toByteArray();
        String requestId = MediaManager.get().upload(byteArray).constrain(TimeWindow.immediate())
                .unsigned("h7fwopdv")
                .option("connect_timeout", 10000)
                .option("read_timeout", 10000)
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {

                    }
                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
//                                      System.out.println("URLLL  "+resultData.get("url"));
                        pass = String.valueOf(resultData.get("url"));
//
                        textView.setClickable(true);
                        ProgressBar progressBar = findViewById(R.id.pbcenter);
                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(ProfileNew.this, pass, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ProfileNew.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        finish();
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.v("HELLO","JIJIJ");
//                        finish();
                        Toast.makeText(ProfileNew.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        // your code here
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // your code here
                    }
                })
                .dispatch(ProfileNew.this);
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri photoUri = data.getData();
            Bitmap selectedImage = null;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);
            byteArray = bs.toByteArray();
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            img = getResizedBitmap(bmp, 300);
//            pass = encodeTobase64(img);
            profilePicture = findViewById(R.id.profilePicture);
            profilePicture.setImageBitmap(img);
            a = 1;
            Bitmap bitmap = ((BitmapDrawable)profilePicture.getDrawable()).getBitmap();
            execute(bitmap);
            ProgressBar progressBar = findViewById(R.id.pbcenter);
            progressBar.setVisibility(View.VISIBLE);
            textView.setClickable(false);
        }


    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


}