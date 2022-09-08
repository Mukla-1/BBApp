package de.cau.inf.se.sopro.ui.projectlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectListBinding;
//import de.cau.inf.se.sopro.ui.home.HomeFragment;

/**
 * Fragment that shows a list of all projects from the database. Each project is displayed with its
 * name in a single list item, which are {@link ProjectItem}s.
 */
public class ProjectListFragment extends Fragment implements ProjectAdapter.ListItemClickListener {

    private FragmentProjectListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create a ViewModel for request handling
        ApiViewModel requestViewModel =
                new ViewModelProvider(this).get(ApiViewModel.class);

        // inflate the layout for this fragment
        binding = FragmentProjectListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get access to recyclerView and set some layout settings
        RecyclerView recyclerView = binding.projectFragmentRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        // create the adapter for the recycler view with this fragment as a onClick-Listener
        ProjectAdapter adapter = new ProjectAdapter(this);
        recyclerView.setAdapter(adapter);

        // request the current projects and observe for changes
        requestViewModel.get_projects().observe(getViewLifecycleOwner(), projects -> {
            // define what to do with the projects, which is to update the adapter
            adapter.setProjects(projects);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onListItemClick(long itemID) {
        Bundle payload = new Bundle();
        payload.putLong("projectID", itemID);
        // TODO: navigate to ProjectOverviewFragment with payload
    }
}