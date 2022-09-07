package de.cau.inf.se.sopro.ui.projectoverview.headinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.model.ProjectBaseInfoItem;

public class HeadingAdapter extends RecyclerView.Adapter<HeadingAdapter.HeadingHolder> {

    // the headings of the recycler view are saved as HeadingBaseInfoItems
    private List<HeadingBaseInfoItem> headings = new ArrayList<>();
    // to listen for clicks on the recycler view, use an onClickListener
    private final ListItemClickListener onClickListener;

    public HeadingAdapter(ListItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HeadingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.heading_item, parent, false);
        return new HeadingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadingHolder holder, int position) {
        HeadingBaseInfoItem currentHeading = headings.get(position);
        holder.textViewTitle.setText(currentHeading.getHeadingName());
    }

    @Override
    public int getItemCount() {
        return headings.size();
    }

    public void setHeadings(List<HeadingBaseInfoItem> headings) {
        this.headings = headings;
        notifyDataSetChanged();
    }

    class HeadingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewTitle;

        public HeadingHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // find out which item has been clicked
            int position = getAdapterPosition();
            long headingID = headings.get(position).getHeadingID();
            // call onClickListener method with ID of the clicked item
            onClickListener.onListItemClick(headingID);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(long itemID);
    }
}
