package com.example.sinemaapp.classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sinemaapp.R;
import com.example.sinemaapp.fragments.Main_fragment;
import com.example.sinemaapp.fragments.Potok_fragment;
import com.example.sinemaapp.fragments.Profile_fragment;
import com.example.sinemaapp.fragments.Search_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
FragmentManager manager;
BottomNavigationView bnv;
Main_fragment main_fragment = new Main_fragment();
Search_fragment search_fragment= new Search_fragment();
Potok_fragment potok_fragment = new Potok_fragment();
Profile_fragment profile_fragment = new Profile_fragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        bnv = findViewById(R.id.bnv);
        manager.beginTransaction().replace(R.id.fragment,main_fragment).commit();
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main:
                        item.setChecked(true);
                        manager.beginTransaction().replace(R.id.fragment,main_fragment).commit();
                        break;
                    case R.id.search:
                        item.setChecked(true);
                        manager.beginTransaction().replace(R.id.fragment,search_fragment).commit();
                        break;
                    case R.id.rec:
                        item.setChecked(true);
                        manager.beginTransaction().replace(R.id.fragment,potok_fragment).commit();
                        break;
                    case R.id.profile:
                        item.setChecked(true);
                        manager.beginTransaction().replace(R.id.fragment,profile_fragment).commit();
                        break;
                }
                return false;
            }
        });
    }

}