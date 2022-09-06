package de.cau.inf.se.sopro.ui.projectlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentProjectBinding;

/**
 * Fragment shown in {@link ProjectListFragment}. For each project found in the database,
 * one of these ProjectFragments will be shown in the list.
 */
public class ProjectFragment extends Fragment {

    private FragmentProjectBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }
}