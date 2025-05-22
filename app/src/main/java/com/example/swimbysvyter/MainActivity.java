package com.example.swimbysvyter;

import static com.example.swimbysvyter.SwimApp.context;
import static com.example.swimbysvyter.SwimApp.customer;
import static com.example.swimbysvyter.SwimApp.masterKey;
import static com.example.swimbysvyter.SwimApp.questioner;
import static com.example.swimbysvyter.SwimApp.secFileShared;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.example.swimbysvyter.ui.auth.AuthActivity;
import com.example.swimbysvyter.ui.header.NavHeaderViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.example.swimbysvyter.databinding.ActivityMainBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavHeaderViewModel navHeaderViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences encSharedPreferences;

    @SuppressWarnings("deprecation")
    public MainActivity() {
        this.sharedPreferences = getSharedPreferences("loginPref",MODE_PRIVATE);
        try {
            this.encSharedPreferences = EncryptedSharedPreferences.create(context,secFileShared,masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
           Log.e("MainActivity","Constructor error: " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navHeaderViewModel = new ViewModelProvider(this).get(NavHeaderViewModel.class);

        updateHeader(navigationView,navHeaderViewModel);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorite, R.id.nav_theory, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        navigationView.setNavigationItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_exit){
                logout();
            }
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            drawer.closeDrawer(GravityCompat.START);
            return handled;
        });
  ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateHeader(NavigationView navigationView, NavHeaderViewModel navHeaderViewModel) {
        TextView nameText = navigationView.getHeaderView(0).findViewById(R.id.name_text);
        TextView emailText = navigationView.getHeaderView(0).findViewById(R.id.email_text);
        navHeaderViewModel.getName().observe(this,nameText::setText);
        navHeaderViewModel.getEmail().observe(this,emailText::setText);
    }

    public void logout(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        sharedPreferences.edit().clear().apply();
        encSharedPreferences.edit().clear().apply();
        customer = null;
        questioner = null;
        finish();
    }
}