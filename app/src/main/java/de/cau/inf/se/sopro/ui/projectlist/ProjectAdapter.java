package de.cau.inf.se.sopro.ui.projectlist;

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

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {

    // the projects of the recycler view are saved as ProjectBaseInfoItems
    private List<ProjectBaseInfoItem> projects = new ArrayList<>();
    // to listen for clicks on the recycler view, use an onClickListener
    private final ListItemClickListener onClickListener;

    public ProjectAdapter(ListItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ProjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        ProjectBaseInfoItem currentProject = projects.get(position);
        holder.textViewTitle.setText(currentProject.getProjectName());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setProjects(List<ProjectBaseInfoItem> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }

    class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewTitle;

        public ProjectHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // find out which item has been clicked
            int position = getAdapterPosition();
            long projectID = projects.get(position).getProjectID();
            // call onClickListener method with ID of the clicked item
            onClickListener.onListItemClick(projectID);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(long itemID);
    }
}
