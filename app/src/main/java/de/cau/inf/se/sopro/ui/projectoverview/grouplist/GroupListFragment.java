package de.cau.inf.se.sopro.ui.projectoverview.grouplist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentGroupListBinding;
import de.cau.inf.se.sopro.databinding.FragmentHeadingListBinding;
import de.cau.inf.se.sopro.groupieItems.GroupGroupieItem;
import de.cau.inf.se.sopro.groupieItems.HeadingGroupieItem;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.ui.projectoverview.headinglist.HeadingAdapter;

@AndroidEntryPoint
public class GroupListFragment extends Fragment {

    private FragmentGroupListBinding binding;
    private ApiViewModel apiViewModel;
    private NavController navController;
    private RecyclerView rv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get projectID from
        Long projectID = getArguments().getLong("projectID");

        // Connect to the Nav Controller
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        this.navController = navHostFragment.getNavController();

        // create a ViewModel for request handling
        apiViewModel = new ViewModelProvider(requireActivity()).get(ApiViewModel.class);

        // inflate the layout for this fragment
        binding = FragmentGroupListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        GroupAdapter ga = new GroupAdapter<GroupieViewHolder>();
        // get access to recyclerView and set some layout settings
        rv = binding.headingListRecyclerView;
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(ga);

        //Make the HTTP-call necessary to get the groups with their headings.
        apiViewModel.getGroupWithHeadings(projectID);
        apiViewModel.get_groupHeadingMap().observe(getViewLifecycleOwner(), pairs->{
            ga.clear();
            //ga.onDataSetInvalidated();
            //System.out.println(apiViewModel.get_groupHeadingMap().getValue());
            //For each over the GroupHeadings HashMap.
            pairs.forEach((group, headings)->{

                ExpandableGroup eg = new ExpandableGroup(new GroupGroupieItem(group));
                for(HeadingBaseInfoItem h:headings){
                    eg.add(new HeadingGroupieItem(h, navController));
                }
                ga.add(eg);
                eg.setExpanded(true);
            });
          /*  apiViewModel.get_groupHeadingMap().getValue().forEach((k,v) -> {
                System.out.println(k.getGroupName() + " " + v);
            });*/
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}