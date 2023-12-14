package com.example.myapplication;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private  List<Task> taskList;
    private TaskDbHelper dbHelper;
    private Context context;
    private OnTaskLongClickListener onTaskLongClickListener;
    public TaskAdapter(List<Task> taskList, TaskDbHelper dbHelper, Context context) {
        this.taskList = taskList;
        this.dbHelper = new TaskDbHelper(context);
        this.context=context;
    }
    public TaskAdapter() {
    }
    public List<Task> getTaskList() {
        return taskList;
    }
    public void updateTaskList(List<Task> newTaskList) {
        taskList.clear(); // Очищаем текущий список задач
        taskList.addAll(newTaskList); // Добавляем новый список задач
        notifyDataSetChanged(); // Обновляем RecyclerView
    }
    public interface OnTaskLongClickListener {
        void onTaskLongClick(Task task);
    }

    public void setOnTaskLongClickListener(OnTaskLongClickListener listener) {
        this.onTaskLongClickListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = taskList.get(position);
        holder.checkBoxTask.setChecked(currentTask.isCompleted());
        holder.textViewTitle.setText(currentTask.getTitle());
        holder.textViewDescription.setText(currentTask.getDescription());

        holder.itemView.setOnLongClickListener(v -> {
            if (onTaskLongClickListener != null) {
                onTaskLongClickListener.onTaskLongClick(currentTask); // Передаем текущую задачу при долгом нажатии
                return true;
            }
            return false;
        });
        holder.checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.setCompleted(isChecked);
            ((TaskListActivity) context).saveCheckBoxState(currentTask);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTask;
        TextView textViewTitle;
        TextView textViewDescription;

        public TaskViewHolder(@NonNull View itemView ) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);


        }

    }


}
