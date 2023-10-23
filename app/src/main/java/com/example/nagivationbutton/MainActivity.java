package com.example.nagivationbutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;


import com.example.nagivationbutton.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    private final int HOME_ID = R.id.home;

    FloatingActionButton ButtonAdd;

    private final int Search_ID = R.id.Search;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == HOME_ID) {
                replaceFragment(new HomeFragment());
            }
            else if (itemId == Search_ID) {
                replaceFragment(new SearchFragment());
            }
            return true;
        });

        replaceFragment(new HomeFragment());

        ButtonAdd = findViewById(R.id.fab);

        ButtonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddingHiking.class);
                startActivity(intent);
            }
        });

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}