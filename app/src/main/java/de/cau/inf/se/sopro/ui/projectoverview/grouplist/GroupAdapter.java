package de.cau.inf.se.sopro.ui.projectoverview.grouplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.GroupBaseInfoItem;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.ui.projectlist.ProjectAdapter;
import de.cau.inf.se.sopro.ui.projectoverview.headinglist.HeadingAdapter;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder> {

    // the headings of the recycler view are saved as HeadingBaseInfoItems
    private List<GroupBaseInfoItem> groups = new ArrayList<>();

    public GroupAdapter() {

    }

    @NonNull
    @Override
    public GroupAdapter.GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_heading_list, parent, false);
        return new GroupAdapter.GroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupHolder holder, int position) {
        GroupBaseInfoItem currentGroup = groups.get(position);
        holder.textViewGroupName.setText(currentGroup.getGroupName());

        // now, create the inner recycler view
        // for that, first set up a layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.headingItemRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        // define how many heading items should be prefetched in nested recycler view
        // TODO layoutManager.setInitialPrefetchItemCount(currentGroup.ge);

        // create instance of heading adapter
        // HeadingAdapter headingAdapter = new HeadingAdapter(context.ge)
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void setGroups(List<GroupBaseInfoItem> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    class GroupHolder extends RecyclerView.ViewHolder {

        private TextView textViewGroupName;
        private RecyclerView headingItemRecyclerView;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            textViewGroupName = itemView.findViewById(R.id.heading_list_title);
            headingItemRecyclerView = itemView.findViewById(R.id.heading_item_recycler_view);
        }
    }
}
