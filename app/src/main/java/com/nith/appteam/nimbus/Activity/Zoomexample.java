package com.nith.appteam.nimbus.Activity;

/**
 * Created by LENOVO on 04-03-2019.
 */
import android.app.Activity;
import android.os.Bundle;
public class Zoomexample extends Activity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(new Zoom(this));
    }
}