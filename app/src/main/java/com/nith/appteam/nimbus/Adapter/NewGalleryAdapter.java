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

import com.nith.appteam.nimbus.Activity.ShowImage;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewGalleryAdapter extends RecyclerView.Adapter<NewGalleryAdapter.MyViewHolder>
{
    List <GalleryModel> gallery;
    Activity activity;
    public NewGalleryAdapter(List<GalleryModel> gallery , Activity activity)
    {
        this.gallery = gallery;
        this.activity = activity;
    }
    @Override
    public NewGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_new_gallery_adapter, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(NewGalleryAdapter.MyViewHolder holder, final int position) {
        holder.year.setText(gallery.get(position).getTitle());
        Picasso.get().load(gallery.get(position).getHeaderImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("year",gallery.get(position).getTitle());
                bundle.putStringArrayList("image",gallery.get(position).getImagesurl());
                Intent i = new Intent(activity,ShowImage.class);
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

        TextView year, textView2, textView3, textView4, textView1;

        ImageView button, button1, button3, image;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            year = itemView.findViewById(R.id.year);
            image = itemView.findViewById(R.id.image);
        }
    }
}
