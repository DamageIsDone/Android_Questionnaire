package com.example.test.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.test.dao.UserDao;
import com.example.test.entity.User;

@Database(entities = { User.class }, version = 1, exportSchema=false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}