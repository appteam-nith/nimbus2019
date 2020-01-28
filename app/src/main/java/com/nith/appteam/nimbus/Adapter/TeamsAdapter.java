package com.nith.appteam.nimbus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.JoinTeam;
import com.nith.appteam.nimbus.Model.TeamsModel;
import com.nith.appteam.nimbus.R;

import java.util.ArrayList;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TeamsModel>list;
    private String eventid;
    public TeamsAdapter(Context context, ArrayList<TeamsModel> list,String eventid) {
        this.context = context;
        this.list = list;
        this.eventid = eventid;
    }

    @Override
    public TeamsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_events_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamsAdapter.ViewHolder holder, int position) {
        final TeamsModel teamsModel = list.get(position);
        holder.textView.setText(teamsModel.getName());
        if(teamsModel.getMemebers().size()==4){
            holder.cardView.setCardBackgroundColor(Color.argb(255,169,169,169));
            holder.cardView.setEnabled(false);
            holder.cardView.setClickable(false);
        }
        else {
            holder.cardView.setCardBackgroundColor(Color.argb(255,255,255,255));
            holder.cardView.setEnabled(true);
            holder.cardView.setClickable(true);
        }
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(context, JoinTeamActivity.class);
////                intent.putExtra("eventid",eventid);
////                intent.putExtra("teamid",teamsModel.getId());
////                ArrayList<String> name = new ArrayList<>();
////                for(int i=0;i<teamsModel.getMemebers().size();i++){
////
////                    name.add(teamsModel.getMemebers().get(i).getName());
////                }
////                intent.putExtra("name",name);
////                context.startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(context, JoinTeam.class);
                intent.putExtra("eventId",eventid);
                intent.putExtra("teamId",list.get(getAdapterPosition()).getId());
                context.startActivity(intent);
                }
            });

        }
    }
}
