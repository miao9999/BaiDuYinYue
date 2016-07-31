package com.example.dllo.baiduyinyue.mode.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Limiao on 16/7/22.
 * 我的--本地音乐--歌曲的实体类
 */
public class LocalMusicSongBean implements Parcelable{
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

    protected LocalMusicSongBean(Parcel in) {
        name = in.readString();
        singer = in.readString();
        path = in.readString();
        time = in.readLong();
        size = in.readLong();
    }

    public static final Creator<LocalMusicSongBean> CREATOR = new Creator<LocalMusicSongBean>() {
        @Override
        public LocalMusicSongBean createFromParcel(Parcel in) {
            return new LocalMusicSongBean(in);
        }

        @Override
        public LocalMusicSongBean[] newArray(int size) {
            return new LocalMusicSongBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(singer);
        dest.writeString(path);
        dest.writeLong(time);
        dest.writeLong(size);
    }
}
