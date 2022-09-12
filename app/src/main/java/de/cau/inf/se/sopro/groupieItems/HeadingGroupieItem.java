package de.cau.inf.se.sopro.groupieItems;

import android.widget.TextView;

import androidx.annotation.NonNull;


import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;

public class HeadingGroupieItem extends Item<GroupieViewHolder> {

    private HeadingBaseInfoItem hi;

    public HeadingGroupieItem(HeadingBaseInfoItem hi){
        this.hi = hi;
    }
    @Override
    public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
        TextView tv = viewHolder.itemView.findViewById(R.id.textView2);
        tv.setTextSize(20);
        tv.setText(hi.getHeadingName());
    }

    @Override
    public int getLayout() {
        return R.layout.sample_layout;
    }

}
