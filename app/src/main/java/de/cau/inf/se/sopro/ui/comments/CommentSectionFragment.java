package de.cau.inf.se.sopro.ui.comments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentCommentSectionBinding;
import de.cau.inf.se.sopro.databinding.FragmentGroupListBinding;
import de.cau.inf.se.sopro.groupieItems.FirstLevelCommentGroupieItem;
import de.cau.inf.se.sopro.groupieItems.GroupGroupieItem;
import de.cau.inf.se.sopro.groupieItems.HeadingGroupieItem;
import de.cau.inf.se.sopro.groupieItems.SecondLevelCommentGroupieItem;
import de.cau.inf.se.sopro.model.CommentInfoItem;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.ui.home.CommentingFragment;

@AndroidEntryPoint
public class CommentSectionFragment extends Fragment {

    private FragmentCommentSectionBinding binding;
    private RecyclerView rv;
    private ApiViewModel apiViewModel;
    private CommentingFragment cmmt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout for this fragment
        binding = FragmentCommentSectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        GroupAdapter ga = new GroupAdapter<GroupieViewHolder>();

        // get access to recyclerView and set some layout settings
        rv = binding.commentListRecyclerView;
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(ga);
        apiViewModel = new ViewModelProvider(requireActivity()).get(ApiViewModel.class);
        String username = apiViewModel.getUsername();

        apiViewModel.getCommentsWithSubcomments(getArguments().getLong("subprojectID"), username);

        apiViewModel.get_commentSubcommentsMap().observe(getViewLifecycleOwner(), pairs->{
            ga.clear();

            //System.out.println(apiViewModel.get_groupHeadingMap().getValue());
            //For each over the GroupHeadings HashMap.
            pairs.forEach((comment, subcomments)->{

                ExpandableGroup eg = new ExpandableGroup(new FirstLevelCommentGroupieItem(comment, this));
                for(CommentInfoItem sub: subcomments){
                    eg.add(new SecondLevelCommentGroupieItem(sub, this));
                }
                ga.add(eg);
                eg.setExpanded(true);
            });
          /*  apiViewModel.get_groupHeadingMap().getValue().forEach((k,v) -> {
                System.out.println(k.getGroupName() + " " + v);
            });*/
        });

        // Setup the Commenting Fragment
        cmmt = (CommentingFragment) getChildFragmentManager().findFragmentById(R.id.subproject_commenting);
        Bundle b = new Bundle();
        b.putLong("subprojectID",getArguments().getLong("subprojectID"));
        cmmt.setArguments(b);


        // Inflate the layout for this fragment
        return root;
    }

    public void setUpReply(String author,Long id){
        cmmt.setReplyComment(author, id);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}