package com.example.misha;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.misha.data.Priority;
import com.example.misha.database.DbManager;
import com.example.misha.fragments.AddEditTaskFragment;
import com.example.misha.fragments.CalendarFragment;
import com.example.misha.fragments.TaskListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация базы данных
        dbManager = new DbManager(this);
        dbManager.openDb();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();
                if (id == R.id.nav_tasks)
                    selectedFragment = new TaskListFragment();
                if (id == R.id.nav_calendar)
                    selectedFragment = new CalendarFragment();
                if (id == R.id.nav_add)
                    selectedFragment = new AddEditTaskFragment();
                loadFragment(selectedFragment);
                return true;
            }
        });

        // Загружаем первый фрагмент по умолчанию
        if (savedInstanceState == null) {
            loadFragment(new TaskListFragment());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}