package com.example.misha.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.misha.R;
import com.example.misha.data.Priority;
import com.example.misha.data.Task;
import com.example.misha.database.DbManager;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddEditTaskFragment extends Fragment {
    private EditText titleEditText, descriptionEditText;
    private MaterialButton dateButton, timeButton, saveButton;
    private DbManager dbManager;
    private Calendar selectedDateTime = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_task, container, false);

        titleEditText = view.findViewById(R.id.title_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        dateButton = view.findViewById(R.id.date_button);
        timeButton = view.findViewById(R.id.time_button);
        saveButton = view.findViewById(R.id.save_button);

        dbManager = new DbManager(getContext());
        dbManager.openDb();

        updateDateTimeButtons();

        dateButton.setOnClickListener(v -> showDatePicker());
        timeButton.setOnClickListener(v -> showTimePicker());
        saveButton.setOnClickListener(v -> saveTask());

        return view;
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDateTime.set(year, month, dayOfMonth);
                        updateDateTimeButtons();
                    }
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);
                        updateDateTimeButtons();
                    }
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    private void updateDateTimeButtons() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        dateButton.setText(dateFormat.format(selectedDateTime.getTime()));
        timeButton.setText(timeFormat.format(selectedDateTime.getTime()));
    }

    private void saveTask() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty()) {
            titleEditText.setError("Введите название задачи");
            return;
        }

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(System.currentTimeMillis());
        task.setDueDate(selectedDateTime.getTimeInMillis());
        task.setPriority(Priority.MEDIUM);
        task.setCompleted(false);

        dbManager.insertTask(task);

        // Возвращаемся назад
        requireActivity().onBackPressed();
    }
}