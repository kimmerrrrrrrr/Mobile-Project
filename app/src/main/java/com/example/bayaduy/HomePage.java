package com.example.bayaduy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bayaduy.databinding.ActivityHomePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    private static final int GROUPS_FRAGMENT = R.id.groups;
    private static final int FRIENDS_FRAGMENT = R.id.friends;
    private static final int ACCOUNT_FRAGMENT = R.id.account;
    ActivityHomePageBinding binding;
    BottomNavigationView btmNavView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityHomePageBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            replaceFragment(new GroupsFragment());

            btmNavView = binding.btmNavView;

            btmNavView.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.groups) {
                    replaceFragment(new GroupsFragment());
                } else if (item.getItemId() == R.id.friends) {
                    replaceFragment(new FriendsFragment());
                } else if (item.getItemId() == R.id.account) {
                    replaceFragment(new AccountFragment());
                }
                return true;
            });
        }

        private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();
        }
    }
