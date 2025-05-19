package com.example.misha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.R;
import com.example.misha.data.Task;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
private List<Task> tasks;

public TaskAdapter(List<Task> tasks) {
    this.tasks = tasks;
}

@NonNull
@Override
public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_task, parent, false);
    return new TaskViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    Task task = tasks.get(position);
    holder.bind(task);
}

@Override
public int getItemCount() {
    return tasks.size();
}

public void updateTasks(List<Task> newTasks) {
    tasks = newTasks;
    notifyDataSetChanged();
}

static class TaskViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView, dateTextView, descriptionTextView, priorityTextView;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        dateTextView = itemView.findViewById(R.id.date_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
        priorityTextView = itemView.findViewById(R.id.priority_text_view);
    }

    public void bind(Task task) {
        titleTextView.setText(task.getTitle());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        dateTextView.setText(format.format(task.getDueDate()));

        descriptionTextView.setText(task.getDescription());
        priorityTextView.setText(task.getPriority().name());
    }
}
}