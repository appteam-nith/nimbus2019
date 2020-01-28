package com.nith.appteam.nimbus.Adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.ShowImage;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.Notification.OnItemTouchListener;
import com.nith.appteam.nimbus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder>
{
    private OnItemTouchListener.OnItemClickListener mOnItemClickListener;
    ArrayList<String>gallery;
    Activity activity;

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    private ItemClickListener onItemClickListener;
    public void setItemClickListener (ItemClickListener clickListener) {
        onItemClickListener = clickListener;
    }
    public ImageAdapter(ArrayList<String> gallery , Activity activity)
    {
        this.gallery = gallery;
        this.activity = activity;
    }
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_image_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.MyViewHolder holder, final int position) {
//        holder.year.setText(gallery.get(position).getyear());
        Picasso.get().load(gallery.get(position)).into(holder.image);
    }
    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView year, textView2, textView3, textView4, textView1;

        ImageView button, button1,button3,image;

        public MyViewHolder(View itemView) {

            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }


}
