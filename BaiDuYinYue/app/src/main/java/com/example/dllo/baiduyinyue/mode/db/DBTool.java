package com.example.dllo.baiduyinyue.mode.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.dllo.baiduyinyue.ui.application.MyApp;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Limiao on 16/7/27.
 * 收藏数据的数据库工具类,有增删查的方法
 */
public class DBTool implements IDBTool<CollectionBean> {
    private CollectionBeanDao collectionBeanDao;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SQLiteDatabase database;
    private DaoMaster.DevOpenHelper helper;
    private QueryBuilder queryBuilder = getCollectionBeanDao().queryBuilder();
    private static DBTool dbInstance;

    /**
     * 私有化的构造方法
     */
    private DBTool() {

    }

    /**
     * 对外提供的获取DBTool单例的方法
     *
     * @return
     */
    public static DBTool getDbInstance() {
        if (dbInstance == null) {
            synchronized (DBTool.class) {
                if (dbInstance == null) {
                    dbInstance = new DBTool();
                }
            }
        }
        return dbInstance;
    }

    /**
     * 获取DaoMaster.DevOpenHelper对象
     *
     * @return
     */
    private DaoMaster.DevOpenHelper _getHelper() {
        helper = new DaoMaster.DevOpenHelper(MyApp.getContext(), "collect.db", null);
        return helper;
    }

    private DaoMaster.DevOpenHelper getHelper() {
        return _getHelper();
    }

    /**
     * 获取database对象
     *
     * @return
     */
    private SQLiteDatabase _getDatabase() {
        database = getHelper().getWritableDatabase();
        return database;
    }

    private SQLiteDatabase getDatabase() {
        return _getDatabase();
    }

    /**
     * 获取DaoMaster对象
     *
     * @return
     */
    private DaoMaster _getDaoMaster() {
        daoMaster = new DaoMaster(getDatabase());
        return daoMaster;
    }

    private DaoMaster getDaoMaster() {
        return _getDaoMaster();
    }

    /**
     * 获取DaoSession对象
     *
     * @return
     */
    private DaoSession _getDaoSession() {
        daoSession = getDaoMaster().newSession();
        return daoSession;
    }

    private DaoSession getDaoSession() {
        return _getDaoSession();
    }

    /**
     * 获取CollectionBeanDao对象
     *
     * @return
     */
    private CollectionBeanDao _getCollectionBeanDao() {
        collectionBeanDao = getDaoSession().getCollectionBeanDao();
        return collectionBeanDao;
    }

    private CollectionBeanDao getCollectionBeanDao() {
        return _getCollectionBeanDao();
    }


    /**
     * 添加数据的方法
     *
     * @param collectionBean
     */
    @Override
    public void insert(CollectionBean collectionBean) {
        getCollectionBeanDao().insertOrReplace(collectionBean);
    }



    /**
     * 通过歌曲名字查询数据的方法
     *
     * @param songName
     * @return
     */
    @Override
    public List<CollectionBean> queryBySongName(String songName) {
        queryBuilder = getCollectionBeanDao().queryBuilder();
        queryBuilder.where(CollectionBeanDao.Properties.SongName.eq(songName));
        return queryBuilder.list();
    }

    @Override
    public List<CollectionBean> queryAll() {
        return getCollectionBeanDao().loadAll();
    }

    /**
     * 通过歌曲名字删除数据的方法
     *
     * @param songName
     */
    @Override
    public void delBySongName(String songName) {
        getCollectionBeanDao().deleteInTx(queryBySongName(songName));
    }
}
