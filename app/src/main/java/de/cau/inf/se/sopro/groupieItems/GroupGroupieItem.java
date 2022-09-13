package de.cau.inf.se.sopro.groupieItems;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.GroupBaseInfoItem;

public class GroupGroupieItem extends Item<GroupieViewHolder> implements ExpandableItem {

    private ExpandableGroup eg;
    private GroupBaseInfoItem gb;

    public GroupGroupieItem(GroupBaseInfoItem gb){
        this.gb = gb;
    }

    @Override
    public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
        TextView tv = viewHolder.itemView.findViewById(R.id.textView2);
        tv.setTextSize(40);
        tv.setTextColor(-16777216);
        tv.setText(gb.getGroupName());
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                eg.onToggleExpanded();
                return true;
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.sample_layout;
    }

    @Override
    public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
        this.eg = onToggleListener;
    }
}
