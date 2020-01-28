package com.nith.appteam.nimbus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.LeaderBoardActivity;
import com.nith.appteam.nimbus.Activity.QuizActivityNew;
import com.nith.appteam.nimbus.Activity.ShowImage;
import com.nith.appteam.nimbus.Model.CategoryQuiz;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyViewHolder>
{
    List <CategoryQuiz> gallery;
    Activity activity;
    public QuizAdapter(List<CategoryQuiz> gallery , Activity activity)
    {
        this.gallery = gallery;
        this.activity = activity;
    }
    @Override
    public QuizAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_quiz_adapter, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(QuizAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(gallery.get(position).getEvent());
        Picasso.get().load(gallery.get(position).getImageUrl()).into(holder.image);
        holder.hostedBy.setText(gallery.get(position).getHostedBy());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("ID",gallery.get(position).getQuizId());
                Intent i = new Intent(activity,QuizActivityNew.class);
                i.putExtras(bundle);
                activity.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView year, name , hostedBy;
        ImageView button, button1, button3, image;
        public MyViewHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            hostedBy = itemView.findViewById(R.id.core_team_designation);
        }
    }
}
