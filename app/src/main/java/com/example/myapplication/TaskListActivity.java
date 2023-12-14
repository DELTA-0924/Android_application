package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity implements TaskAdapter.OnTaskLongClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);
        TaskDbHelper dbHelper = new TaskDbHelper(this);
        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Task> taskList = loadTasksFromDatabase();

        taskAdapter = new TaskAdapter(taskList,dbHelper,this);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.setOnTaskLongClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<Task> taskList = loadTasksFromDatabase();

            if (taskList != null) {
                outState.putParcelableArrayList("taskList", new ArrayList<>(taskList));
            }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Восстанавливаем список задач
        List<Task> restoredTaskList = savedInstanceState.getParcelableArrayList("taskList");
        if (restoredTaskList != null) {
            taskAdapter.updateTaskList(restoredTaskList);
            taskAdapter.notifyDataSetChanged();
        }
    }
    // Метод для создания тестовых задач
    private List<Task> loadTasksFromDatabase() {
        List<Task> tasks = new ArrayList<>();

        TaskDbHelper dbHelper = new TaskDbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TITLE,
                TaskContract.TaskEntry.COLUMN_DESCRIPTION,
                TaskContract.TaskEntry.COLUMN_COMPLETED
        };

        Cursor cursor = database.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    String title = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE));
                    String description = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION));
                    boolean completed = cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_COMPLETED)) == 1;
                    long id = cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID));
                    tasks.add(new Task(title, description, completed, id));
                }
                catch (Exception ex){
                    Log.d("Exception in cursor ","type"+ex.getMessage());
                }
            } while (cursor.moveToNext());
        }


        cursor.close();
        database.close();

        return tasks;
    }
    public void saveCheckBoxState(Task task) {
        Log.d("SaveCheckBox", "Update query: " + task.getTitle());
        Log.d("SaveCheckBox", "Update query: " + task.getId());

        // Сохранение состояния чекбокса в базе данных
        TaskDbHelper dbHelper = new TaskDbHelper(this);
        dbHelper.updateTask(task);
    }
    @Override
    public void onTaskLongClick(Task task) {
        // Обрабатываем долгое нажатие на элемент списка
        Log.d("TaskListActivity", "Long clicked on task: " + task.getTitle());
        TaskDbHelper dbHelper = new TaskDbHelper(this);
        dbHelper.deleteTask(task.getId());

        // Обновляем список задач в адаптере
        List<Task> updatedTaskList = dbHelper.loadTasksFromDatabase();
        taskAdapter.updateTaskList(updatedTaskList);
        taskAdapter.notifyDataSetChanged();
    }

}
