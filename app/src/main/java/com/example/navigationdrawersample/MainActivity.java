package com.example.navigationdrawersample;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawersample.fragment.AndroidFragment;
import com.example.navigationdrawersample.fragment.ArchiveFragment;
import com.example.navigationdrawersample.fragment.BackupFragment;
import com.google.android.material.navigation.NavigationView;

/**Add dependency
 * Create a menu, from this we populate the items into navigational drawer
 * remove the default actionBar and add custom toolbar , or set a separate theme without actionBar for the activity which contains navi_drawer
 * Implement the "NavigationView.OnNavigationItemSelectedListener"*/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup toolbar as our actionBar
        //Optional : we can set title and options in toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this); //passing instance into navigation item listener

        //Helps for the animation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close); //NOTE : its needed as String values.
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) { /**If implementing screen rotation only this check have to be implemented*/
        loadFirstFragment();
        }
    }

    private void loadFirstFragment() {
        /**Starting fragment
         * if its not given its just shows a blank page because none of the fragments selected*/
        displayFragment(new AndroidFragment());
        navigationView.setCheckedItem(R.id.nav_android);
        toolbar.setTitle("Android");
    }


    //Close the navigation bar on back button
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**For onClick on navigation drawer*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;

        switch (menuItem.getItemId()){
            case R.id.nav_android:
                selectedFragment=new AndroidFragment(); // create android fragment
                toolbar.setTitle("Android");
                break;
            case R.id.nav_archive:
                selectedFragment=new ArchiveFragment();
                toolbar.setTitle("Archive");
                break;
            case R.id.nav_backup:
                selectedFragment=new BackupFragment();
                toolbar.setTitle("Backup");
                break;
        }
        if (selectedFragment != null){
            displayFragment(selectedFragment);
            drawer.closeDrawer(GravityCompat.START); // close the drawer
            return true; //true -> show the selected item
        }
        return false;
    }

    /**To show the fragments*/
    private void displayFragment(Fragment fragment){
        getSupportFragmentManager() //to get FragmentManager object
                .beginTransaction() //to get FragmentTransaction object
                .replace(R.id.fragment_container, fragment) // Replacing container with homeFragment at starting
                .commit();
    }
}
