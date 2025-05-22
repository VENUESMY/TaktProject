package com.example.misha.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.misha.R;
import com.example.misha.adapters.TaskAdapter;
import com.example.misha.data.Task;
import com.example.misha.database.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private DbManager dbManager;
    private FloatingActionButton createButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        createButton = view.findViewById(R.id.createActionButton);

        dbManager = new DbManager(getContext());
        dbManager.openDb();
        loadTasks();

        createButton.setOnClickListener(v ->{
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new AddEditTaskFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void loadTasks() {
        List<Task> tasks = dbManager.getAllTasks();
        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }
    private void openCreater(){

    }

}