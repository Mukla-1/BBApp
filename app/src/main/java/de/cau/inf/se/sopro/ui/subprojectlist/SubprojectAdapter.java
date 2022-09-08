package de.cau.inf.se.sopro.ui.subprojectlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.ProjectBaseInfoItem;
import de.cau.inf.se.sopro.model.SubprojectBaseInfoItem;
import de.cau.inf.se.sopro.ui.projectlist.ProjectAdapter;

public class SubprojectAdapter extends RecyclerView.Adapter<SubprojectAdapter.SubprojectHolder> {

    // the projects of the recycler view are saved as ProjectBaseInfoItems
    private List<SubprojectBaseInfoItem> subprojects = new ArrayList<>();
    // to listen for clicks on the recycler view, use an onClickListener
    private final SubprojectAdapter.ListItemClickListener onClickListener;

    public SubprojectAdapter(SubprojectAdapter.ListItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SubprojectAdapter.SubprojectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subproject_item, parent, false);
        return new SubprojectAdapter.SubprojectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubprojectAdapter.SubprojectHolder holder, int position) {
        SubprojectBaseInfoItem currentSubproject = subprojects.get(position);
        holder.textViewSubprojectName.setText(currentSubproject.getSubprojectName());
    }

    @Override
    public int getItemCount() {
        return subprojects.size();
    }

    public void setSubprojects(List<SubprojectBaseInfoItem> subprojects) {
        this.subprojects = subprojects;
        notifyDataSetChanged();
    }

    class SubprojectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewSubprojectName;

        public SubprojectHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubprojectName = itemView.findViewById(R.id.text_view_subproject_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // find out which item has been clicked
            int position = getAdapterPosition();
            long subprojectID = subprojects.get(position).getSubprojectID();
            // call onClickListener method with ID of the clicked item
            onClickListener.onListItemClick(subprojectID);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(long itemID);
    }
}
