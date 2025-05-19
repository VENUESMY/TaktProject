package com.example.misha.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misha.R;
import com.example.misha.adapters.TaskAdapter;
import com.example.misha.data.Task;
import com.example.misha.database.DbManager;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    private MaterialCalendarView calendarView;
    private RecyclerView tasksRecycler;
    private TaskAdapter adapter;
    private DbManager dbManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        tasksRecycler = view.findViewById(R.id.tasks_recycler);

        dbManager = new DbManager(getContext());
        dbManager.openDb();

        // Устанавливаем текущую дату
        Calendar today = Calendar.getInstance();
        calendarView.setSelectedDate(today);

        // Загружаем задачи на выбранную дату
        loadTasksForDay(today.getTimeInMillis());

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDay());
                loadTasksForDay(calendar.getTimeInMillis());
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    private void loadTasksForDay(long timestamp) {
        List<Task> tasks = dbManager.getTasksForDay(timestamp);
        adapter = new TaskAdapter(tasks);
        tasksRecycler.setAdapter(adapter);
    }
}