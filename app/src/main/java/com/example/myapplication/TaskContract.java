package com.example.myapplication;

import android.provider.BaseColumns;

public class TaskContract {

    private TaskContract() {
        // Конструктор класса приватный, чтобы предотвратить создание экземпляров этого класса.
    }

    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_COMPLETED = "completed";
    }
}
