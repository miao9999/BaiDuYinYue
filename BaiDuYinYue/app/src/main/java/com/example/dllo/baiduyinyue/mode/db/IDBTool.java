package com.example.dllo.baiduyinyue.mode.db;

import java.util.List;

/**
 * Created by Limiao on 16/7/27.
 * 封装增删改查方法的接口
 */
public interface IDBTool<T>  {
    void insert(T t);
    void insertList(List<T> ts);
    List<T> queryBySongName(String songName);
    List<T> query(int count);
    List<T> queryAll();
    void delBySongName(String songName);

}
