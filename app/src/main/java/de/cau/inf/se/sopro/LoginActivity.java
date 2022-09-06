package de.cau.inf.se.sopro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.databinding.ActivityMainBinding;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the Correct xml File
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    // Used by Fragments to access a/the ApiViewModel, depends on what Mads did in his Method
    public ApiViewModel getApi(){
        return new ApiViewModel();
    }
}