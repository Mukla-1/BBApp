package de.cau.inf.se.sopro.ui.subprojectlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectListBinding;
import de.cau.inf.se.sopro.databinding.FragmentSubprojectListBinding;
import de.cau.inf.se.sopro.ui.projectlist.ProjectAdapter;
@AndroidEntryPoint
public class SubprojectListFragment extends Fragment implements SubprojectAdapter.ListItemClickListener {
    private NavController navController;
    private FragmentSubprojectListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Long headingID = getArguments().getLong("headingID");


        String headingName = getArguments().getString("headingName");


        // create a ViewModel for request handling
        ApiViewModel requestViewModel =
                new ViewModelProvider(getActivity()).get(ApiViewModel.class);

        // inflate the layout for this fragment
        binding = FragmentSubprojectListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // rename heading title
        binding.subprojectListTitle.setText(headingName);

        // get access to recyclerView and set some layout settings
        RecyclerView recyclerView = binding.subprojectItemRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        // create the adapter for the recycler view with this fragment as a onClick-Listener
        SubprojectAdapter adapter = new SubprojectAdapter(this);
        recyclerView.setAdapter(adapter);

        // send a request to get all subprojects for this headingID from the web API
        requestViewModel.getSubprojects(headingID);

        // request the current subprojects and observe for changes
        requestViewModel.get_subprojects().observe(getViewLifecycleOwner(), subprojects -> {
            // define what to do with the subprojects, which is to update the adapter
            adapter.setSubprojects(subprojects);
        });

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
        payload.putLong("subprojectID", itemID);

        this.navController.navigate(R.id.action_subproject_list_to_subproject_overview, payload);
    }
}
