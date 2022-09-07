package de.cau.inf.se.sopro.ui.projectoverview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectOverviewBinding;

/**
 * Fragment that gives an overview to a project. This includes a title, picture, phase-overview,
 * a list of groups with headings and a map which shows all subprojects.
 */
public class ProjectOverviewFragment extends Fragment {

    private FragmentProjectOverviewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_overview, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}