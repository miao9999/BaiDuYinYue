package com.example.dllo.baiduyinyue.mode.bean;

import java.util.List;

/**
 * Created by Limiao on 16/7/17.
 * 搜索界面的实体类
 */
public class SearchBean {

    /**
     * error_code : 22000
     * result : [{"strong":1,"linktype":0,"linkurl":"","word":"郁可唯"},{"strong":0,"linktype":0,"linkurl":"","word":"张杰"},{"strong":0,"linktype":0,"linkurl":"","word":"薛之谦"},{"strong":0,"linktype":0,"linkurl":"","word":"父亲"},{"strong":0,"linktype":0,"linkurl":"","word":"太阳的后裔 O.S.T"},{"strong":0,"linktype":0,"linkurl":"","word":"伤感"},{"strong":0,"linktype":0,"linkurl":"","word":"世界上最难唱的歌"},{"strong":0,"linktype":0,"linkurl":"","word":"老九门"},{"strong":0,"linktype":0,"linkurl":"","word":"许嵩"},{"strong":0,"linktype":0,"linkurl":"","word":"小苹果-筷子兄弟"}]
     */

    private int error_code;
    /**
     * strong : 1
     * linktype : 0
     * linkurl :
     * word : 郁可唯
     */

    private List<MySearchBean> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<MySearchBean> getResult() {
        return result;
    }

    public void setResult(List<MySearchBean> result) {
        this.result = result;
    }

    public static class MySearchBean {
        private int strong;
        private int linktype;
        private String linkurl;
        private String word;

        public int getStrong() {
            return strong;
        }

        public void setStrong(int strong) {
            this.strong = strong;
        }

        public int getLinktype() {
            return linktype;
        }

        public void setLinktype(int linktype) {
            this.linktype = linktype;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }
}
