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

import org.osmdroid.util.GeoPoint;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectListBinding;
import de.cau.inf.se.sopro.databinding.FragmentSubprojectListBinding;
import de.cau.inf.se.sopro.model.GeoData;
import de.cau.inf.se.sopro.model.SubprojectBaseInfoItem;
import de.cau.inf.se.sopro.ui.home.MapFragment;
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

        // initialize the map
        MapFragment map = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_in_subproject_list);

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

            // also, insert pointers into the map
            // for that, first create a list of all GeoData-objects from the SubprojectBaseInfoItems
            GeoData[] subprojectGeoData = new GeoData[subprojects.size()];
            for (int i = 0; i < subprojects.size(); i++) {
                subprojectGeoData[i] = subprojects.get(i).getSubprojectGeoData();
            }
            // then, set up the map with these locations
            map.setUp(12, new GeoPoint(54.3232927f,10.1227652f), subprojectGeoData);
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
