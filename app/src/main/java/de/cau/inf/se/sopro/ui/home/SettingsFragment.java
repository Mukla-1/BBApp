package de.cau.inf.se.sopro.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.LoginActivity;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.network.WebConfiguration;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    //private FragmentHomeBinding binding;
    private NavController navController;

    Button button_settings;
    Button button_apply;
    EditText url;
    TextView success;

    private ApiViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // For some reason this works, but not the outcommented one
        View view2 = inflater.inflate(R.layout.fragment_settings,container,false);

        // Connect to ApiViewModel and set up stuff
        dashboardViewModel = new ViewModelProvider(this).get(ApiViewModel.class);


        // Connect to the Nav Controller
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_login);
        this.navController = navHostFragment.getNavController();




        // Set up References and Functionality
        button_settings = view2.findViewById(R.id.button_return);
        button_settings.setOnClickListener(view -> onSettingsButtonClick());
        button_apply = view2.findViewById(R.id.button_apply);
        button_apply.setOnClickListener(view -> onApplyButtonClick());
        url = view2.findViewById(R.id.url);
        success = view2.findViewById(R.id.success_text);

        // Get old URL from the API thingy
        url.setText(dashboardViewModel.getCurrentURL());

        return view2;
    }

    protected void onSettingsButtonClick(){
        // Switch to the Login Screen
        Bundle payload = new Bundle();
        this.navController.navigate(R.id.action_settings_to_login, payload);


    }

    protected void onApplyButtonClick(){
        // Get Text from Field and Save URL
        String newRL = url.getText().toString();
        if(newRL.isEmpty()){
            success.setText("URL darf nicht leer sein!");
            return;
        }

        WebConfiguration.changeUrl(newRL);
        WebConfiguration._validUrl.observe(getViewLifecycleOwner(), flag -> {
            // Display Success or Failure message
            if(flag){
                success.setText("Neue URL wird benutzt.");
                // set new url in the view model, where it is saved persistently
                dashboardViewModel.setCurrentURL(newRL);
            }
            else{
                success.setText("URL funktioniert nicht.");
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}