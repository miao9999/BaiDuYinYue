package com.example.dllo.baiduyinyue.mode.bean;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 */
public class RecommendCyclePicBean {

    /**
     * pic : [{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468399808d311d72aac288c888e911ab0464cba34.jpg","randpic_ios5":"","randpic_desc":"金希澈","randpic_ipad":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14683774837967548cff10b5cefe86fd6fd80eac48.jpg","randpic_qq":"","randpic_2":"bos_client_146839981578a2c96d205e56fd75733b67f255de89","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_146839981578a2c96d205e56fd75733b67f255de89.jpg","special_type":0,"ipad_desc":"金希澈","is_publish":"110000","mo_type":"4","type":6,"code":"http://music.baidu.com/cms/webview/draw_tmpl/draw20160712/index.html"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468415892d1ab6c176ca7fe0828a65af5c6624cfd.jpg","randpic_ios5":"","randpic_desc":"snh48","randpic_ipad":"","randpic_qq":"","randpic_2":"bos_client_14684159016e371c4bc10e7faf3284150604a0feac","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14684159016e371c4bc10e7faf3284150604a0feac.jpg","special_type":0,"ipad_desc":"snh48","is_publish":"110000","mo_type":"4","type":6,"code":"http://music.baidu.com/cms/webview/bigwig/v6/index.html"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_146838936796c53f458edaf22b04fddb87d65c22d8.jpg","randpic_ios5":"","randpic_desc":"就爱长歌名","randpic_ipad":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14683893794520e416cbbb08fb7e3fba1c2d306108.jpg","randpic_qq":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468389388447d35b5d94ac3bea24e7dd9948d7cf5.jpg","randpic_2":"bos_client_14683893715fae6073f464cfec2aa516f2c2d82247","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14683893715fae6073f464cfec2aa516f2c2d82247.jpg","special_type":0,"ipad_desc":"就爱长歌名","is_publish":"110001","mo_type":"5","type":7,"code":"6793"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_146823289739fdbecb45795c7588319ef1b3e6ff33.jpg","randpic_ios5":"","randpic_desc":"一周音乐热38","randpic_ipad":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14682328155fd7eaa103a4f70822ea63290e3ba38d.jpg","randpic_qq":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468232819b38a5466a73ac0ac30341618f8e82ed5.jpg","randpic_2":"bos_client_14682328081a0931f1a1daff666df2e8e848539d15","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14682328081a0931f1a1daff666df2e8e848539d15.jpg","special_type":0,"ipad_desc":"一周音乐热38","is_publish":"110000","mo_type":"4","type":6,"code":"http://music.baidu.com/cms/webview/topic_activity/mobile-tmp-v42/"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468233150f564b8f338cc3297048b4e87517d3e54.jpg","randpic_ios5":"","randpic_desc":"丛日新","randpic_ipad":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468233158f4975293afd25fd2cf4d50d5932626b9.jpg","randpic_qq":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468233161b6a6926e5a3bab785ec18c59589ea60e.jpg","randpic_2":"bos_client_1468233153ab7058e82e83764ad9b4d04895652fa4","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468233153ab7058e82e83764ad9b4d04895652fa4.jpg","special_type":0,"ipad_desc":"丛日新","is_publish":"110001","mo_type":"4","type":6,"code":"http://music.baidu.com/cms/webview/bigwig/congfirst/index.html"}]
     * error_code : 22000
     */

    private int error_code;
    /**
     * randpic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1468399808d311d72aac288c888e911ab0464cba34.jpg
     * randpic_ios5 :
     * randpic_desc : 金希澈
     * randpic_ipad : http://business.cdn.qianqian.com/qianqian/pic/bos_client_14683774837967548cff10b5cefe86fd6fd80eac48.jpg
     * randpic_qq :
     * randpic_2 : bos_client_146839981578a2c96d205e56fd75733b67f255de89
     * randpic_iphone6 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_146839981578a2c96d205e56fd75733b67f255de89.jpg
     * special_type : 0
     * ipad_desc : 金希澈
     * is_publish : 110000
     * mo_type : 4
     * type : 6
     * code : http://music.baidu.com/cms/webview/draw_tmpl/draw20160712/index.html
     */

    private List<PicBean> pic;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public static class PicBean {
        private String randpic;
        private String randpic_ios5;
        private String randpic_desc;
        private String randpic_ipad;
        private String randpic_qq;
        private String randpic_2;
        private String randpic_iphone6;
        private int special_type;
        private String ipad_desc;
        private String is_publish;
        private String mo_type;
        private int type;
        private String code;

        public String getRandpic() {
            return randpic;
        }

        public void setRandpic(String randpic) {
            this.randpic = randpic;
        }

        public String getRandpic_ios5() {
            return randpic_ios5;
        }

        public void setRandpic_ios5(String randpic_ios5) {
            this.randpic_ios5 = randpic_ios5;
        }

        public String getRandpic_desc() {
            return randpic_desc;
        }

        public void setRandpic_desc(String randpic_desc) {
            this.randpic_desc = randpic_desc;
        }

        public String getRandpic_ipad() {
            return randpic_ipad;
        }

        public void setRandpic_ipad(String randpic_ipad) {
            this.randpic_ipad = randpic_ipad;
        }

        public String getRandpic_qq() {
            return randpic_qq;
        }

        public void setRandpic_qq(String randpic_qq) {
            this.randpic_qq = randpic_qq;
        }

        public String getRandpic_2() {
            return randpic_2;
        }

        public void setRandpic_2(String randpic_2) {
            this.randpic_2 = randpic_2;
        }

        public String getRandpic_iphone6() {
            return randpic_iphone6;
        }

        public void setRandpic_iphone6(String randpic_iphone6) {
            this.randpic_iphone6 = randpic_iphone6;
        }

        public int getSpecial_type() {
            return special_type;
        }

        public void setSpecial_type(int special_type) {
            this.special_type = special_type;
        }

        public String getIpad_desc() {
            return ipad_desc;
        }

        public void setIpad_desc(String ipad_desc) {
            this.ipad_desc = ipad_desc;
        }

        public String getIs_publish() {
            return is_publish;
        }

        public void setIs_publish(String is_publish) {
            this.is_publish = is_publish;
        }

        public String getMo_type() {
            return mo_type;
        }

        public void setMo_type(String mo_type) {
            this.mo_type = mo_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
