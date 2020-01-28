package com.nith.appteam.nimbus.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus.R;

public class CustomDialog extends Dialog{
    TextView no,yes;
    public static int status = 0;
    public CustomDialog(@NonNull Context context) {
        super(context);
        initUI();
    }
    public void initUI() {
        setContentView(R.layout.activity_custom_dialog);
//        setContentView(R.layout.dialogbox);
        setCancelable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.3f;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        TextView cancel,ok;
        ok = findViewById(R.id.yes);
        cancel = findViewById(R.id.no);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 1;
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 0;
                dismiss();
            }
        });
    }
}
