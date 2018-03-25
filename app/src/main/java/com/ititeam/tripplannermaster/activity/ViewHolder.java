package com.ititeam.tripplannermaster.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ititeam.tripplannermaster.R;

/**
 * Created by body on 24/03/2018.
 */

public class ViewHolder {
    View converView;
    TextView Notebody;

    public ViewHolder(View v) {
        converView = v;

    }

    public TextView getheader() {
        if (Notebody == null) {
            Notebody = converView.findViewById(R.id.NoteItem);
        }
        return Notebody;
    }

}
