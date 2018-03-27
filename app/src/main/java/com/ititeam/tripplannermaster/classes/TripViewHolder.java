package com.ititeam.tripplannermaster.classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.ititeam.tripplannermaster.R;

import org.w3c.dom.Text;

/**
 * Created by Hesham Kadry on 3/19/2018.
 */

public class TripViewHolder extends RecyclerView.ViewHolder
{
    /*
    public ImageView imgViewIcon;
    public Button btnStart;
    public TextView tvTitle , tvDescription;

    public TripViewHolder(View itemView) {
        super(itemView);
        imgViewIcon = itemView.findViewById(R.id.imageView);
        btnStart = itemView.findViewById(R.id.Button_Start);
        tvTitle = itemView.findViewById(R.id.TextView_Title);
        tvDescription = itemView.findViewById(R.id.TextView_Desc);
    }

*/


    public SwipeLayout swipeLayout;
    public TextView Name;
    public TextView EmailId;
    public TextView Delete;
    public TextView Edit;
    public TextView Share;
    public ImageButton btnLocation;
    public ImageView imgViewIcon;


    public TripViewHolder(View itemView) {
        super(itemView);

        swipeLayout = itemView.findViewById(R.id.swipe);
        Name = itemView.findViewById(R.id.Name);
        EmailId = itemView.findViewById(R.id.EmailId);
        Delete = itemView.findViewById(R.id.Delete);
        Edit = itemView.findViewById(R.id.Edit);
        Share = itemView.findViewById(R.id.Share);
        btnLocation = itemView.findViewById(R.id.btnLocation);
        imgViewIcon = itemView.findViewById(R.id.SwipLayout_ImageView);
    }

}