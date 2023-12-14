package com.example.myapplication;
import com.example.myapplication.TaskDbHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TaskDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddTaskScreen = findViewById(R.id.buttonAddTaskScreen);
        Button buttonTaskListScreen = findViewById(R.id.buttonTaskListScreen);
        Button buttonClearDatabase = findViewById(R.id.buttonClearDatabase);

         dbHelper = new TaskDbHelper(this);

        buttonAddTaskScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на экран добавления задачи
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        buttonTaskListScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на экран списка задач
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        buttonClearDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDatabase();
            }
        });
    }

    private void clearDatabase() {
        dbHelper.clearDatabase();
        // Можно добавить дополнительную логику или обновить интерфейс, если необходимо
    }

}
