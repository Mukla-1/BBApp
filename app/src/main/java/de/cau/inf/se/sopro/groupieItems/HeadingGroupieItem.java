package de.cau.inf.se.sopro.groupieItems;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;

public class HeadingGroupieItem extends Item<GroupieViewHolder> {

    private HeadingBaseInfoItem hi;
    private NavController navController;

    public HeadingGroupieItem(HeadingBaseInfoItem hi, NavController navController){
        this.hi = hi;
        this.navController = navController;

    }
    @Override
    public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
        TextView tv = viewHolder.itemView.findViewById(R.id.textView2);
        tv.setTextSize(20);
        tv.setText(hi.getHeadingName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle payload = new Bundle();
                payload.putLong("headingID", hi.getHeadingID());
                payload.putString("headingName", hi.getHeadingName());
                // switch fragments
                navController.navigate(R.id.action_project_overview_to_subproject_list, payload);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.sample_layout;
    }

}
