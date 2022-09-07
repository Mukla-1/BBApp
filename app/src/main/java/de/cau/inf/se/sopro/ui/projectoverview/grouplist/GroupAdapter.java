package de.cau.inf.se.sopro.ui.projectoverview.grouplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.GroupBaseInfoItem;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.ui.projectoverview.headinglist.HeadingAdapter;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder> {

    // the headings of the recycler view are saved as HeadingBaseInfoItems
    private List<GroupBaseInfoItem> groups = new ArrayList<>();

    @NonNull
    @Override
    public GroupAdapter.GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_heading_list, parent, false);
        return new GroupAdapter.GroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupHolder holder, int position) {
        GroupBaseInfoItem currentGroup = groups.get(position);
        holder.textViewTitle.setText(currentGroup.getGroupName());
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

        private TextView textViewTitle;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
        }
    }
}
