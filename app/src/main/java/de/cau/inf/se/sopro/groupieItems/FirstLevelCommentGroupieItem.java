package de.cau.inf.se.sopro.groupieItems;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.CommentInfoItem;
import de.cau.inf.se.sopro.ui.comments.CommentSectionFragment;

public class FirstLevelCommentGroupieItem extends Item<GroupieViewHolder> implements ExpandableItem {

    private CommentInfoItem cii;
    private ExpandableGroup eg;
    private CommentSectionFragment parentFragment;

    // ViewModel for request handling
    ApiViewModel apiViewModel;

    public FirstLevelCommentGroupieItem(CommentInfoItem cii, CommentSectionFragment parentFragment){
        this.cii = cii;
        this.parentFragment = parentFragment;
        this.apiViewModel = new ViewModelProvider(parentFragment.requireActivity()).get(ApiViewModel.class);

    }

    @Override
    public void bind(@NonNull GroupieViewHolder viewHolder, int position) {



        //Textual contentholders
        TextView tvContent = viewHolder.itemView.findViewById(R.id.comment_text);
        TextView tvAuthor = viewHolder.itemView.findViewById(R.id.comment_author);
        TextView tvLikes = viewHolder.itemView.findViewById(R.id.amount_likes);
        TextView tvDislikes = viewHolder.itemView.findViewById(R.id.amount_dislikes);

        //Buttons
        ImageButton upvoteBtn = viewHolder.itemView.findViewById(R.id.upvoteButton);
        ImageButton downvoteBtn = viewHolder.itemView.findViewById(R.id.downvoteButton);
        Button reply = viewHolder.itemView.findViewById(R.id.reply_button);

        // set colored icon buttons based on current vote status
        switch (cii.getCommentVote()) {
            case -1:
                // user downvoted this comment before
                upvoteBtn.setImageResource(R.drawable.ic_thumbs_up);
                downvoteBtn.setImageResource(R.drawable.ic_thumbs_down_red);
                break;
            case 0:
                // user didn't vote for this comment before
                upvoteBtn.setImageResource(R.drawable.ic_thumbs_up);
                downvoteBtn.setImageResource(R.drawable.ic_thumbs_down);
                break;
            case 1:
                // user upvoted this comment before
                upvoteBtn.setImageResource(R.drawable.ic_thumbs_up_green);
                downvoteBtn.setImageResource(R.drawable.ic_thumbs_down);
                break;
        }

        //Bind the textViews
        tvContent.setText(cii.getCommentContent());
        tvAuthor.setText(cii.getCommentAuthor());
        tvLikes.setText(String.valueOf(cii.getCommentLikes()));
        tvDislikes.setText(String.valueOf(cii.getCommentDislikes()));

        //Create the button listeners.
        if(getClass() == FirstLevelCommentGroupieItem.class) {
            reply.setOnClickListener(b -> {
                parentFragment.setUpReply(cii.getCommentAuthor(), cii.getCommentID());
            });
        }


        // Create button listener for upvote button
        upvoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // initialize required variables
                Long commentID = cii.getCommentID();
                int newLikes = 0;
                int newDislikes = 0;
                // send different vote requests to the web backend based on current vote status
                switch (cii.getCommentVote()) {
                    case -1:
                        // user downvoted this comment before, now it should switch to an upvote
                        // first, send backend request
                        // neutralize the vote first, then send upvote (backend reasons)
                        apiViewModel.voteComment(commentID, 0);
                        apiViewModel.voteComment(commentID, 1);

                        // second, change button icons and like counters
                        upvoteBtn.setImageResource(R.drawable.ic_thumbs_up_green);
                        downvoteBtn.setImageResource(R.drawable.ic_thumbs_down);
                        // change commentVote in cii for future button clicks
                        cii.setCommentVote(1);

                        // in cii and views: update like and dislike counter
                        newLikes = cii.getCommentLikes() + 1;
                        newDislikes = cii.getCommentDislikes() - 1;
                        cii.setCommentLikes(newLikes);
                        cii.setCommentDislikes(newDislikes);
                        tvLikes.setText(String.valueOf(newLikes));
                        tvDislikes.setText(String.valueOf(newDislikes));
                        break;
                    case 0:
                        // user didn't vote for this comment before, now it should switch to an upvote
                        // first, send backend request
                        apiViewModel.voteComment(commentID, 1);

                        // second, change button icons and like counters
                        upvoteBtn.setImageResource(R.drawable.ic_thumbs_up_green);
                        downvoteBtn.setImageResource(R.drawable.ic_thumbs_down);
                        // change commentVote in cii for future button clicks
                        cii.setCommentVote(1);

                        // in cii and view: update like counter
                        newLikes = cii.getCommentLikes() + 1;
                        cii.setCommentLikes(newLikes);
                        tvLikes.setText(String.valueOf(newLikes));
                        break;
                    case 1:
                        // user upvoted this comment before, now it should remove the vote
                        // first, send backend request
                        // neutralize the vote
                        apiViewModel.voteComment(commentID, 0);

                        // second, change button icons and like counters
                        upvoteBtn.setImageResource(R.drawable.ic_thumbs_up);
                        downvoteBtn.setImageResource(R.drawable.ic_thumbs_down);
                        // change commentVote in cii for future button clicks
                        cii.setCommentVote(0);

                        // in cii and view: update like counter
                        newLikes = cii.getCommentLikes() - 1;
                        cii.setCommentLikes(newLikes);
                        tvLikes.setText(String.valueOf(newLikes));
                        break;
                }
            }
        });

        // Create button listener for downvote button
        downvoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initialize required variables
                Long commentID = cii.getCommentID();
                int newLikes = 0;
                int newDislikes = 0;
                // send different vote requests to the web backend based on current vote status
                switch (cii.getCommentVote()) {
                    case -1:
                        // user downvoted this comment before, now it should remove the vote
                        // first, send backend request
                        // neutralize the vote
                        apiViewModel.voteComment(commentID, 0);

                        // second, change button icons and like counters
                        upvoteBtn.setImageResource(R.drawable.ic_thumbs_up);
                        downvoteBtn.setImageResource(R.drawable.ic_thumbs_down);
                        // change commentVote in cii for future button clicks
                        cii.setCommentVote(0);

                        // in cii and view: update dislike counter
                        newDislikes = cii.getCommentDislikes() - 1;
                        cii.setCommentDislikes(newDislikes);
                        tvDislikes.setText(String.valueOf(newDislikes));
                        break;
                    case 0:
                        // user didn't vote for this comment before, now it should switch to an downvote
                        // first, send backend request
                        apiViewModel.voteComment(commentID, -1);

                        // second, change button icons and like counters
                        upvoteBtn.setImageResource(R.drawable.ic_thumbs_up);
                        downvoteBtn.setImageResource(R.drawable.ic_thumbs_down_red);
                        // change commentVote in cii for future button clicks
                        cii.setCommentVote(-1);

                        // in cii and view: update dislike counter
                        newDislikes = cii.getCommentDislikes() + 1;
                        cii.setCommentDislikes(newDislikes);
                        tvDislikes.setText(String.valueOf(newDislikes));
                        break;
                    case 1:
                        // user upvoted this comment before, now it should switch to a downvote
                        // first, send backend request
                        // neutralize the vote first, then send downvote (backend reasons)
                        apiViewModel.voteComment(commentID, 0);
                        apiViewModel.voteComment(commentID, -1);

                        // second, change button icons and like counters
                        upvoteBtn.setImageResource(R.drawable.ic_thumbs_up);
                        downvoteBtn.setImageResource(R.drawable.ic_thumbs_down_red);
                        // change commentVote in cii for future button clicks
                        cii.setCommentVote(-1);

                        // in cii and views: update like and dislike counter
                        newLikes = cii.getCommentLikes() - 1;
                        newDislikes = cii.getCommentDislikes() + 1;
                        cii.setCommentLikes(newLikes);
                        cii.setCommentDislikes(newDislikes);
                        tvLikes.setText(String.valueOf(newLikes));
                        tvDislikes.setText(String.valueOf(newDislikes));
                        break;
                }
            }
        });


        // click expands the group. But only if we are First Layer, who wrote this code !?!?
        if(getClass() == FirstLevelCommentGroupieItem.class) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eg.onToggleExpanded();
                }
            });
        }
    }

    @Override
    public int getLayout() {return R.layout.comment_item;}

    @Override
    public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
        this.eg = onToggleListener;
    }
}
