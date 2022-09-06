package de.cau.inf.se.sopro.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import de.cau.inf.se.sopro.LoginActivity;
import de.cau.inf.se.sopro.MainActivity;
import de.cau.inf.se.sopro.R;

public class LoginFragment extends Fragment {

    //private FragmentHomeBinding binding;
    private NavController navController;


    Button button_login;
    Button button_register;
    EditText text_username;
    EditText text_password;
    TextView text_hint;
    Button button_settings;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // I used this way of inflating because it actually works
        View view2 = inflater.inflate(R.layout.fragment_login,container,false);

        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();


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

    // Switch to the Settings screen
    protected void onSettingsButtonClick(){
        Bundle payload = new Bundle();
        this.navController.navigate(R.id.action_login_to_settings, payload);
    }

    protected  void onLoginButtonClick(){
        // Get User Information
        String n = text_username.getText().toString();
        String p = text_password.getText().toString();
        // Check for Empty
        if(n.isEmpty() || p.isEmpty()){
            text_hint.setText("Name und Passwort dürfen nicht leer sein");
            return;
        }
        // Ask ApiVM if the Login is valid
        ApiViewModel api = ((LoginActivity)getActivity()).getApi();
        if (api.validateLogin(n,p)){
            switchActivity(n,p);
        }else{
            text_hint.setText("Es geschah ein Fehlschlag. Bitte versuchen sie es erneut ");
        }
    }

    protected void onRegisterButtonClick(){
        // Get User Information
        String n = text_username.getText().toString();
        String p = text_password.getText().toString();
        // Check for Empty
        if(n.isEmpty() || p.isEmpty()){
            text_hint.setText("Name und Passwort dürfen nicht leer sein");
            return;
        }
        // Ask the ApiVM if the Login is valid
        ApiViewModel api = ((LoginActivity)getActivity()).getApi();
        if (api.validateLogin(n,p)){
            switchActivity(n,p);
        }else{
            text_hint.setText("Es geschah ein Fehlschlag. Bitte versuchen sie es erneut ");
        }

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
        binding = null;
    }
}