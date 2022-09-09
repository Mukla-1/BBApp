package de.cau.inf.se.sopro.ui.projectoverview.grouplist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentGroupListBinding;
import de.cau.inf.se.sopro.databinding.FragmentHeadingListBinding;
import de.cau.inf.se.sopro.ui.projectoverview.headinglist.HeadingAdapter;


public class GroupListFragment extends Fragment {

    private FragmentGroupListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get projectID from
        Long projectID = savedInstanceState.getLong("projectID");

        // create a ViewModel for request handling
        ApiViewModel requestViewModel =
                new ViewModelProvider(requireActivity()).get(ApiViewModel.class);

        // inflate the layout for this fragment
        binding = FragmentGroupListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get access to recyclerView and set some layout settings
        RecyclerView recyclerView = binding.headingListRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        // create the adapter for the recycler view
        GroupAdapter adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);

        // send a request to get all groups from the web API
        requestViewModel.getGroups(projectID);

        // request the current groups and observe for changes
        requestViewModel.get_groups().observe(getViewLifecycleOwner(), groups -> {
            // define what to do with the groups, which is to update the adapter
            adapter.setGroups(groups);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}