package de.cau.inf.se.sopro.ui.projectoverview;

import static java.util.Arrays.copyOfRange;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectListBinding;
import de.cau.inf.se.sopro.databinding.FragmentProjectOverviewBinding;

/**
 * Fragment that gives an overview to a project. This includes a title, picture, phase-overview,
 * a list of groups with headings and a map which shows all subprojects.
 */
@AndroidEntryPoint
public class ProjectOverviewFragment extends Fragment {

    private NavController navController;
    private FragmentProjectOverviewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Long projectID = this.getArguments().getLong("projectID");


        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        navController = navHostFragment.getNavController();


        // create a ViewModel for request handling
        ApiViewModel requestViewModel =
                new ViewModelProvider(requireActivity()).get(ApiViewModel.class);


        // TODO: nachher wieder entfernen WICHTIG!!!11!!1

    /*    binding.tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle payload = new Bundle();
                payload.putLong("headingID",11);
                payload.putString("headingName", " Hallo, Mads hier :D");
                NavHostFragment.findNavController(ProjectOverviewFragment.this)
                        .navigate(R.id.action_project_overview_to_subproject_list);
            }
        });*/

        // TODO: nachher wieder entfernen WICHTIG!!!11!!1



        // Inflate the layout for this fragment
        binding = FragmentProjectOverviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get access to project title + project description text and project image view
        TextView projectTitleView = binding.textViewProjectTitle;
        TextView projectDescriptionView = binding.descriptionTextView;
        ImageView projectImageView = binding.imageViewProjectImage;


        // send a request to get all project information from Web API
        requestViewModel.getProjectInfo(projectID);

        // request the current project information and observe for changes
        requestViewModel.get_projectInfo().observe(getViewLifecycleOwner(), projectInfoItem -> {
            // define what to do with the project info, which is to set the project title ...
            String projectTitle = projectInfoItem.getProjectName();
            if (projectTitle != null) {
                projectTitleView.setText(projectTitle);
            }
            // ... and description
            String projectDescription = projectInfoItem.getProjectDescription();
            if (projectDescription != null) {
                projectTitleView.setText(projectDescription);
            }
            // ... and picture
            String pictureURL = projectInfoItem.getProjectPictureURL();
            if (pictureURL != null) {
                Glide.with(this).load(pictureURL).into(projectImageView);

            }
            // TODO: Nadeln auf Map einfügen (GeoData[] aus projectInfoItem)
        });

        // get access to the progress bar
        StateProgressBar projectProgressBar = binding.stateProgressBarProject;

        // send a request to get all phase information from Web API
        requestViewModel.getPhaseInfo(projectID);

        // request the current phase information and observe for changes
        requestViewModel.get_phaseInfo().observe(getViewLifecycleOwner(), phaseInfoItem -> {
            // define what to do with the phase info, which is to update the progress bar
            // update phase names and amount
            String[] phases = phaseInfoItem.getPhases();
            if (phases != null) {
                // progress bar can only show 5 stages, so check if there are too many
                if (phases.length > 5) {
                    // TODO: implement better option then just cutting off the rest
                    // if so, cut off the rest

                    phases = Arrays.copyOfRange(phases, 0, 5);
                }
                projectProgressBar.setStateDescriptionData(phases);
            }
            // update active phase
            int activePhase = phaseInfoItem.getActivePhase();
            // phase info can only show 5 phases, index 4 is phase 5
            if (activePhase > 4) {
                // TODO: implement better option
                activePhase = 4;
            }
            // set active phase
            switch (activePhase) {
                case 0:
                    projectProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                    break;
                case 1:
                    projectProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                    break;
                case 2:
                    projectProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                    break;
                case 3:
                    projectProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                    break;
                case 4:
                    projectProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                    break;
                default:
                    // should not happen anyways
                    projectProgressBar.setAllStatesCompleted(true);
                    break;
            }
        });
        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}