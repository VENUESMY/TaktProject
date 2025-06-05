//package com.example.misha.fragments;
//
//import static android.content.Context.MODE_PRIVATE;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.appcompat.widget.SwitchCompat;
//import androidx.fragment.app.Fragment;
//
//import android.preference.PreferenceManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Switch;
//
//import com.example.misha.R;
//
//public class SettingsFragment extends Fragment {
//    private SwitchCompat darkThemeSwitch;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        darkThemeSwitch = view.findViewById(R.id.darkThemeSwitch);
//
//        darkThemeSwitch.setChecked(isDarkTheme);
//
//        darkThemeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            // Применяем тему
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//
//            // Если нужно применить изменения без перезагрузки активити
//            requireActivity().recreate();
//        });
//    }
//
//}