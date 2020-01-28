package com.nith.appteam.nimbus.CustomView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.nith.appteam.nimbus.Model.PrizeModel;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

public class EventDialog extends Dialog {

    private Activity activity;
    TextView description,rules,prizes;
    private String desc = " ";
    ArrayList<String>rule;
    ArrayList<PrizeModel>model;
    public EventDialog(@NonNull Activity context, String desc , ArrayList<String>rule, ArrayList<PrizeModel>model)
    {
        super(context);
        activity=context;
        this.desc = desc;
        this.rule = rule;
        this.model = model;
        initUI();
    }

    public void initUI(){
        setContentView(R.layout.team_adapter);
        setCancelable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.3f;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setAttributes(lp);
        getData();
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void getData(){
        String rulesfinal = "";
        String prizes1 = " ";
        for(int i=0;i<rule.size();i++){
            rulesfinal+=rule.get(i)+" ";
        }
        for(int i=0;i<model.size();i++){
            prizes1+=model.get(i).getPosition()+" "+model.get(i).getPrize()+"\n";
        }
        description=findViewById(R.id.description);
        description.setText(desc);
        rules=findViewById(R.id.rules);
        rules.setText(rulesfinal);
        prizes=findViewById(R.id.prizes);
        prizes.setText(prizes1);
    }
}
