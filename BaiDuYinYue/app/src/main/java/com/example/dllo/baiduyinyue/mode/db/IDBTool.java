package com.example.dllo.baiduyinyue.mode.db;

import java.util.List;

/**
 * Created by Limiao on 16/7/27.
 * 封装增删改查方法的接口
 */
public interface IDBTool<T> {
    /**
     * 查询
     *
     * @param t
     */
    void insert(T t);

    /**
     * 按歌名查询
     *
     * @param songName
     * @return
     */
    List<T> queryBySongName(String songName);

    /**
     * 查询所有
     *
     * @return
     */
    List<T> queryAll();

    /**
     * 按歌名删除
     *
     * @param songName
     */
    void delBySongName(String songName);

}
