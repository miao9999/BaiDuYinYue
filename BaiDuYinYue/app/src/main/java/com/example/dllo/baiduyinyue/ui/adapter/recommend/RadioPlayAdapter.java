package com.example.dllo.baiduyinyue.ui.adapter.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/15.
 */
public class RadioPlayAdapter extends BaseAdapter {
    private List<RecommendBean.ResultBean.RadioBean.MusicPlayBean> musicPlayBeen;
    private Context context;

    public RadioPlayAdapter(Context context) {
        this.context = context;
    }

    public void setMusicPlayBeen(List<RecommendBean.ResultBean.RadioBean.MusicPlayBean> musicPlayBeen) {
        this.musicPlayBeen = musicPlayBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return musicPlayBeen == null ? 0 : musicPlayBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return musicPlayBeen == null ? null : musicPlayBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RadioPlayViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview,parent,false);
            holder = new RadioPlayViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (RadioPlayViewHolder) convertView.getTag();
        }
        RecommendBean.ResultBean.RadioBean.MusicPlayBean musicPlayBean = musicPlayBeen.get(position);
        holder.titleTv.setText(musicPlayBean.getTitle());
        Picasso.with(context).load(musicPlayBean.getPic()).resize(300,300).into(holder.bgIv);
        return convertView;
    }

    class RadioPlayViewHolder {
        private ImageView bgIv;
        private TextView titleTv ;
        public RadioPlayViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_gridview_bg_iv);
            titleTv = (TextView) view.findViewById(R.id.item_gridview_title_tv);

        }
    }
}
