package com.example.dllo.baiduyinyue.mode.bean;

import java.util.List;

/**
 * Created by Limiao on 16/7/21.
 * eventBus的事件类
 */
public class EventBean {
    private String imgUrl, songUrl;
    private int songNum;
    private int type, currentIndex;

    private TopDetailBean topDetailBean;

    public TopDetailBean getTopDetailBean() {
        return topDetailBean;
    }


    public void setTopDetailBean(TopDetailBean topDetailBean) {
        this.topDetailBean = topDetailBean;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    private List<LocalMusicSongBean> localMusicSongBeen;

    public int getType() {
        return type;
    }

    public List<LocalMusicSongBean> getLocalMusicSongBeen() {

        return localMusicSongBeen;
    }

    public void setLocalMusicSongBeen(List<LocalMusicSongBean> localMusicSongBeen) {
        this.localMusicSongBeen = localMusicSongBeen;
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
