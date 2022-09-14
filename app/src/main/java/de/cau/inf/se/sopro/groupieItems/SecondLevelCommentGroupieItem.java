package de.cau.inf.se.sopro.groupieItems;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.CommentInfoItem;
import de.cau.inf.se.sopro.ui.comments.CommentSectionFragment;

public class SecondLevelCommentGroupieItem extends FirstLevelCommentGroupieItem {

    public SecondLevelCommentGroupieItem(CommentInfoItem cii, CommentSectionFragment parentFragment) {
        super(cii, parentFragment);
    }

    @Override
    public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
        super.bind(viewHolder, position);
    }

    @Override
    public int getLayout() {return R.layout.sub_comment_item;}


}