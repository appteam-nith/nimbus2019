package com.nith.appteam.nimbus.Adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nith.appteam.nimbus.Activity.ContestantActivity;
import com.nith.appteam.nimbus.Activity.ShowImage;
import com.nith.appteam.nimbus.Model.EventModel;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.Notification.OnItemTouchListener;
import com.nith.appteam.nimbus.R;
import com.squareup.picasso.Picasso;

import java.util.List;
public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.MyViewHolder>
{
    List <EventModel> gallery;
    Activity activity;


    public EventsListAdapter(List<EventModel> gallery , Activity activity)
    {
        this.gallery = gallery;
        this.activity = activity;
    }
    @Override
    public EventsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_events_list_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsListAdapter.MyViewHolder holder, final int position) {
        holder.event.setText(gallery.get(position).getName());
        if (gallery.get(position).getShowDates()) {
        holder.eventvenue.setVisibility(View.VISIBLE);
        holder.eventdate.setVisibility(View.VISIBLE);
        }
        holder.eventvenue.setText(gallery.get(position).getVenue());
        holder.eventdate.setText(gallery.get(position).getDate());
        }
    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView event,eventvenue,eventdate;

        CardView cardView;

        public MyViewHolder(View itemView) {

            super(itemView);
            event = itemView.findViewById(R.id.text);
            eventvenue = itemView.findViewById(R.id.venue);
            eventdate = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ContestantActivity.class);
                    intent.putExtra("name",gallery.get(getAdapterPosition()).getName());
                    intent.putExtra("eventId",gallery.get(getAdapterPosition()).getId());
                    intent.putStringArrayListExtra("rules",gallery.get(getAdapterPosition()).getRules());
                    intent.putExtra("desc",gallery.get(getAdapterPosition()).getDescription());
                    Gson gson = new Gson();
                    String prizemodels = gson.toJson(gallery.get(getAdapterPosition()).getPrizeModels());
                    intent.putExtra("prize",prizemodels);
                    activity.startActivity(intent);
                }
            });
        }
    }


}
