package com.example.dllo.baiduyinyue.mode.bean;

/**
 * Created by Limiao on 16/7/22.
 * 我的--本地音乐--歌曲的实体类
 */
public class LocalMusicSongBean {
    private String name,singer,path;
    private long time,size;


    public LocalMusicSongBean() {

    }

    public LocalMusicSongBean(String name, String singer, long time, long size, String path) {
        this.name = name;
        this.singer = singer;
        this.time = time;
        this.size = size;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public LocalMusicSongBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getSinger() {
        return singer;
    }

    public LocalMusicSongBean setSinger(String singer) {
        this.singer = singer;
        return this;
    }

    public String getPath() {
        return path;
    }

    public LocalMusicSongBean setPath(String path) {
        this.path = path;
        return this;
    }

    public long getTime() {
        return time;
    }

    public LocalMusicSongBean setTime(long time) {
        this.time = time;
        return this;
    }

    public long getSize() {
        return size;
    }

    public LocalMusicSongBean setSize(long size) {
        this.size = size;
        return this;
    }
}
