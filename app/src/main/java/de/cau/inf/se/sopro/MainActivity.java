package de.cau.inf.se.sopro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.databinding.ActivityMainBinding;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    Button home,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        home = (Button) findViewById(R.id.button_Home);
        logout = (Button) findViewById(R.id.button_Logout);
        Button project = (Button) findViewById(R.id.button_project);




    }
    // called when the logout button is pressed
    public void logout(View view){

    }

    //called when the Home button is pressed
    public void goHome(View view){

    }

    //called when the project button is pressed
    public void showProject(View view){

    }
}