package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button buttonAddTask;

    private TaskDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new TaskDbHelper(this);

        String dbPath = dbHelper.getDatabasePath();
        Log.d("DatabasePath", "Path: " + dbPath);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем введенные значения
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                // Сохраняем задачу в базе данных
                saveTask(title, description);

                // Закрываем активность
                finish();
            }
        });
    }

    private void saveTask(String title, String description) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description);

        long newRowId = database.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);

        // Проверяем, успешно ли была вставка
        if (newRowId != -1) {
            // Вставка прошла успешно
            // Можно добавить обработку успешного сохранения
        } else {
            // Вставка не удалась
            // Можно добавить обработку неудачного сохранения
        }

        database.close();
    }

}
