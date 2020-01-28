package com.nith.appteam.nimbus.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.ShortBufferException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowProfile extends AppCompatActivity {
    EditText email,branch,year,rollno,editname;
    TextView name,editButton;
    SharedPref sharedPref;
    String pass = "";
    private byte[] byteArray;
    private Bitmap bmp, img;
    private int PICK_PHOTO_CODE = 1046;
    FloatingActionButton fab;
    int touch = 0;
    String names, branchs, rollnos , years, profileS,emails;
    CircleImageView profilePicture;
    public String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//        Toast.makeText(ShowProfile.this, imageEncoded, Toast.LENGTH_SHORT).show();
        return imageEncoded;
    }
ProgressBar progressCenter;
String encoded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        name = findViewById(R.id.profileName);
        email = findViewById(R.id.email);
        progressCenter = findViewById(R.id.progressCenter);
        branch = findViewById(R.id.branch);
        pass = "https://res.cloudinary.com/dpxfdn3d8/image/upload/v1553173872/user.png";
        year = findViewById(R.id.year);
        editname = findViewById(R.id.editname);
        fab = findViewById(R.id.edit);
        rollno = findViewById(R.id.rollno);
        profilePicture = findViewById(R.id.profilePicture);
                sharedPref = new SharedPref(this);
        profilePicture.setClickable(false);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo2",MODE_PRIVATE);
        String Name = sharedPreferences.getString("name","Not Filled");
        String Email = sharedPreferences.getString("email","Not Filled");
        String Branch = sharedPreferences.getString("Branch","Not Filled");
        String Year = sharedPreferences.getString("year_pos","Not Filled");
        String rollNu = sharedPreferences.getString("rollno","Not Filled");
        encoded = sharedPreferences.getString("image","null");
        if (!encoded.equals("null")) {
            pass = encoded;
            Picasso.get().load(encoded).into(profilePicture);
        }
        else
        {
            encoded = "https://res.cloudinary.com/dpxfdn3d8/image/upload/v1553173872/user.png";
            profilePicture.setImageResource(R.drawable.user);
        }
        //
//  Toast.makeText(this, Name + Email + Branch + Year + rollNu, Toast.LENGTH_SHORT).show();
        YoYo.with(Techniques.RollIn)
                .duration(1000)
                .repeat(0)
                .playOn(findViewById(R.id.profilePicture));
        if (!Name.equals("Not Filled"))
        name.setText(Name);
        else
            name.setText("Username");
        if (!Email.equals("Not Filled"))
        email.setText(Email);
        if (!Branch.equals("Not Filled"))
            branch.setText(Branch);
        if (!Year.equals("Not Filled"))
            year.setText(Year);
        if (!rollNu.equals("Not Filled"))
            rollno.setText(rollNu);
        email.setFocusable(false);
        branch.setFocusable(false);
        year.setFocusable(false);
        rollno.setFocusable(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (touch == 0) {
                    touch = 1;
                    email.setFocusableInTouchMode(true);
                    branch.setFocusableInTouchMode(true);
                    rollno.setFocusableInTouchMode(true);
                    year.setFocusableInTouchMode(true);
                    editname.setVisibility(View.VISIBLE);
                    editname.setText(name.getText());
                    name.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.updatep);
                    profilePicture.setClickable(true);
                    profilePicture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, PICK_PHOTO_CODE);
                            }
                        }
                    });
                }
                else
                {
                    progressCenter.setVisibility(View.VISIBLE);
                    fab.setClickable(false);
                    Bitmap bitmap = ((BitmapDrawable)profilePicture.getDrawable()).getBitmap();
                    //                    Bitmap bitmap = profilePicture.getDrawable()
                    pass = execute(bitmap);
                }
                }
        });
    }
    public String execute(Bitmap bitmap)
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
                        Log.v("HelloMr",pass);
                        progressCenter.setVisibility(View.GONE);
                        fab.setClickable(true);
//                        Toast.makeText(ProfileNew.this, pass, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ProfileNew.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        finish();
                        names = editname.getText().toString();
                        emails = email.getText().toString();
                        profileS = pass;
                        rollnos = rollno.getText().toString();
                        branchs = branch.getText().toString();
                        years = year.getText().toString();
                        SharedPreferences sp = getSharedPreferences("UserInfo2",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name",editname.getText().toString());
                        editor.putString("email",email.getText().toString());
                        editor.putString("Branch",branch.getText().toString());
                        editor.putString("year_pos",year.getText().toString());
                        editor.putString("rollno",rollno.getText().toString());
                        editor.putString("image",pass);
                        editor.apply();
//                        Toast.makeText(ShowProfile.this, pass, Toast.LENGTH_SHORT).show();
                        Log.v("passing pass",pass);
                        touch = 0;
                        email.setFocusable(false);
                        branch.setFocusable(false);
                        rollno.setFocusable(false);
                        year.setFocusable(false);
                        profilePicture.setClickable(false);
                        editname.setVisibility(View.GONE);
                        name.setVisibility(View.VISIBLE);
                        name.setText(editname.getText().toString());
                        fab.setImageResource(R.drawable.edit);
                        postData();
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.v("HELLO","JIJIJ");
//                        finish();
                        Toast.makeText(ShowProfile.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        // your code here
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // your code here
                    }
                })
                .dispatch(ShowProfile.this);
    return pass;
    }
    public void postData()
    {
        if (!name.getText().toString().isEmpty() && !rollno.getText().toString().isEmpty() && !branch.getText().toString().isEmpty() && !email.getText().toString().isEmpty())
        {
//            Toast.makeText(ProfileNew.this,sharedPref.getHashedValue(),Toast.LENGTH_LONG).show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", names);
                    jsonObject.put("rollNumber",rollnos);
                    jsonObject.put("authId", sharedPref.getHashedValue());
                    jsonObject.put("branch",branchs);
                    jsonObject.put("year", years);
                    jsonObject.put("profilePicture",pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.v("TESTINGSHOWPROFILE", String.valueOf(jsonObject));
                Log.v("GETHASHEDVALUE",sharedPref.getHashedValue());
                final String requestBody = jsonObject.toString();
                RequestQueue queue = Volley.newRequestQueue(ShowProfile.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.base_url)+"/auth/info", new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Response",response);
//                        Toast.makeText(ShowProfile.this,response,Toast.LENGTH_LONG).show();
                        if(response.equals("OK")){
                            Toast.makeText(ShowProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();

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

                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);

            } else {
                Toast.makeText(ShowProfile.this, "Please Provide all details", Toast.LENGTH_LONG).show();
            }
        }

    String p;
    public String decodeBase64String(String encodedString)
    {
        byte[] data = Base64.decode(encodedString, Base64.DEFAULT);
        try {
            p = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    return  p;
    }

    Bitmap textToBitmap(String text)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(12);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, 0, paint);
        return bitmap;
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
            profilePicture = findViewById(R.id.profilePicture);
            profilePicture.setImageBitmap(img);
        }
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        }
        else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
