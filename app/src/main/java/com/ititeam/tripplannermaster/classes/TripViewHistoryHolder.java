package com.ititeam.tripplannermaster.classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.ititeam.tripplannermaster.R;


/**
 * Created by Hesham Kadry on 3/26/2018.
 */

public class TripViewHistoryHolder extends RecyclerView.ViewHolder{

    public SwipeLayout swipeLayout2;
    public TextView Name;
    public TextView EmailId;
    public TextView Delete;
    public TextView Edit;
    //public TextView Share;
    //public TextView startDate;
    public ImageButton btnLocation;
    public ImageView imgViewIcon;

    public TripViewHistoryHolder(View itemView) {
        super(itemView);

        swipeLayout2 =  itemView.findViewById(R.id.swipe);
        //Share = itemView.findViewById(R.id.Share);
        Name = itemView.findViewById(R.id.Name);
        EmailId = itemView.findViewById(R.id.EmailId);
        Delete = itemView.findViewById(R.id.Delete);
        Edit = itemView.findViewById(R.id.Edit);
        //startDate = itemView.findViewById(R.id.textView2);
        btnLocation = itemView.findViewById(R.id.btnLocation);
        imgViewIcon = itemView.findViewById(R.id.SwipLayout_ImageView);

    }
}
