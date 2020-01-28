package com.nith.appteam.nimbus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.Events;
import com.nith.appteam.nimbus.Activity.ShowImage;
import com.nith.appteam.nimbus.Model.CoreTeam;
import com.nith.appteam.nimbus.Model.DepartmentalClubs;
import com.nith.appteam.nimbus.Model.Gallery;
import com.nith.appteam.nimbus.Model.GalleryModel;
import com.nith.appteam.nimbus.Notification.OnItemTouchListener;
import com.nith.appteam.nimbus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TeamsNew extends RecyclerView.Adapter<TeamsNew.MyViewHolder>
{

    List <DepartmentalClubs> gallery;
    Activity activity;

    public TeamsNew (List<DepartmentalClubs> gallery , Activity activity)
    {
        this.gallery = gallery;
        this.activity = activity;
    }
    @Override
    public TeamsNew.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_teams_new, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeamsNew.MyViewHolder holder, final int position) {
        final DepartmentalClubs departmentalClubs = gallery.get(position);
        holder.team.setText(gallery.get(position).getNameClub());
        if (gallery.get(position).getNameClub().equals("App Team NITH")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552846608/applogo.png").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("English Club")){
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552848506/12208804_499697503543700_1838820025682692401_n.png").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Informals")){
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/10424984_711670472256379_3132724176294543488_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("C-Helix")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/1932483_880595615290587_434542123_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Hermetica")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/12744229_1739875389568500_8348044034765806723_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Medextrous")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/166658_123795731025791_6559205_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Ojas")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/1185032_660123224044505_582237825_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Vibhav")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/11006429_811133905619291_1349048637482079085_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("DesignOcrats")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/16996101_963569097076905_768367180693726447_n.png").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Pixonoids")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/42340513_2111757782190441_1971627169842987008_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Public Relations")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/42397474_1714519252009736_6568588356752506880_n.png").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Meta Morph")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884329/51570590_1069208709941591_7496207847085047808_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("EXE")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884340/this.png").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Organisation")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1552884330/14731254_611982775668673_1456822988139869385_n.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Robotics Society")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1553080766/robotics.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Design N Deco")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1553082685/dnd.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Finance")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1553083248/finanace.jpg").into(holder.image);
        }
        else if (gallery.get(position).getNameClub().equals("Technical Club")) {
            Picasso.get().load("https://res.cloudinary.com/dmt9ntsjm/image/upload/v1553083254/technical.jpg").into(holder.image);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Events.class);
                intent.putExtra("name",departmentalClubs.getNameClub());
                intent.putExtra("id",departmentalClubs.getClubId());
                intent.putExtra("info",departmentalClubs.getInfo());
                intent.putExtra("logo",departmentalClubs.getLogo());
                activity.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView team;
        ImageView image;
        RelativeLayout relativeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            team = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }


}
