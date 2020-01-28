package com.nith.appteam.nimbus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.ShowImage;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.Model.LeaderBoardModelNew;
import com.nith.appteam.nimbus.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardAdapterNew extends RecyclerView.Adapter<LeaderBoardAdapterNew.MyViewHolder>
{
    List <LeaderBoardModelNew> gallery;
    Activity activity;
    public LeaderBoardAdapterNew(List<LeaderBoardModelNew> gallery , Activity activity)
    {
        this.gallery = gallery;
        this.activity = activity;
    }
//    public LeaderBoardAdapterNew(){
//
//
// }
    @Override
    public LeaderBoardAdapterNew.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leader_board_adapter_new, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(LeaderBoardAdapterNew.MyViewHolder holder, final int position) {
        holder.name.setText(gallery.get(position).getUsername());
        holder.score.setText(gallery.get(position).getScore());
//        byte[] decodedString = Base64.decode(gallery.get(position).getEncoded(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.profileImage.setImageBitmap(decodedByte);
        Picasso.get().load(gallery.get(position).getEncoded()).into(holder.profileImage);
    }
    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,score;

        CircleImageView button, button1, button3, profileImage;

        public MyViewHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.leader_username);
            score = itemView.findViewById(R.id.leader_score);
            profileImage = itemView.findViewById(R.id.leader_pic);
        }
    }
}
