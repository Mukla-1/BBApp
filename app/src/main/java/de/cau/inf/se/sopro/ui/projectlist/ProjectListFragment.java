package de.cau.inf.se.sopro.ui.projectlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectListBinding;
//import de.cau.inf.se.sopro.ui.home.HomeFragment;

/**
 * Fragment that shows a list of all projects from the database. Each project is displayed with its
 * name in a single list item, which are {@link ProjectItem}s.
 */
@AndroidEntryPoint
public class ProjectListFragment extends Fragment implements ProjectAdapter.ListItemClickListener {
    private NavController navController;
    private FragmentProjectListBinding binding;
    int i = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create a ViewModel for request handling
        ApiViewModel requestViewModel =
                new ViewModelProvider(requireActivity()).get(ApiViewModel.class);

        // inflate the layout for this fragment
        binding = FragmentProjectListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get access to recyclerView and set some layout settings
        RecyclerView recyclerView = binding.projectItemRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        // create the adapter for the recycler view with this fragment as a onClick-Listener
        ProjectAdapter adapter = new ProjectAdapter(this);
        recyclerView.setAdapter(adapter);

        // send a request to get all projects from the web API
        requestViewModel.getProjects();

        // request the current projects and observe for changes
        requestViewModel.get_projects().observe(getViewLifecycleOwner(), projects -> {
            // define what to do with the projects, which is to update the adapter
            adapter.setProjects(projects);
        });

        //Insert icon into homescreen
        String url = "http://134.245.1.240:1502/images/bbs-logo.png";
        ImageView ourIcon = root.findViewById(R.id.project_list_image_icon);
        Glide.with(this).load(url).into(ourIcon);


        // Connect to the Nav Controller
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        this.navController = navHostFragment.getNavController();

       
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
        // switch fragments
        this.navController.navigate(R.id.action_project_list_to_project_overview, payload);
    }
}