package de.cau.inf.se.sopro.ui.projectoverview;

import static java.util.Arrays.copyOfRange;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
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

import org.osmdroid.util.GeoPoint;

import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectListBinding;
import de.cau.inf.se.sopro.databinding.FragmentProjectOverviewBinding;
import de.cau.inf.se.sopro.ui.home.MapFragment;

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



        // Inflate the layout for this fragment
        binding = FragmentProjectOverviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // TODO: Sehr Nötig
        Fragment frag = (Fragment) getChildFragmentManager().findFragmentById(R.id.group_list_fragment_container_view);
        Bundle payload = new Bundle();
        payload.putLong("projectID",projectID);
        frag.setArguments(payload);


        // get access to project title + project description text and project image view
        TextView projectTitleView = binding.textViewProjectTitle;
        TextView projectDescriptionView = binding.descriptionTextView;
        ImageView projectImageView = binding.imageViewProjectImage;

        // initialize the map fragment
        MapFragment map = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_in_project_overview);


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
            // insert pointers into the map
            map.setUp(12, new GeoPoint(54.3232927f,10.1227652f),projectInfoItem.getProjectGeoData());

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
                projectProgressBar.setMaxStateNumber(intToStateNumber(phases.length));
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
            projectProgressBar.setCurrentStateNumber(intToStateNumber(activePhase + 1));
        });
        return root;
    }

    /**
     * Converts an int into its corresponding StateNumber needed for
     * @param n the integer to convert
     * @return the corresponding StateNumber
     */
    private StateProgressBar.StateNumber intToStateNumber(int n) {
        switch (n) {
            case 1:
                return StateProgressBar.StateNumber.ONE;
            case 2:
                return StateProgressBar.StateNumber.TWO;
            case 3:
                return StateProgressBar.StateNumber.THREE;
            case 4:
                return StateProgressBar.StateNumber.FOUR;
            default:
                return StateProgressBar.StateNumber.FIVE;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}