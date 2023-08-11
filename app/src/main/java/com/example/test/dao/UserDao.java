package com.example.test.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll(); //查询所有
    @Query("SELECT * FROM user WHERE phone = :phone AND password = :password")
    User findUser( String phone, String password ); //条件查询
    @Query("SELECT * FROM user WHERE phone = :phone")
    User findUserByPhone( String phone ); //通过手机号查询
    @Insert
    void insert(User user); //新增，由于主键自增，user不用设置uid值
    @Delete
    void delete(User user); //删除（user要有uid值）
    @Query("DELETE FROM user")
    void deleteAll(); //删除所有
    @Update
    void update(User user); //修改（user要有uid值）
}