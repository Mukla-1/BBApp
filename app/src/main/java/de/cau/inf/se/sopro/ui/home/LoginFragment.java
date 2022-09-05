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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import de.cau.inf.se.sopro.MainActivity;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.databinding.FragmentHomeBinding;

public class LoginFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;


    Button button_login;
    Button button_register;
    EditText username_text;
    EditText password_text;
    TextView hint_text;
    Button settings_button;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view2 = inflater.inflate(R.layout.login_home,container,false);

        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();


        // Connect to the Nav Controller
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_login);
        this.navController = navHostFragment.getNavController();




        // Connecting to Elements
        settings_button = view2.findViewById(R.id.settings_button);
        settings_button.setOnClickListener(view -> onSettingsButtonClick());
        button_login = view2.findViewById(R.id.button_login);
        button_login.setOnClickListener(view -> onLoginButtonClick());
        button_register = view2.findViewById(R.id.button_register);
        button_register.setOnClickListener(view -> onRegisterButtonClick());
        username_text = view2.findViewById(R.id.username);
        password_text = view2.findViewById(R.id.password);
        hint_text = view2.findViewById(R.id.output);



        return view2;
    }
    protected void onSettingsButtonClick(){
        Bundle payload = new Bundle();
        this.navController.navigate(R.id.action_login_to_settings, payload);
    }

    protected  void onLoginButtonClick(){
        // Get User Information
        String n = username_text.getText().toString();
        String p = password_text.getText().toString();
        // Check for Empty
        if(n.isEmpty() || p.isEmpty()){
            hint_text.setText("Name und Passwort dürfen nicht leer sein");
            return;
        }
        //ApiViewModel api = getActivity().getApi();
        //if (api.validateLogin(n,p)){
        //    switchActivity(n,p);
        //}else{
        //    hint_text.setText("Es geschah ein Fehlschlag. Bitte versuchen sie es erneut ");
        //}
    }

    protected void onRegisterButtonClick(){
        // Get User Information
        String n = username_text.getText().toString();
        String p = password_text.getText().toString();
        // Check for Empty
        if(n.isEmpty() || p.isEmpty()){
            hint_text.setText("Name und Passwort dürfen nicht leer sein");
            return;
        }
        //ApiViewModel api = getActivity().getApi();
        //if (api.addUser(n,p)){
        //    switchActivity(n,p);
        //}else{
        //    hint_text.setText("Es geschah ein Fehlschlag. Bitte versuchen sie es erneut ");
        //}

    }

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