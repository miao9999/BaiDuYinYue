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
     * 最热MV
     */
    public static final String MV_HOT_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=0&page_num=1&page_size=20&query=%E5%85%A8%E9%83%A8";
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
    /**
     * 推荐--歌曲分类上面图片
     */
    public static final String RECOMMEND_SORT_HEAD_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&method=baidu.ting.tag.getHotTag&format=json&from=android&version=5.8.0.1&nums=8";
    /**
     * 推荐--歌曲分类具体内容
     */
    public static final String RECOMMEND_SORT_CONTENT_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&method=baidu.ting.tag.getAllTag&format=json&from=android&version=5.8.0.1";
    /**
     * 乐库--排行--详情
     */
    public static final String TOP_DETAIL_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=参数&format=json&offset=0&size=50&from=ios&fields=title,song_id,author,resource_type,havehigh,is_new,has_mv_mobile,album_title,ting_uid,album_id,charge,all_rate&version=5.2.1&from=ios&channel=appstore";
    /**
     * 播放音乐
     */
    public static final String SONG_ULR = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.song.play&format=json&callback=&songid=参数&_=1413017198449";

    /**
     * MV详情
     */
    public static final String MV_DETAIL_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.0.1&channel=xiaomi&operator=2&provider=11%2C12&method=baidu.ting.mv.playMV&format=json&mv_id=参数&song_id=&definition=0";
}
