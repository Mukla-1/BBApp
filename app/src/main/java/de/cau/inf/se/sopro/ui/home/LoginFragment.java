package de.cau.inf.se.sopro.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.LoginActivity;
import de.cau.inf.se.sopro.MainActivity;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.ProjectBaseInfoItem;
import de.cau.inf.se.sopro.network.WebConfiguration;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    //private FragmentHomeBinding binding;
    private NavController navController;


    Button button_login;
    Button button_register;
    EditText text_username;
    EditText text_password;
    TextView text_hint;
    ImageButton button_settings;

    private String un = "";
    private String pw = "";


    private ApiViewModel  dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // I used this way of inflating because it actually works
        View view2 = inflater.inflate(R.layout.fragment_login,container,false);
        // Api stuff setup
        dashboardViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        // on first create, set shared preferences in the ApiViewModel
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Persistence", Context.MODE_PRIVATE);
        ApiViewModel.setSharedPreferences(sharedPreferences);
        // then, read the persistent url and set it in the ApiViewModel
        String currentURL = sharedPreferences.getString("baseURL", "http://134.245.1.240:1502");
        dashboardViewModel.setCurrentURL(currentURL);
        // also, change it for the WebConfiguration
        WebConfiguration.changeUrl(currentURL);

        dashboardViewModel.get_loginValid().observe(getViewLifecycleOwner(),b -> onLoginUpdate(b));
        dashboardViewModel.get_userAddedSuccess().observe(getViewLifecycleOwner(),b -> onRegisterUpdate(b));


        // Connect to the Nav Controller
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_login);
        this.navController = navHostFragment.getNavController();


        // Connecting to Elements
        button_settings = view2.findViewById(R.id.settings_button);
        button_settings.setOnClickListener(view -> onSettingsButtonClick());
        button_login = view2.findViewById(R.id.button_login);
        button_login.setOnClickListener(view -> onLoginButtonClick());
        button_register = view2.findViewById(R.id.button_register);
        button_register.setOnClickListener(view -> onRegisterButtonClick());
        text_username = view2.findViewById(R.id.username);
        text_password = view2.findViewById(R.id.password);
        text_hint = view2.findViewById(R.id.output);


        return view2;

    }


    public void onLoginUpdate(Boolean success){

        if(success){
            switchActivity(un,pw);
        }else{
            text_hint.setText(R.string.login_error_message);
        }
    }

    public void onRegisterUpdate(Boolean success){
        if(success){
            ApiViewModel.setUsername(un);
            switchActivity(un,pw);
        }else{
            text_hint.setText(R.string.register_error_message);
        }
    }



    protected void onLoginButtonClick(){
        // Get User Information
        un = text_username.getText().toString();
        pw = text_password.getText().toString();
        // Check for Empty
        if(un.isEmpty() || pw.isEmpty()){
            text_hint.setText(R.string.empty_fields_error_message);
            return;
        }
        dashboardViewModel.validateLogin(un,pw);
    }

    protected void onRegisterButtonClick(){
        // Get User Information
        un = text_username.getText().toString();
        pw = text_password.getText().toString();
        // Check for Empty
        if(un.isEmpty() || pw.isEmpty()){
            text_hint.setText("Name und Passwort d??rfen nicht leer sein");
            return;
        }
        dashboardViewModel.addUser(un,pw);
    }

    // Switch to the Settings screen
    protected void onSettingsButtonClick(){
        Bundle payload = new Bundle();
        this.navController.navigate(R.id.action_login_to_settings, payload);
    }

    // Start the MainActivity and send it the Userdata
    private void switchActivity(String name,String password){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username",name);
        bundle.putString("password",password);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}