package com.example.dllo.baiduyinyue.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.ui.fragment.MainFragment;
import com.example.dllo.baiduyinyue.ui.fragment.SearchFragment;
import com.example.dllo.baiduyinyue.ui.fragment.mine_child_fragment.LocalMusicFragment;
import com.example.dllo.baiduyinyue.utils.Contant;
import com.example.dllo.baiduyinyue.utils.OnSkipFragment;

public class MainActivity extends AbsBaseActivity implements OnSkipFragment {


    private ProgressBar mProgress;
    private MainFragment mainFragment;
    private FragmentManager manager;
    private SearchFragment searchFragment;
    private LocalMusicFragment localMusicFragment;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mProgress = findView(R.id.main_progressbar);
        mainFragment = new MainFragment();
        mainFragment.setOnSkipFragment(this);
        searchFragment = new SearchFragment();
        localMusicFragment = new LocalMusicFragment();
    }

    @Override
    protected void initData() {

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_content_frame, mainFragment);
        transaction.addToBackStack("mainFragment");
        transaction.commit();
    }

    @Override
    public void toFragment(int type) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case Contant.SEARCH_FRAGMENT:
                transaction.replace(R.id.main_content_frame, searchFragment).addToBackStack(null);
                break;
            case Contant.MAIN_FRAGMENT:
                transaction.replace(R.id.main_content_frame,mainFragment).addToBackStack(null);
                break;
            case Contant.LOCAL_MUSIC_FRAGMENT:
                transaction.replace(R.id.main_content_frame,localMusicFragment).addToBackStack(null);
                break;
        }
        transaction.commit();
    }

    /**
     * 监听返回键,当只有一个fragment在栈中时,按返回键直接finishMainActivity
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(getSupportFragmentManager().getBackStackEntryCount()==1){
                finish();
                return true;
            }
        }
        super.onKeyDown(keyCode, event);
        return true;
    }
}
