package de.cau.inf.se.sopro.groupieItems;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.CommentInfoItem;

public class SecondLevelCommentGroupieItem extends Item<GroupieViewHolder> {

    private CommentInfoItem cii;

    public SecondLevelCommentGroupieItem(CommentInfoItem cii){this.cii = cii;}

    @Override
    public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
        //Textual contentholders
        TextView tvContent = viewHolder.itemView.findViewById(R.id.comment_text);
        TextView tvAuthor = viewHolder.itemView.findViewById(R.id.comment_author);
        TextView tvLikes = viewHolder.itemView.findViewById(R.id.amount_likes);
        TextView tvDislikes = viewHolder.itemView.findViewById(R.id.amount_dislikes);

        //Buttons
        ImageButton upvote = viewHolder.itemView.findViewById(R.id.upvoteButton);
        ImageButton downvote = viewHolder.itemView.findViewById(R.id.downvoteButton);

        //Bind the textViews
        tvContent.setText(cii.getCommentContent());
        tvAuthor.setText(cii.getCommentAuthor());
        //tvLikes.setText(cii.getCommentLikes());
        //tvDislikes.setText(cii.getCommentDislikes());


    }

    @Override
    public int getLayout() {return R.layout.sub_comment_item;}


}