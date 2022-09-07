package de.cau.inf.se.sopro.ui.projectoverview.headinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.databinding.FragmentHeadingListBinding;

/**
 * Fragment that shows a list of all headings of a group from the database.
 * Each heading is displayed with its name in a single list item, which are {@link HeadingItem}s.
 */
@AndroidEntryPoint
public class HeadingListFragment extends Fragment implements HeadingAdapter.ListItemClickListener {

    private FragmentHeadingListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get groupID from Bundle
        Long groupID = savedInstanceState.getLong("groupID");

        // create a ViewModel for request handling
        ApiViewModel requestViewModel =
                new ViewModelProvider(this).get(ApiViewModel.class);

        // inflate the layout for this fragment
        binding = FragmentHeadingListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get access to recyclerView and set some layout settings
        RecyclerView recyclerView = binding.headingItemRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        // create the adapter for the recycler view with this fragment as a onClick-Listener
        HeadingAdapter adapter = new HeadingAdapter(this);
        recyclerView.setAdapter(adapter);

        // send a request to get all projects from the web API
        requestViewModel.getHeadings(groupID);

        // request the current projects and observe for changes
        requestViewModel.get_headings().observe(getViewLifecycleOwner(), headings -> {
            // define what to do with the projects, which is to update the adapter
            adapter.setHeadings(headings);
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
        payload.putLong("heading", itemID);
        // TODO: navigate to SubprojectListFragment with payload
    }
}