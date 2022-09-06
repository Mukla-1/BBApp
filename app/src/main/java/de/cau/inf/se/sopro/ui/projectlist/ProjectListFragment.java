package de.cau.inf.se.sopro.ui.projectlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectListBinding;

/**
 * Fragment that shows a list of all projects from the database. Each project is displayed with its
 * name in a single list item, which are {@link ProjectFragment}s.
 */
public class ProjectListFragment extends Fragment {

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

        // request the current projects and observe for changes
        requestViewModel.get_projects().observe(getViewLifecycleOwner(), projects -> {
            // define what to do with the projects
            // https://www.youtube.com/watch?v=reSPN7mgshI
        });

        return root;
    }
}