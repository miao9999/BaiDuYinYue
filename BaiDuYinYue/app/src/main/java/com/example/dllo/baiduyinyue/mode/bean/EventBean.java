package com.example.dllo.baiduyinyue.mode.bean;

/**
 * Created by Limiao on 16/7/21.
 * eventBus的事件类
 */
public class EventBean {
    private String imgUrl,songUrl;
    private int songNum;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private LocalMusicSongBean localMusicSongBean;

    public LocalMusicSongBean getLocalMusicSongBean() {
        return localMusicSongBean;
    }

    public void setLocalMusicSongBean(LocalMusicSongBean localMusicSongBean) {
        this.localMusicSongBean = localMusicSongBean;
    }

    public int getSongNum() {
        return songNum;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
