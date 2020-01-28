package com.nith.appteam.nimbus.Activity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LENOVO on 04-03-2019.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    private int spaceleft,spaceright,spacetop,spacebottom;
    public SpaceItemDecoration(int spaceleft,int spaceright , int spacetop , int spacebottom) {
        this.spaceleft = spaceleft;
        this.spacebottom = spacebottom;
        this.spaceright = spaceright;
        this.spacetop = spacetop;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = spaceleft;
        outRect.right = spaceright;
        outRect.bottom = spacebottom;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = spacetop;
        }
//        else {
////            outRect.top = 0;
//        }
    }
}