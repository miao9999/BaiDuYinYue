package com.example.dllo.baiduyinyue.mode.net;

/**
 * Created by Limiao on 16/7/13.
 * 常量类
 */
public final class NetValues {
    /**
     * 排行url
     */
    public static final String TOP_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billCategory&format=json&from=ios&version=5.2.1&from=ios&channel=appstore\n";
    /**
     * 推荐url
     */
    public static final String RECOMMEND_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&method=baidu.ting.plaza.index&cuid=37C292E541CFC81D026F380EBAAE4111";
    /**
     * 歌单url
     */
    public static final String SONGLIST_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedan&page_no=1&page_size=30&from=ios&version=5.2.3&from=ios&channel=appstore";
    /**
     * 全部MV
     */
    public static final String MV_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=1&page_num=1&page_size=20&query=全部http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=1&page_num=1&page_size=20&query=全部http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=1&page_num=1&page_size=20&query=全部";
    /**
     * k歌轮播图
     */
    public static final String K_CYCLE_PIC_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&method=baidu.ting.active.showList";
    /**
     * K歌记录
     */
    public static final String K_SING_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&method=baidu.ting.learn.now&page_size=50";
    /**
     * 搜索
     */
    public static final String SEARCH_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&method=baidu.ting.search.hot";

}
