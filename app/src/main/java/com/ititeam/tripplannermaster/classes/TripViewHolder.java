package com.ititeam.tripplannermaster.classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ititeam.tripplannermaster.R;

/**
 * Created by Hesham Kadry on 3/19/2018.
 */

public class TripViewHolder extends RecyclerView.ViewHolder
{
    ImageView imgViewIcon;
    Button btnStart;
    TextView tvTitle;

    public TripViewHolder(View itemView) {
        super(itemView);
        imgViewIcon = itemView.findViewById(R.id.imageView);
        btnStart = itemView.findViewById(R.id.Button_Start);
        tvTitle = itemView.findViewById(R.id.TextView_Title);
    }

}